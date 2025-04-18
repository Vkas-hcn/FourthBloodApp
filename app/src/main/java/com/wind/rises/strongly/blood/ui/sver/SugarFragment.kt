package com.wind.rises.strongly.blood.ui.sver

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.wind.rises.strongly.blood.data.AllUtils
import com.wind.rises.strongly.blood.databinding.FragmentSugarBinding
import com.wind.rises.strongly.blood.data.SugarBean

class SugarFragment : Fragment() {
    private val binding by lazy { FragmentSugarBinding.inflate(layoutInflater) }

    private lateinit var sugarBeanList: MutableList<SugarBean>
    private lateinit var sugarAdapter: SugarAdapter

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
            val intent = Intent(activity, SugarDetail::class.java)
            startActivity(intent)
        }
    }

     fun observeViewModel() {
        initAdapter()
    }

    private fun initAdapter() {
        sugarBeanList = AllUtils.getSugarListData()
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
        sugarBeanList = AllUtils.getSugarListData()
        sugarAdapter.setDataList(sugarBeanList)
        binding.haveList = sugarBeanList.isNotEmpty()
    }
}