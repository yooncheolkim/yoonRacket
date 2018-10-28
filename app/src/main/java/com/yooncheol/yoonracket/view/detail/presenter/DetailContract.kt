package com.yooncheol.yoonracket.view.detail.presenter

import com.yooncheol.yoonracket.data.source.CommentRepository

interface DetailContract {

    interface View{
        fun showToast(msg : String)
    }

    interface Presenter{
        var view : DetailContract.View
        var commentData : CommentRepository

        fun loadData()
    }
}