package com.example.weatherapp.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.model.presentation.DailyViewData

class ForecastAdapter : RecyclerView.Adapter<ForecastViewHolder>() {
    private var items = mutableListOf<DailyViewData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.forecast_item, parent, false)
        return ForecastViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun submitList(items: List<DailyViewData>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}