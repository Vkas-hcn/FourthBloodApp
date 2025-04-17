package com.blue.colored.frog.flowing.light.thirdbloodpressure.main.pressure

import android.annotation.SuppressLint
import android.util.Log
import android.view.MotionEvent
import android.view.View.OnTouchListener
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.blue.colored.frog.flowing.light.thirdbloodpressure.R
import com.blue.colored.frog.flowing.light.thirdbloodpressure.databinding.ActivityPDBinding
import com.blue.colored.frog.flowing.light.thirdbloodpressure.god.BaseActivity
import com.blue.colored.frog.flowing.light.thirdbloodpressure.key.PressureBean
import com.blue.colored.frog.flowing.light.thirdbloodpressure.key.TBSUtils
import com.blue.colored.frog.flowing.light.thirdbloodpressure.wight.VerticalPickerView


class PressureDetail : BaseActivity<ActivityPDBinding, PressureViewModel>() {
    private var isEdit = false
    private var pressureId = ""
    private var pressureBean: PressureBean? = null
    override val layoutId: Int
        get() = R.layout.activity_p_d

    override val viewModelClass: Class<PressureViewModel>
        get() = PressureViewModel::class.java

    @SuppressLint("ClickableViewAccessibility")
    override fun setupViews() {
        isEdit = intent.getBooleanExtra("isEdit", false)
        pressureId = intent.getStringExtra("pressureId").toString()
        isEditShow()
        binding.tvRecord.setOnClickListener {
            finish()
        }
        binding.tvDelete.setOnClickListener {
            showDeleteDialog {
                viewModel.deletePressure(pressureBean!!)
                finish()
            }
        }
        binding.vpvSys.setOnValueChangeListener(object : VerticalPickerView.OnValueChangeListener {
            override fun onValueChange(newValue: Int) {
                binding.colorState =
                    TBSUtils.checkBloodPressure(newValue, binding.vpvDia.getSelectedValue())
                Log.e("TAG", "onValueChange: newValue-Sys-=$newValue", )
                Log.e("TAG", "onValueChange: binding.vpvDia.getSelectedValue()=${binding.vpvDia.getSelectedValue()}")
                Log.e("TAG", "onValueChange: binding.colorState=${binding.colorState}")

                setStateData()
            }
        })

        binding.vpvDia.setOnValueChangeListener(object : VerticalPickerView.OnValueChangeListener {
            override fun onValueChange(newValue: Int) {
                binding.colorState =
                    TBSUtils.checkBloodPressure(binding.vpvSys.getSelectedValue(), newValue)
                Log.e("TAG", "onValueChange: newValue-dia=$newValue", )
                Log.e("TAG", "onValueChange: binding.vpvSys.getSelectedValue()=${binding.vpvSys.getSelectedValue()}")
                Log.e("TAG", "onValueChange: binding.colorState=${binding.colorState}")
                setStateData()
            }
        })

        binding.imgSave.setOnClickListener {
            pressureBean = PressureBean(
                systolic = binding.vpvSys.getSelectedValue(),
                diatonic = binding.vpvDia.getSelectedValue(),
                pultonic = binding.vpvPul.getSelectedValue(),
                date = TBSUtils.getDateTime(System.currentTimeMillis().toString()),
                remark = binding.etRecording.text.toString().trim(),
                id = if (isEdit) {
                    pressureBean!!.id
                } else {
                    System.currentTimeMillis().toString()
                }
            )
            if (isEdit) {
                viewModel.updatePressure(pressureBean!!)
            } else {
                viewModel.savePressure(pressureBean!!)
            }
            finish()
        }
        binding.etRecording.setOnTouchListener { v, event ->
            if (v.id == R.id.et_recording) {
                v.parent.requestDisallowInterceptTouchEvent(true)
                when (event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_UP -> v.parent.requestDisallowInterceptTouchEvent(false)
                }
            }
            false
        }
    }

    fun setStateData() {
        binding.tvGreat.text = TBSUtils.getBloodPressureTip(binding?.colorState!!)
        binding.tvDetailState.text = TBSUtils.getBloodPressureState(binding?.colorState!!)
        binding.tvDetailState.setTextColor(TBSUtils.getBloodPressureStateColor(binding?.colorState!!))
        binding.tvDetailDate.text = pressureBean?.date?:TBSUtils.getDateTime(System.currentTimeMillis().toString())
    }

    private fun isEditShow() {
        binding.vpvPul.setRange(0, 250)
        binding.vpvDia.setRange(0, 150)
        binding.vpvSys.setRange(0, 200)
        if (isEdit) {
            pressureBean = viewModel.getPressureListData().find { it.id == pressureId }
            binding.vpvSys.setDefaultValue(pressureBean?.systolic!!)
            binding.vpvDia.setDefaultValue(pressureBean?.diatonic!!)
            binding.vpvPul.setDefaultValue(pressureBean?.pultonic!!)
            binding.colorState =
                TBSUtils.checkBloodPressure(pressureBean?.systolic!!, pressureBean?.diatonic!!)
            binding.tvDetailState.text = TBSUtils.getBloodPressureState(binding?.colorState!!)
            binding.tvDetailState.setTextColor(TBSUtils.getBloodPressureStateColor(binding?.colorState!!))
            binding.tvDetailDate.text = pressureBean?.date!!
            binding.tvGreat.text = TBSUtils.getBloodPressureTip(binding?.colorState!!)
            binding.etRecording.setText(pressureBean?.date!!)
            binding.tvDelete.isVisible = true
            binding.atvDate.text = "Datetime:${pressureBean?.date}"
            if (pressureBean!!.remark.isNotBlank()) {
                binding.llEditRecording.isVisible = true
                binding.etRecording.setText(pressureBean?.remark)
            } else {
                binding.llEditRecording.isVisible = false
            }
        } else {
            binding.tvDetailDate.text = TBSUtils.getDateTime(System.currentTimeMillis().toString())
            binding.atvDate.text =
                "Datetime:${TBSUtils.getDateTime(System.currentTimeMillis().toString())}"
            binding.tvDelete.isVisible = false
            binding.colorState = 2
            binding.vpvSys.setDefaultValue(100)
            binding.vpvDia.setDefaultValue(70)
            binding.vpvPul.setDefaultValue(70)
        }
    }

    override fun observeViewModel() {
    }

    private fun showDeleteDialog(deleteFun: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle("Tip")
            .setMessage("Do You Need To Delete Records?")
            .setPositiveButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("Confirm") { dialog, _ ->
                deleteFun()
                dialog.dismiss()
            }
            .create()
            .show()
    }
}