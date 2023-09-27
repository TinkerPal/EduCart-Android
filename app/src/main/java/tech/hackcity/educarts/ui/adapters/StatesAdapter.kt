package tech.hackcity.educarts.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tech.hackcity.educarts.databinding.RegionItemBinding
import tech.hackcity.educarts.domain.model.location.Country
import tech.hackcity.educarts.domain.model.location.State

class StatesAdapter() :
    RecyclerView.Adapter<StatesAdapter.RegionViewHolder>() {

    inner class RegionViewHolder(val binding: RegionItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<State>() {
        override fun areItemsTheSame(oldItem: State, newItem: State): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: State, newItem: State): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var regionList = emptyList<State>()

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

    fun setData(region: MutableList<State>) {
        this.regionList = region
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((State) -> Unit)? = null

    fun setOnItemClickListener(listener: (State) -> Unit) {
        onItemClickListener = listener
    }
}