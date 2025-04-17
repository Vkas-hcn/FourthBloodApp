package com.blue.colored.frog.flowing.light.thirdbloodpressure.main.sugar

import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.blue.colored.frog.flowing.light.thirdbloodpressure.R
import com.blue.colored.frog.flowing.light.thirdbloodpressure.databinding.FragmentSugarBinding
import com.blue.colored.frog.flowing.light.thirdbloodpressure.god.BaseFragment
import com.blue.colored.frog.flowing.light.thirdbloodpressure.key.SugarBean

class SugarFragment : BaseFragment<FragmentSugarBinding, SugarViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_sugar

    override val viewModelClass: Class<SugarViewModel>
        get() = SugarViewModel::class.java
    private lateinit var sugarBeanList: MutableList<SugarBean>
    private lateinit var sugarAdapter: SugarAdapter
    override fun setupViews() {
        binding.imageView4.setOnClickListener {
            val intent = Intent(activity, SugarDetail::class.java)
            startActivity(intent)
        }
    }

    override fun observeViewModel() {
        initAdapter()
    }

    private fun initAdapter() {
        sugarBeanList = viewModel.getSugarListData()
        binding.haveList = sugarBeanList.isNotEmpty()
        sugarAdapter = SugarAdapter(sugarBeanList)
        binding.rvHistory.adapter = sugarAdapter
        binding.rvHistory.layoutManager = LinearLayoutManager(activity)
        sugarAdapter.setOnItemClickListener(object : SugarAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                jumpToDetail(sugarBeanList[position].id)
            }
        })
    }

    fun jumpToDetail(sugarId: String) {
        val intent = Intent(activity, SugarDetail::class.java)
        intent.putExtra("isEdit", true)
        intent.putExtra("sugarId", sugarId)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun refreshData() {
        sugarBeanList = viewModel.getSugarListData()
        sugarAdapter.setDataList(sugarBeanList)
        binding.haveList = sugarBeanList.isNotEmpty()
    }
}