package com.practice.univapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.practice.univapp.databinding.HolderRvUniversityBinding
import com.practice.univapp.network.data.UniversityItem

class UniAdapter : RecyclerView.Adapter<UniAdapter.UniViewHolder>() {

    inner class UniViewHolder(val uniDataBinding : HolderRvUniversityBinding) : RecyclerView.ViewHolder(uniDataBinding.root)

    private val diffUtils = object : DiffUtil.ItemCallback<UniversityItem>(){
        override fun areItemsTheSame(oldItem: UniversityItem, newItem: UniversityItem): Boolean {
          return oldItem.name == newItem.name
        }
        override fun areContentsTheSame(oldItem: UniversityItem, newItem: UniversityItem): Boolean {
          return oldItem.name == newItem.name
        }
    }

    val asyncDiffList = AsyncListDiffer(this, diffUtils)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniAdapter.UniViewHolder {
     val binding = HolderRvUniversityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
     return UniViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UniAdapter.UniViewHolder, position: Int) {
      val uniBinding  = holder.uniDataBinding
      val item = asyncDiffList.currentList[position]

      uniBinding.apply {
        tvUniName.text = item.name
        tvUniCountry.text = item.country
        tvUniCountry.visibility = if (item.country != null) View.VISIBLE else View.GONE
        tvUniState.text = item.stateProvince
        tvUniState.visibility = if (item.stateProvince != null) View.VISIBLE else View.GONE
      }
    }

    override fun getItemCount(): Int {
      return asyncDiffList.currentList.size
    }
}