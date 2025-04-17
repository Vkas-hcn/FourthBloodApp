package com.blue.colored.frog.flowing.light.thirdbloodpressure.main.sugar

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.blue.colored.frog.flowing.light.thirdbloodpressure.R
import com.blue.colored.frog.flowing.light.thirdbloodpressure.databinding.ActivitySDBinding
import com.blue.colored.frog.flowing.light.thirdbloodpressure.god.BaseActivity
import com.blue.colored.frog.flowing.light.thirdbloodpressure.key.DataUtilsHelp
import com.blue.colored.frog.flowing.light.thirdbloodpressure.key.SugarBean
import com.blue.colored.frog.flowing.light.thirdbloodpressure.key.TBSUtils
import java.math.BigDecimal
import java.math.RoundingMode

class SugarDetail : BaseActivity<ActivitySDBinding, SugarViewModel>() {
    private var isEdit = false
    private var pressureId = ""
    private var sugarBean: SugarBean? = null
    override val layoutId: Int
        get() = R.layout.activity_s_d

    override val viewModelClass: Class<SugarViewModel>
        get() = SugarViewModel::class.java
    private var currentStateSugar = TBSUtils.CurrentState.NORMAL

    @SuppressLint("ClickableViewAccessibility")
    override fun setupViews() {
        isEdit = intent.getBooleanExtra("isEdit", false)
        pressureId = intent.getStringExtra("sugarId").toString()
        isEditShow()
        binding.conDialog.setOnClickListener { }
        binding.tvRecord.setOnClickListener {
            finish()
        }
        binding.tvDelete.setOnClickListener {
            showDeleteDialog {
                viewModel.deleteSugar(sugarBean!!)
                finish()
            }
        }

        binding.tvSugarUnit.setOnClickListener {
            val inputText = binding.edSugarNum.text.toString().trim()
            if (binding.sugarUnit == TBSUtils.BloodSugarUnit.DL.toString()) {
                binding.sugarUnit = TBSUtils.BloodSugarUnit.L.toString()
                if (inputText.isNotBlank()) {
                    val numData = inputText.toDouble()
                    binding.edSugarNum.setText(
                        String.format("%.2f", TBSUtils.convertDlToL(numData))
                    )
                    binding.colorState =
                        TBSUtils.determineBloodSugarStatus(
                            currentStateSugar,
                            binding.edSugarNum.text.toString().toDouble()
                        ).toString()
                }

            } else {
                binding.sugarUnit = TBSUtils.BloodSugarUnit.DL.toString()
                if (inputText.isNotBlank()) {
                    val numData = inputText.toDouble()
                    binding.edSugarNum.setText(
                        String.format("%.2f", TBSUtils.convertLToDl(numData))
                    )
                    binding.colorState =
                        TBSUtils.determineBloodSugarStatus(
                            currentStateSugar,
                            numData
                        ).toString()
                }

            }
        }

        binding.imgSave.setOnClickListener {
            val num = binding.edSugarNum.text.toString().trim()
            if (num.isBlank()) {
                Toast.makeText(
                    this@SugarDetail,
                    "Please enter the amount of sugar",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            sugarBean = SugarBean(
                date = TBSUtils.getDateTime(System.currentTimeMillis().toString()),
                remarks = binding.etRecording.text.toString().trim(),
                currentState = currentStateSugar,
                state = TBSUtils.determineBloodSugarStatus(
                    currentStateSugar,
                    getSugarNumToL(num.toDouble())
                ),
                numL = getSugarNumToL(num.toDouble()),
                numDL = getSugarNumToDl(num.toDouble()),
                id = if (isEdit) {
                    sugarBean!!.id
                } else {
                    System.currentTimeMillis().toString()
                }
            )
            DataUtilsHelp.sugarUnit =
                if (binding.sugarUnit == TBSUtils.BloodSugarUnit.DL.toString()) {
                    TBSUtils.BloodSugarUnit.DL.toString()
                } else {
                    TBSUtils.BloodSugarUnit.L.toString()
                }
            if (isEdit) {
                viewModel.updateSugar(sugarBean!!)
            } else {
                viewModel.saveSugar(sugarBean!!)
            }
            finish()
        }

        binding.tvScene.setOnClickListener {
            binding.conDialog.isVisible = true
            TBSUtils.closeKeyboard(this)
        }
        binding.tvSugarStateNormal.setOnClickListener {
            clickCurrentState(TBSUtils.CurrentState.NORMAL)
        }
        binding.tvSugarStateBeforeMeals.setOnClickListener {
            clickCurrentState(TBSUtils.CurrentState.BEFORE_MEAL)
        }

        binding.tvSugarStateBeforeExercise.setOnClickListener {
            clickCurrentState(TBSUtils.CurrentState.BEFORE_EXERCISE)
        }
        binding.tvSugarStateAfterExercise.setOnClickListener {
            clickCurrentState(TBSUtils.CurrentState.AFTER_EXERCISE)
        }

        binding.tvSugarState1hour.setOnClickListener {
            clickCurrentState(TBSUtils.CurrentState.ONE_HOUR_AFTER_MEAL)
        }
        binding.tvSugarStateSleep.setOnClickListener {
            clickCurrentState(TBSUtils.CurrentState.ASLEEP)
        }

        binding.tvSugarState2hours.setOnClickListener {
            clickCurrentState(TBSUtils.CurrentState.TWO_HOURS_AFTER_MEAL)
        }
        binding.tvSugarStateBeforeFasting.setOnClickListener {
            clickCurrentState(TBSUtils.CurrentState.FASTING)

        }

        binding.imgCancel.setOnClickListener {
            binding.conDialog.isVisible = false
            clickCurrentState(TBSUtils.CurrentState.NORMAL)
        }
        binding.imgSaveDialog.setOnClickListener {
            binding.conDialog.isVisible = false
        }
        binding.edSugarNum.addTextChangedListener {
            if (!it.isNullOrBlank()) {
                if (binding.sugarUnit == TBSUtils.BloodSugarUnit.DL.toString()) {
                    binding.colorState =
                        TBSUtils.determineBloodSugarStatus(
                            currentStateSugar,
                            TBSUtils.convertDlToL(it.toString().toDouble())
                        ).toString()
                } else {
                    binding.colorState =
                        TBSUtils.determineBloodSugarStatus(
                            currentStateSugar,
                            it.toString().toDouble()
                        ).toString()
                }
                setTvSugarStateNum(binding?.colorState!!)
            }
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

    private fun clickCurrentState(currentState: TBSUtils.CurrentState) {
        binding.sugarCurrentState = currentState.toString()
        currentStateSugar = currentState
        binding.edSugarNum.text.toString().trim().let {
            if (it.isNotBlank()) {
                setColorState(currentStateSugar, it.toDouble())
            }
        }
    }

    private fun setColorState(currentState: TBSUtils.CurrentState, num: Double) {
        binding.colorState =
            TBSUtils.determineBloodSugarStatus(
                currentState,
                getSugarNumToL(num)
            ).toString()
    }

    private fun getSugarNumToL(num: Double): Double {
        val numData = if (binding.sugarUnit == TBSUtils.BloodSugarUnit.DL.toString()) {
            TBSUtils.convertDlToL(num)
        } else {
            num
        }
        return String.format("%.2f", numData).toDouble()
    }

    private fun getSugarNumToDl(num: Double): Double {
        val numData = if (binding.sugarUnit == TBSUtils.BloodSugarUnit.L.toString()) {
            TBSUtils.convertLToDl(num)
        } else {
            num
        }
        return String.format("%.2f", numData).toDouble()
    }

    private fun setTvSugarStateNum(bloodSugarStatus: String) {
        binding.tvSugarStateNum.text = bloodSugarStatus
        binding.tvSugarStateNum.setTextColor(TBSUtils.getSugarStateColorString(bloodSugarStatus))
    }

    private fun isEditShow() {
        if (isEdit) {
            sugarBean = viewModel.getSugarListData().find { it.id == pressureId }
            currentStateSugar = sugarBean?.currentState!!
            binding.sugarCurrentState = sugarBean?.currentState.toString()
            binding.sugarUnit = DataUtilsHelp.sugarUnit
            setTvSugarStateNum(sugarBean!!.state.toString())
            val numData = if (DataUtilsHelp.sugarUnit == TBSUtils.BloodSugarUnit.DL.toString()) {
                sugarBean!!.numDL
            } else {
                sugarBean!!.numL
            }
            binding.edSugarNum.setText(numData.toString())

            setColorState(
                sugarBean?.currentState!!,
                sugarBean?.numL!!
            )
            binding.tvDelete.isVisible = true
            binding.atvDate.text = "Datetime:${sugarBean?.date}"
            if (sugarBean!!.remarks.isNotBlank()) {
                binding.llEditRecording.isVisible = true
                binding.etRecording.setText(sugarBean?.remarks)
            } else {
                binding.llEditRecording.isVisible = false
            }
        } else {
            binding.sugarUnit = TBSUtils.BloodSugarUnit.L.toString()
            binding.sugarCurrentState = TBSUtils.CurrentState.NORMAL.toString()
            binding.atvDate.text =
                "Datetime:${TBSUtils.getDateTime(System.currentTimeMillis().toString())}"
            binding.tvDelete.isVisible = false
            binding.colorState = TBSUtils.BloodSugarStatus.NORMAL.toString()
        }
    }

    override fun observeViewModel() {
    }

    private fun showDeleteDialog(deleteFun: () -> Unit) {
        AlertDialog.Builder(this)
            .setTitle("Tip")
            .setMessage("Do You need to delete records?")
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