package com.wind.rises.strongly.blood.ui.sver

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import com.wind.rises.strongly.blood.R
import com.wind.rises.strongly.blood.data.AllUtils
import com.wind.rises.strongly.blood.databinding.ActivitySDBinding
import com.wind.rises.strongly.blood.data.DataUtilsHelp
import com.wind.rises.strongly.blood.data.SugarBean
import com.wind.rises.strongly.blood.data.TBSUtils

class SugarDetail : AppCompatActivity() {
    private val binding by lazy { ActivitySDBinding.inflate(layoutInflater) }

    private var isEdit = false
    private var pressureId = ""
    private var sugarBean: SugarBean? = null
    private var currentStateSugar = TBSUtils.CurrentState.NORMAL
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupViews()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setupViews() {
        isEdit = intent.getBooleanExtra("isEdit", false)
        pressureId = intent.getStringExtra("sugarId").toString()
        isEditShow()
        binding.conDialog.setOnClickListener { }
        binding.tvRecord.setOnClickListener {
            finish()
        }
        binding.tvSugarUnit.setOnClickListener {
            val inputText = binding.edSugarNum.text.toString().trim()
            if (binding.sugarUnit == TBSUtils.BloodSugarUnit.DL.toString()) {
                binding.sugarUnit = TBSUtils.BloodSugarUnit.L.toString()
                binding.tvSugarUnit.text = getString(R.string.text_l)
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
                Log.e("TAG", "setupViews: ${getString(R.string.text_dl)}")
                binding.tvSugarUnit.text = getString(R.string.text_dl)
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
                AllUtils.updateSugar(sugarBean!!)
            } else {
                AllUtils.saveSugar(sugarBean!!)
            }
            finish()
        }

        binding.tvBefore.setOnClickListener {
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

    }

    private fun clickCurrentState(currentState: TBSUtils.CurrentState) {
        binding.sugarCurrentState = currentState.toString()
        currentStateSugar = currentState
        binding.edSugarNum.text.toString().trim().let {
            if (it.isNotBlank()) {
                setColorState(currentStateSugar, it.toDouble())
            }
        }
        binding.tvDetailState.text = binding?.colorState
        binding.tvDetailState.setTextColor(TBSUtils.getSugarStateColorString(binding?.colorState.toString()))
        binding.imgCc.setImageResource(TBSUtils.getSugarStateImage(null, binding?.colorState))
        setIntState()
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
        binding.tvDetailState.text = bloodSugarStatus
        binding.tvDetailState.setTextColor(TBSUtils.getSugarStateColorString(bloodSugarStatus))
        binding.imgCc.setImageResource(TBSUtils.getSugarStateImage(null, bloodSugarStatus))
        setIntState()
    }

    private fun isEditShow() {
        binding.tvSugarUnit.text =
            if (DataUtilsHelp.sugarUnit == TBSUtils.BloodSugarUnit.DL.toString()) {
                getString(R.string.text_dl)
            } else {
                getString(R.string.text_l)
            }
        if (isEdit) {
            sugarBean = AllUtils.getSugarListData().find { it.id == pressureId }
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
            binding.tvDetailDate.text = "Datetime:${sugarBean?.date}"
            binding.colorState = sugarBean?.state.toString()

        } else {
            binding.sugarUnit = TBSUtils.BloodSugarUnit.L.toString()
            binding.sugarCurrentState = TBSUtils.CurrentState.NORMAL.toString()
            binding.tvDetailDate.text =
                "Datetime:${TBSUtils.getDateTime(System.currentTimeMillis().toString())}"
            binding.colorState = TBSUtils.BloodSugarStatus.NORMAL.toString()
        }
        setIntState()
    }

    private fun setIntState(){
        binding.colorInt = when (binding?.colorState) {
            "LOW" -> 1
            "NORMAL" -> 2
            "PREDIABETES" -> 3
            else -> 4
        }
        binding.sugarCurrentInt = when (binding?.sugarCurrentState) {
            "NORMAL" -> 1
            "BEFORE_MEAL" -> 2
            "BEFORE_EXERCISE" -> 3
            "AFTER_EXERCISE" -> 4
            "ONE_HOUR_AFTER_MEAL" -> 5
            "ASLEEP" -> 6
            "TWO_HOURS_AFTER_MEAL" -> 7
            "FASTING" -> 8
            else -> 1
        }
    }

}