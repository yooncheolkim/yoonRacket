package com.yooncheol.yoonracket.view.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.yooncheol.yoonracket.R
import com.yooncheol.yoonracket.adapter.RacketRecyclerViewAdapter
import com.yooncheol.yoonracket.data.source.ProductRepository
import com.yooncheol.yoonracket.view.main.presenter.MainContract
import com.yooncheol.yoonracket.view.main.presenter.MainPresenter

class MainActivity : AppCompatActivity() , MainContract.View{
    private val TAG = "MainActivity"

    private val recyclerView by lazy{
        findViewById(R.id.recycler_view) as RecyclerView
    }

    private lateinit var racketAdapter : RacketRecyclerViewAdapter

    private lateinit var presenter : MainPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        racketAdapter = RacketRecyclerViewAdapter(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = racketAdapter

        presenter = MainPresenter().apply {
            view = this@MainActivity
            productData = ProductRepository
            adapterModel = racketAdapter
            adapterView = racketAdapter
        }

        presenter.loadItems()
    }

    override fun showToast(title: String) {
        Toast.makeText(this,"onClick Item $title",Toast.LENGTH_SHORT).show()
    }
}
