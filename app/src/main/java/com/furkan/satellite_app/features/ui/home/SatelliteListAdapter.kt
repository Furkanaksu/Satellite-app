package com.furkan.satellite_app.features.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.furkan.satellite_app.data.model.SatelliteUIModel
import com.furkan.satellite_app.databinding.ItemSatelliteListBinding
import com.furkan.satellite_app.features.base.BaseAdapter
import com.furkan.satellite_app.utils.getDiffUtilCallBack


class SatelliteListAdapter(
    private val onClick: (SatelliteUIModel) -> Unit
    ) :
    BaseAdapter<SatelliteUIModel, RecyclerView.ViewHolder>(
        getDiffUtilCallBack()
    ) {

    private val dataList: ArrayList<SatelliteUIModel> = arrayListOf()


    override fun bindView(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SatelliteViewHolder).bind(dataList[position])
    }

    override fun createView(
        context: Context,
        parent: ViewGroup,
        inflater: LayoutInflater,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return SatelliteViewHolder(
            ItemSatelliteListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    inner class SatelliteViewHolder constructor(
        private val binding: ItemSatelliteListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: SatelliteUIModel) {
            with(binding) {
                data.activeImg?.let { statusImg.setImageResource(it) }

                statusTv.apply {
                    text = data.activeText
                    if (data.active == false) {
                        nameTv.setTextColor(Color.GRAY)
                        statusTv.setTextColor(Color.GRAY)
                    }
                }

                nameTv.text = data.name

                root.setOnClickListener {
                    onClick.invoke(data)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateItemList(itemList: MutableList<SatelliteUIModel?>?) {
        this.dataList.clear()
        itemList?.let {
            this.dataList.addAll(itemList.filterNotNull())
        }
        notifyDataSetChanged()
    }
}