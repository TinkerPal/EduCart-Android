package tech.hackcity.educarts.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import tech.hackcity.educarts.R
import tech.hackcity.educarts.domain.model.FAQ
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
            val faq = faqList[position]
            question.text = faq.question
            answer.text = faq.answer

            questionCard.setOnClickListener {
                if (hiddenView.visibility == View.VISIBLE) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(questionCard, AutoTransition())
                    }
                    hiddenView.visibility = View.GONE
                    arrowButton.setImageResource(R.drawable.arrow_forward)
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        TransitionManager.beginDelayedTransition(questionCard, AutoTransition())
                    }
                    hiddenView.visibility = View.VISIBLE
                    arrowButton.setImageResource(R.drawable.arrow_down)
                }
                onItemClickListener?.let {it(faq)}
            }
        }

    }

    fun setData(faq: List<FAQ>) {
        differ.submitList(faq)
    }

    private var onItemClickListener: ((FAQ) -> Unit)? = null

    fun setOnItemClickListener(listener: (FAQ) -> Unit) {
        onItemClickListener = listener
    }
}