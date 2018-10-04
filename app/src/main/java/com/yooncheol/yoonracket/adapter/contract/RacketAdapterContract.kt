package com.yooncheol.yoonracket.adapter.contract

import com.yooncheol.yoonracket.data.Product

interface RacketAdapterContract {

    interface View{
        var onClickFunc : ((Int) -> Unit)?

        fun notifyAdapter()
    }

    interface Model{
        fun addItems(items : ArrayList<Product>)

        fun clearItem()

        fun getItem(position : Int) : Product
    }
}