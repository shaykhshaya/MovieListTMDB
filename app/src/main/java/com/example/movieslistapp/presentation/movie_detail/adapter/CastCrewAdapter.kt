package com.example.movieslistapp.presentation.movie_detail.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieslistapp.data.remote.ProductionCompany
import com.example.movieslistapp.databinding.RowItemCastCrewBinding

class CastCrewAdapter(
    private var mList: ArrayList<ProductionCompany>
) : RecyclerView.Adapter<CastCrewAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RowItemCastCrewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProductionCompany) {
            binding.apply {
                tvTitle.text = item.name
                tvSubtitle.text = item.originCountry
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowItemCastCrewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}


