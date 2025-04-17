package com.blue.colored.frog.flowing.light.thirdbloodpressure.main.pressure

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.blue.colored.frog.flowing.light.thirdbloodpressure.R
import com.blue.colored.frog.flowing.light.thirdbloodpressure.databinding.FragmentPressureBinding
import com.blue.colored.frog.flowing.light.thirdbloodpressure.god.BaseFragment
import com.blue.colored.frog.flowing.light.thirdbloodpressure.key.PressureBean

class PressureFragment : BaseFragment<FragmentPressureBinding, PressureViewModel>() {

    override val layoutId: Int
        get() = R.layout.fragment_pressure

    override val viewModelClass: Class<PressureViewModel>
        get() = PressureViewModel::class.java

    private lateinit var pressureBeanList: MutableList<PressureBean>
    private lateinit var pressureAdapter: PressureAdapter
    override fun setupViews() {
        binding.imgNew.setOnClickListener {
            val intent = Intent(activity, PressureDetail::class.java)
            startActivity(intent)
        }
    }

    override fun observeViewModel() {
        initAdapter()
    }

    private fun initAdapter() {
        pressureBeanList = viewModel.getPressureListData()
        binding.haveList = pressureBeanList.isNotEmpty()
        pressureAdapter = PressureAdapter(pressureBeanList)
        binding.rvHistory.adapter = pressureAdapter
        binding.rvHistory.layoutManager = GridLayoutManager(activity, 2)
        pressureAdapter.setOnItemClickListener(object : PressureAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                jumpToDetail(pressureBeanList[position].id)
            }
        })
    }

    fun jumpToDetail(pressureId: String) {
        val intent = Intent(activity, PressureDetail::class.java)
        intent.putExtra("isEdit", true)
        intent.putExtra("pressureId", pressureId)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }
    private fun refreshData() {
        pressureBeanList = viewModel.getPressureListData()
        pressureAdapter.setDataList(pressureBeanList)
        binding.haveList = pressureBeanList.isNotEmpty()
    }
}