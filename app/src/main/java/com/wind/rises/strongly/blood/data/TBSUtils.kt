package com.wind.rises.strongly.blood.data

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.inputmethod.InputMethodManager
import com.wind.rises.strongly.blood.R
import java.text.SimpleDateFormat
import java.util.Date

object TBSUtils {

    fun checkBloodPressure(systolic: Int, diastolic: Int): Int {
        return when {
            systolic < 90 || diastolic < 60 -> 1
            (systolic in 90..119) && (diastolic in 60..79) -> 2
            systolic in 120..129 && diastolic < 80 -> 3
            (systolic in 130..139) || (diastolic in 80..89) -> 4
            (systolic in 140..180) || (diastolic in 90..120) -> 5
            systolic >= 180 || diastolic >= 120 -> 6
            else -> 1
        }
    }

    fun getBloodPressureTip(state: Int): String {
        return when (state) {
            1 -> "Hypotension: Please contact your doctor."
            2 -> "Normal: Your blood pressure is within the normal range."
            3 -> "Elevated: Please watch your blood pressure. It's a little high."
            4 -> "Hypertension Stage 1: Please contact your doctor to schedule an appropriate medical examination."
            5 -> "Hypertension Stage 2: Please contact your doctor as soon as possible."
            6 -> "Hypertension Crisis: Please call the hospital."
            else -> "Invalid blood pressure readings."
        }
    }

    fun getBloodPressureState(state: Int): String {
        return when (state) {
            1 -> "Hypotension"
            2 -> "Normal"
            3 -> "Elevated"
            4 -> "Hypertension Stage 1"
            5 -> "Hypertension Stage 2"
            6 -> "Hypertension Crisis"
            else -> "Invalid"
        }
    }

    fun getBloodPressureStateColor(state: Int): Int {
        return when (state) {
            1 -> Color.parseColor("#0180F8")
            2 -> Color.parseColor("#3AC34A")
            3 -> Color.parseColor("#DBB60A")
            4 -> Color.parseColor("#EE9102")
            5 -> Color.parseColor("#F57602")
            6 -> Color.parseColor("#FE5001")
            else -> Color.parseColor("#0180F8")
        }
    }

    fun getBloodPressureStateImage(state: Int): Int {
        return when (state) {
            1 -> R.drawable.circle_1
            2 -> R.drawable.circle_2
            3 -> R.drawable.circle_3
            4 -> R.drawable.circle_4
            5 -> R.drawable.circle_5
            6 -> R.drawable.circle_6
            else -> R.drawable.circle_2
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateTime(currentTimeMillis: String): String {
        val now1 = Date(currentTimeMillis.toLong())
        val format = SimpleDateFormat("yyyy-MM-dd")
        return format.format(now1)
    }


    enum class BloodSugarUnit {
        DL, L
    }

    enum class BloodSugarStatus {
        LOW, NORMAL, PREDIABETES, DIABETES
    }

    enum class CurrentState {
        NORMAL,
        BEFORE_EXERCISE,
        BEFORE_MEAL,
        FASTING,
        ONE_HOUR_AFTER_MEAL,
        TWO_HOURS_AFTER_MEAL,
        AFTER_EXERCISE,
        ASLEEP
    }

    fun determineBloodSugarStatus(
        currentState: CurrentState,
        bloodSugarValue: Double
    ): BloodSugarStatus {
        return when (currentState) {
            CurrentState.NORMAL,
            CurrentState.BEFORE_EXERCISE,
            CurrentState.BEFORE_MEAL,
            CurrentState.FASTING,
            CurrentState.AFTER_EXERCISE,
            CurrentState.ASLEEP -> {
                when {
                    bloodSugarValue < 4 -> BloodSugarStatus.LOW
                    bloodSugarValue < 5.5 -> BloodSugarStatus.NORMAL
                    bloodSugarValue < 7 -> BloodSugarStatus.PREDIABETES
                    else -> BloodSugarStatus.DIABETES
                }
            }

            CurrentState.ONE_HOUR_AFTER_MEAL -> {
                when {
                    bloodSugarValue < 4 -> BloodSugarStatus.LOW
                    bloodSugarValue < 7.8 -> BloodSugarStatus.NORMAL
                    bloodSugarValue < 8.5 -> BloodSugarStatus.PREDIABETES
                    else -> BloodSugarStatus.DIABETES
                }
            }

            CurrentState.TWO_HOURS_AFTER_MEAL -> {
                when {
                    bloodSugarValue < 4 -> BloodSugarStatus.LOW
                    bloodSugarValue < 4.7 -> BloodSugarStatus.NORMAL
                    bloodSugarValue < 7 -> BloodSugarStatus.PREDIABETES
                    else -> BloodSugarStatus.DIABETES
                }
            }
        }
    }


    fun getSugarStateImage(bloodSugarStatus: BloodSugarStatus?,stringInt:String?): Int {
        if(stringInt!=null){
            return when (stringInt) {
                BloodSugarStatus.LOW.toString() -> R.drawable.circle_1
                BloodSugarStatus.NORMAL.toString() -> R.drawable.circle_2
                BloodSugarStatus.PREDIABETES.toString() -> R.drawable.circle_4
                BloodSugarStatus.DIABETES.toString() -> R.drawable.circle_6
                else -> R.drawable.circle_2
            }
        }else{
            return when (bloodSugarStatus) {
                BloodSugarStatus.LOW -> R.drawable.circle_1
                BloodSugarStatus.NORMAL -> R.drawable.circle_2
                BloodSugarStatus.PREDIABETES -> R.drawable.circle_4
                BloodSugarStatus.DIABETES -> R.drawable.circle_6
                else -> R.drawable.circle_2
            }
        }
    }



    fun getSugarStateColor(bloodSugarStatus: BloodSugarStatus): Int {
        return when (bloodSugarStatus) {
            BloodSugarStatus.LOW -> Color.parseColor("#0180F8")
            BloodSugarStatus.NORMAL -> Color.parseColor("#3AC34A")
            BloodSugarStatus.PREDIABETES -> Color.parseColor("#EE9102")
            BloodSugarStatus.DIABETES -> Color.parseColor("#FE5001")
        }
    }
    fun getSugarStateColorString(bloodSugarStatus: String): Int {
        return when (bloodSugarStatus) {
            "LOW" -> Color.parseColor("#0180F8")
            "NORMAL" -> Color.parseColor("#3AC34A")
            "PREDIABETES" -> Color.parseColor("#EE9102")
            "DIABETES" -> Color.parseColor("#FE5001")
            else -> Color.parseColor("#3AC34A")
        }
    }

    fun getSugarCurrentState(currentState: CurrentState): String {
        return when (currentState) {
            CurrentState.NORMAL -> "Normal"
            CurrentState.BEFORE_EXERCISE -> "Before Exercise"
            CurrentState.BEFORE_MEAL -> "Before Meal"
            CurrentState.FASTING ->  "Fasting"
            CurrentState.ONE_HOUR_AFTER_MEAL ->  "1HourAfterMeal"
            CurrentState.TWO_HOURS_AFTER_MEAL ->  "2HoursAfterMeal"
            CurrentState.AFTER_EXERCISE ->  "AfterExercise"
            CurrentState.ASLEEP ->  "Sleep"
        }
    }
    fun convertDlToL(value: Double): Double {
        return value * 0.0555
    }

    fun convertLToDl(value: Double): Double {
        return value / 0.0555
    }


    fun closeKeyboard(activity: Activity) {
        val view = activity.window.peekDecorView()
        if (view != null) {
            val inputMethodManager =
                activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}