package com.acg.hotel.ui.other.test

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.acg.hotel.R

/**
 * @Classname TestAdapter
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/9/10 18:59
 * @Created by an
 */
class TestAdapter(arrayData: ArrayList<TestData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TAG = "TestAdapter"


    private var listData: List<TestData> = arrayData


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.activity_test_rv_item, parent, false)
        return  TestViewHolder1(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TestViewHolder1) {
            holder.textView?.text = listData[position].textMessage
            holder.btnTest?.text = listData[position].btnText
            holder.btnTest?.setOnClickListener {
                Log.e(TAG, "点击了 $position")
            }
        }
    }

    override fun getItemCount(): Int {
        return listData.size
    }


}