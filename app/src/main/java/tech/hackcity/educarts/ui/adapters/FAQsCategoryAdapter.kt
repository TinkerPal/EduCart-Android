package tech.hackcity.educarts.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.FaqsCategoryItemBinding
import tech.hackcity.educarts.domain.model.support.FaqCategory
import tech.hackcity.educarts.uitls.shortenString

class FAQsCategoryAdapter(private val context: Context) :
    RecyclerView.Adapter<FAQsCategoryAdapter.FAQsViewHolder>() {

    inner class FAQsViewHolder(val binding: FaqsCategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<FaqCategory>() {
        override fun areItemsTheSame(oldItem: FaqCategory, newItem: FaqCategory): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: FaqCategory, newItem: FaqCategory): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var faqCategoryList = emptyList<FaqCategory>()

    override fun getItemCount() = faqCategoryList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FAQsViewHolder {
        return FAQsViewHolder(
            FaqsCategoryItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FAQsViewHolder, position: Int) {
        holder.binding.apply {
            val faqCategory = faqCategoryList[position]
            if (faqCategory.image.isNullOrEmpty()) {
                image.setImageResource(R.drawable.logo)
            } else {
                Glide.with(context).load(faqCategory.image).into(image)
            }
            title.text = faqCategory.title
            description.text = shortenString(faqCategory.description, 90)

            holder.itemView.apply {
                setOnClickListener {
                    onItemClickListener?.let { it(faqCategory) }
                }
            }
        }
    }

    fun setData(faq: List<FaqCategory>) {
        this.faqCategoryList = faq
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((FaqCategory) -> Unit)? = null

    fun setOnItemClickListener(listener: (FaqCategory) -> Unit) {
        onItemClickListener = listener
    }
}