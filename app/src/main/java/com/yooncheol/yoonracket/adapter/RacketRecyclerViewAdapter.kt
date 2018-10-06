package com.yooncheol.yoonracket.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.yooncheol.yoonracket.R
import com.yooncheol.yoonracket.adapter.contract.RacketAdapterContract
import com.yooncheol.yoonracket.data.Product
import com.yooncheol.yoonracket.view.detail.DetailActivity

class RacketRecyclerViewAdapter(val context : Context)
    : RacketAdapterContract.View , RacketAdapterContract.Model , RecyclerView.Adapter<RacketRecyclerViewAdapter.SimpleRacketListViewHolder>(){

    private var mProductList : ArrayList<Product> = ArrayList()

    override var onClickFunc: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        = SimpleRacketListViewHolder(context,parent,onClickFunc)


    override fun onBindViewHolder(holder: SimpleRacketListViewHolder, position: Int) {
        mProductList[position].let{
            holder?.onBind(it,position)
        }
    }

    override fun getItemCount(): Int = mProductList.size

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    override fun addItems(items: ArrayList<Product>) {
        mProductList = items
    }

    override fun clearItem() {
        mProductList.clear()
    }

    override fun getItem(position: Int): Product = mProductList[position]



    class SimpleRacketListViewHolder(val context:Context, parent : ViewGroup ? , val listenerFunc : ((Int) -> Unit) ?)
        : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item, parent,false)){
        private val mImage by lazy{
            itemView.findViewById(R.id.item_image) as ImageView
        }

        private val mTitle by lazy{
            itemView.findViewById(R.id.item_title) as TextView
        }

        private val mCompany by lazy{
            itemView.findViewById(R.id.item_company) as TextView
        }

        fun onBind(product: Product, position : Int){
            Glide.with(itemView).load(product.imagePath).into(mImage)
            mTitle.setText(product.name)
            mCompany.setText(product.company)

            itemView.setOnClickListener {
                var intent : Intent = Intent(context,DetailActivity::class.java)
                intent.putExtra("productObject",product)
                context.startActivity(intent)
            }
        }
    }
}