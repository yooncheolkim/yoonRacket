package com.yooncheol.yoonracket.data.source

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.yooncheol.yoonracket.data.Product
import com.yooncheol.yoonracket.util.FirebaseDatabaseUtils
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.SingleSubject

object ProductRepository : ProductSource{
    val TAG : String = "ProductRepository"


    override fun insertToFirebase(product: Product) {
        Log.d(TAG,"insert :")

        var push : DatabaseReference = FirebaseDatabaseUtils.getProductRef()
        product.id = push.key!!
        push.setValue(product)
    }

    override fun getProductList(): Observable<List<Product>> {
        val result : BehaviorSubject<List<Product>> = BehaviorSubject.create()

        FirebaseDatabaseUtils.getProductRef()
                .addValueEventListener(object : ValueEventListener{
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var productList : ArrayList<Product> = ArrayList()
                        for(snapshot in dataSnapshot.children){
                           var product : Product = snapshot.getValue<Product>(Product::class.java)!!
                            productList.add(product)
                        }
                        result.onNext(productList)
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        Log.d(TAG,"onCancelled : ")
                    }
                })
        return result.subscribeOn(Schedulers.io())
    }

    override fun getOnce(id: String): Single<Product> {
        Log.d(TAG, "getOnce() called with : id = [$id]")

        val result : SingleSubject<Product> = SingleSubject.create()

        FirebaseDatabaseUtils.getProductRef()
                .child(id)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var product : Product = dataSnapshot.getValue(Product::class.java)!!
                        result.onSuccess(product)
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        Log.d(TAG,"getOnce onCancelled : ")
                    }
                })
        return result.subscribeOn(Schedulers.io())
    }
}