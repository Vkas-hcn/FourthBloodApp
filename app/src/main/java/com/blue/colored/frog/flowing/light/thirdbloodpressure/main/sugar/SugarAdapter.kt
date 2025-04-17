package com.blue.colored.frog.flowing.light.thirdbloodpressure.main.sugar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.blue.colored.frog.flowing.light.thirdbloodpressure.R
import com.blue.colored.frog.flowing.light.thirdbloodpressure.key.DataUtilsHelp
import com.blue.colored.frog.flowing.light.thirdbloodpressure.key.SugarBean
import com.blue.colored.frog.flowing.light.thirdbloodpressure.key.TBSUtils
import java.math.BigDecimal
import java.math.RoundingMode

class SugarAdapter(private var dataList: List<SugarBean>) :
    RecyclerView.Adapter<SugarAdapter.ViewHolder>() {
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var listener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgEdit: ImageView = itemView.findViewById(R.id.img_edit)
        val imgItemState: ImageView = itemView.findViewById(R.id.img_item_state)
        val tvItemState: TextView = itemView.findViewById(R.id.tv_item_state)
        val tvItemDate: TextView = itemView.findViewById(R.id.tv_item_date)
        val tvSugarNum: TextView = itemView.findViewById(R.id.tv_sugar_num)
        val imgItemImage: ImageView = itemView.findViewById(R.id.img_item_image)
        val tvItemImageState: TextView = itemView.findViewById(R.id.tv_item_image_state)
        val tvSugarUnit: TextView = itemView.findViewById(R.id.tv_sugar_unit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_sugar, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemData = dataList[position]

        val state = TBSUtils.determineBloodSugarStatus(
            itemData.currentState,
            itemData.numL
        )
        holder.imgItemState.setImageResource(TBSUtils.getSugarStateImage(state))
        holder.tvItemState.text = state.toString()
        holder.tvItemState.setTextColor(TBSUtils.getSugarStateColor(state))
        holder.tvItemDate.text = itemData.date
        holder.tvSugarNum.text = getSugarNum(itemData).toString()
        holder.imgItemImage.setImageResource(TBSUtils.getSugarCurrentStateImage(itemData.currentState))
        holder.tvItemImageState.text = TBSUtils.getSugarCurrentState(itemData.currentState)
        holder.tvSugarUnit.text = if (DataUtilsHelp.sugarUnit == TBSUtils.BloodSugarUnit.DL.toString()) {
            "mg/dl"
        } else {
            "mmol/l"
        }
        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun setDataList(dataList: List<SugarBean>) {
        this.dataList = dataList
        notifyDataSetChanged()
    }

    private fun getSugarNum(sugarBean:SugarBean): Double {
        val numData = if (DataUtilsHelp.sugarUnit == TBSUtils.BloodSugarUnit.DL.toString()) {
            sugarBean.numDL
        } else {
            sugarBean.numL
        }
        return String.format("%.2f", numData).toDouble()
    }
}