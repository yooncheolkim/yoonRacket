package com.yooncheol.yoonracket.data.source

import com.yooncheol.yoonracket.data.Comment
import io.reactivex.Observable
import io.reactivex.Single

interface CommentSource {
    fun insertToFirebase(comment : Comment)

    fun getCommentList(productId : String) : Observable<List<Comment>>

    fun getCommentOnce(productId : String) : Single<Comment>
}