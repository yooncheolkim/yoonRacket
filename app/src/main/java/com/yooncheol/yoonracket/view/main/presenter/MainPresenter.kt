package com.yooncheol.yoonracket.view.main.presenter

import com.yooncheol.yoonracket.adapter.contract.RacketAdapterContract
import com.yooncheol.yoonracket.data.Product
import com.yooncheol.yoonracket.data.source.ProductRepository
import com.yooncheol.yoonracket.data.source.ProductSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy

class MainPresenter : MainContract.Presenter{
    override lateinit var view: MainContract.View

    override lateinit var productData: ProductRepository

    override lateinit var adapterModel: RacketAdapterContract.Model

    override var adapterView: RacketAdapterContract.View? = null
    set(value){
        field = value
        field?.onClickFunc = {
            onClickListener(it)
        }
    }

    override fun loadItems() {
        productData.getProductList().observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    adapterModel.addItems(it as ArrayList<Product>)
                   adapterView!!.notifyAdapter()
                }
    }

    private fun onClickListener(position : Int){
        adapterModel.getItem(position).let{
            view.showToast(it.name.toString())
        }
    }
}