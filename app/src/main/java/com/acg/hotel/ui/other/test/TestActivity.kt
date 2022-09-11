package com.acg.hotel.ui.other.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.acg.hotel.R
import com.scwang.smartrefresh.layout.SmartRefreshLayout

class TestActivity : AppCompatActivity() {

    private val TAG = "TestActivity"

    private var mTestRv:RecyclerView? = null
    private var mRefresh: SmartRefreshLayout? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        initView()


        var listData = initData()

        setSimpleAdapter(listData)

    }

    private fun initView() {
        mTestRv = findViewById(R.id.test_list)
        mRefresh = findViewById<SmartRefreshLayout>(R.id.test_refresh)

        mRefresh?.setOnRefreshListener {
            mRefresh?.finishRefresh(true)
            Log.e(TAG, "已经完成了刷新")
        }

    }

    private fun setSimpleAdapter(listData: ArrayList<TestData>) {
        var testAdapter = TestAdapter(listData)
        mTestRv?.adapter = testAdapter
        //LayoutManger必须设置，否则不显示列表
        mTestRv?.layoutManager = LinearLayoutManager(this)
    }

    private fun initData(): ArrayList<TestData> {
        var listData = ArrayList<TestData>()
        listData.add(TestData("test1", "测试1"))
        listData.add(TestData("test2", "测试2"))
        listData.add(TestData("test3", "测试3"))
        listData.add(TestData("test4", "测试4"))
        return listData
    }
}