package com.yooncheol.yoonracket.view.detail.presenter

import com.yooncheol.yoonracket.data.source.CommentRepository

class DetailPresenter : DetailContract.Presenter{
    override lateinit var view : DetailContract.View

    override lateinit var commentData: CommentRepository

    override fun loadData() {
        
    }
}