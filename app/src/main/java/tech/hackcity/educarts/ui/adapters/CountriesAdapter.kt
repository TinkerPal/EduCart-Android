package tech.hackcity.educarts.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tech.hackcity.educarts.databinding.RegionItemBinding
import tech.hackcity.educarts.domain.model.location.Country

class CountriesAdapter() :
    RecyclerView.Adapter<CountriesAdapter.RegionViewHolder>() {

    inner class RegionViewHolder(val binding: RegionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var regionList = emptyList<Country>()

    override fun getItemCount() = regionList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegionViewHolder {
        return RegionViewHolder(
            RegionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RegionViewHolder, position: Int) {
        holder.binding.apply {
            val region = regionList[position]

            textView.text = region.name

            holder.itemView.setOnClickListener {
                onItemClickListener?.invoke(region)
            }
        }
    }

    fun setData(region: MutableList<Country>) {
        this.regionList = region
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((Country) -> Unit)? = null

    fun setOnItemClickListener(listener: (Country) -> Unit) {
        onItemClickListener = listener
    }
}