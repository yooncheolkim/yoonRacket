package com.yooncheol.yoonracket.data.source

import com.yooncheol.yoonracket.data.Product
import io.reactivex.Observable
import io.reactivex.Single

interface ProductSource {
    fun insertToFirebase(product : Product)

    fun getProductList() : Observable<List<Product>>

    fun getOnce(id : String) : Single<Product>
}