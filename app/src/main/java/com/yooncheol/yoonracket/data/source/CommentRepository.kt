package com.yooncheol.yoonracket.data.source

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.yooncheol.yoonracket.data.Comment
import com.yooncheol.yoonracket.util.FirebaseDatabaseUtils
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.SingleSubject

object CommentRepository : CommentSource{
    val TAG = "CommentRepository"

    override fun insertToFirebase(comment: Comment) {
        Log.d(TAG,"insert : ")

        var push : DatabaseReference = FirebaseDatabaseUtils.getCommentRef()
        comment.id = push.key!!
        push.setValue(comment)
    }

    override fun getCommentList(productId: String): Observable<List<Comment>> {
        var result :BehaviorSubject<List<Comment>> = BehaviorSubject.create()

        FirebaseDatabaseUtils.getProductRef()
                .orderByChild("name").equalTo("astrox 9")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(p0: DataSnapshot) {
                        Log.d(TAG,"onDataChange() : ")
                        print(p0)
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        Log.d(TAG,"onCancelled() : ")
                    }
                })
        return result.subscribeOn(Schedulers.io())
    }

    override fun getCommentOnce(id: String): Single<Comment> {
        Log.d(TAG,"getCommentOnce() called with : id = [$id]")

        var result : SingleSubject<Comment> = SingleSubject.create()

        FirebaseDatabaseUtils.getCommentRef()
                .child(id)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        var comment : Comment = dataSnapshot.getValue(Comment::class.java)!!
                        result.onSuccess(comment)
                    }

                    override fun onCancelled(p0: DatabaseError) {
                        Log.d(TAG,"getCommentOnce onCancelled.")
                    }
                })
        return result.subscribeOn(Schedulers.io())
    }
}