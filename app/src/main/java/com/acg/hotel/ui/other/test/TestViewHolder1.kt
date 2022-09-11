package com.acg.hotel.ui.other.test

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.acg.hotel.R

/**
 * @Classname TestViewHolder1
 * @Description TODO
 * @Version 1.0.0
 * @Date 2022/9/10 18:54
 * @Created by an
 */
class TestViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var textView: TextView? = null
    var btnTest: Button? = null

    init {
        textView = itemView.findViewById(R.id.text_item_test)
        btnTest = itemView.findViewById(R.id.btn_item_test)

    }

}