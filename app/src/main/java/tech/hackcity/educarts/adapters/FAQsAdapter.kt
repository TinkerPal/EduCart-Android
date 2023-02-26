package tech.hackcity.educarts.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tech.hackcity.educarts.data.model.AddressBook
import tech.hackcity.educarts.data.model.FAQ
import tech.hackcity.educarts.databinding.FaqsItemBinding

class FAQsAdapter(private val context: Context) :
    RecyclerView.Adapter<FAQsAdapter.FAQsViewHolder>() {

    inner class FAQsViewHolder(val binding: FaqsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<FAQ>() {
        override fun areItemsTheSame(oldItem: FAQ, newItem: FAQ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FAQ, newItem: FAQ): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var faqList = emptyList<FAQ>()

    override fun getItemCount() = faqList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQsViewHolder {
        return FAQsViewHolder(
            FaqsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FAQsViewHolder, position: Int) {
        holder.binding.apply {
            val fa = faqList[position]

        }

    }

    fun setData(faq: List<FAQ>) {
        this.faqList = faq
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((FAQ) -> Unit)? = null

    fun setOnItemClickListener(listener: (FAQ) -> Unit) {
        onItemClickListener = listener
    }
}