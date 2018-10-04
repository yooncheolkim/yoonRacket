package com.yooncheol.yoonracket.view.main.presenter

import android.content.Context
import com.yooncheol.yoonracket.adapter.contract.RacketAdapterContract
import com.yooncheol.yoonracket.data.source.ProductRepository
import com.yooncheol.yoonracket.data.source.ProductSource

interface MainContract {

    interface View{
        fun showToast(title : String)
    }

    interface Presenter{
        var view : MainContract.View
        var productData : ProductRepository
        var adapterModel : RacketAdapterContract.Model
        var adapterView : RacketAdapterContract.View ?

        fun loadItems()
    }
}