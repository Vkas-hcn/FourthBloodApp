package com.wind.rises.strongly.blood.ui.pvejv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wind.rises.strongly.blood.data.AllUtils.getPressureListData
import com.wind.rises.strongly.blood.databinding.FragmentPressureBinding
import com.wind.rises.strongly.blood.data.PressureBean

class PressureFragment : Fragment() {

    private lateinit var pressureBeanList: MutableList<PressureBean>
    private lateinit var pressureAdapter: PressureAdapter
    private val binding by lazy { FragmentPressureBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        observeViewModel()
    }
     fun setupViews() {
        binding.imgAdd.setOnClickListener {
            val intent = Intent(activity, PressureDetail::class.java)
            startActivity(intent)
        }
    }

     fun observeViewModel() {
        initAdapter()
    }

    private fun initAdapter() {
        pressureBeanList = getPressureListData()
        binding.haveList = pressureBeanList.isNotEmpty()
        pressureAdapter = PressureAdapter(pressureBeanList)
        binding.rvHistory.adapter = pressureAdapter
        binding.rvHistory.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
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
        pressureBeanList = getPressureListData()
        pressureAdapter.setDataList(pressureBeanList)
        binding.haveList = pressureBeanList.isNotEmpty()
    }
}