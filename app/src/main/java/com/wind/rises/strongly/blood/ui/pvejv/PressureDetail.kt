package com.wind.rises.strongly.blood.ui.pvejv

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wind.rises.strongly.blood.data.AllUtils
import com.wind.rises.strongly.blood.databinding.ActivityPDBinding
import com.wind.rises.strongly.blood.data.PressureBean
import com.wind.rises.strongly.blood.data.TBSUtils

class PressureDetail : AppCompatActivity() {
    private val binding by lazy { ActivityPDBinding.inflate(layoutInflater) }
    private var isEdit = false
    private var pressureId = ""
    private var pressureBean: PressureBean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupViews() {
        isEdit = intent.getBooleanExtra("isEdit", false)
        pressureId = intent.getStringExtra("pressureId").orEmpty()
        isEditShow()

        binding.tvRecord.setOnClickListener { finish() }

        binding.editSys.addTextChangedListener(createTextWatcher { sysData, diaData ->
            updateBloodPressureState(sysData, diaData)
        })

        binding.editDia.addTextChangedListener(createTextWatcher { diaData, sysData ->
            updateBloodPressureState(sysData, diaData)
        })

        binding.imgSave.setOnClickListener {
            val sysEditData = binding.editSys.text.toString().trim()
            val diaEditData = binding.editDia.text.toString().trim()
            val pulEditData = binding.editPulse.text.toString().trim()

            if (sysEditData.isEmpty() || diaEditData.isEmpty() || pulEditData.isEmpty()) {
                Toast.makeText(this, "Please fill it out completely", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            pressureBean = PressureBean(
                systolic = sysEditData.toIntOrNull() ?: 0,
                diatonic = diaEditData.toIntOrNull() ?: 0,
                pultonic = pulEditData.toIntOrNull() ?: 0,
                date = TBSUtils.getDateTime(System.currentTimeMillis().toString()),
                id = if (isEdit) pressureBean?.id ?: "" else System.currentTimeMillis().toString()
            )

            if (isEdit) {
                pressureBean?.let { AllUtils.updatePressure(it) }
            } else {
                pressureBean?.let { AllUtils.savePressure(it) }
            }
            finish()
        }
    }

    private fun createTextWatcher(onTextChanged: (Int, Int) -> Unit): android.text.TextWatcher {
        return object : android.text.TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: android.text.Editable?) {
                val sysData = binding.editSys.text.toString().trim().toIntOrNull() ?: 0
                val diaData = binding.editDia.text.toString().trim().toIntOrNull() ?: 0
                onTextChanged(sysData, diaData)
            }
        }
    }

    private fun updateBloodPressureState(sysData: Int, diaData: Int) {
        binding.colorState = TBSUtils.checkBloodPressure(sysData, diaData)
        setStateData()
    }

    private fun setStateData() {
        binding.tvGreat.text = TBSUtils.getBloodPressureTip(binding.colorState ?: 2)
        binding.tvDetailState.text = TBSUtils.getBloodPressureState(binding.colorState ?: 2)
        binding.tvDetailState.setTextColor(TBSUtils.getBloodPressureStateColor(binding.colorState ?: 2))
        binding.tvDetailDate.text = pressureBean?.date ?: TBSUtils.getDateTime(System.currentTimeMillis().toString())
    }

    private fun isEditShow() {
        if (isEdit) {
            pressureBean = AllUtils.getPressureListData().find { it.id == pressureId }
            pressureBean?.let {
                binding.editSys.setText(it.systolic.toString())
                binding.editDia.setText(it.diatonic.toString())
                binding.editPulse.setText(it.pultonic.toString())
                binding.colorState = TBSUtils.checkBloodPressure(it.systolic, it.diatonic)
                binding.tvDetailState.text = TBSUtils.getBloodPressureState(binding.colorState ?: 2)
                binding.tvDetailState.setTextColor(TBSUtils.getBloodPressureStateColor(binding.colorState ?: 2))
                binding.tvDetailDate.text = it.date
                binding.tvGreat.text = TBSUtils.getBloodPressureTip(binding.colorState ?: 2)
                binding.imgCc.setImageResource(TBSUtils.getBloodPressureStateImage(binding.colorState ?: 2))
                binding.atvDate.text = "Datetime:${it.date}"
            }
        } else {
            binding.tvDetailDate.text = TBSUtils.getDateTime(System.currentTimeMillis().toString())
            binding.atvDate.text = "Datetime:${TBSUtils.getDateTime(System.currentTimeMillis().toString())}"
            binding.colorState = 2
            binding.editSys.setText("100")
            binding.editDia.setText("70")
            binding.editPulse.setText("70")
        }
    }
}