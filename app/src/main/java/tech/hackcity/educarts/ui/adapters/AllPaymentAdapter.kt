package tech.hackcity.educarts.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tech.hackcity.educarts.domain.model.payment.PaymentHistory
import tech.hackcity.educarts.databinding.ItemPaymentBinding

class AllPaymentAdapter(private val context: Context) :
    RecyclerView.Adapter<AllPaymentAdapter.AllPaymentViewHolder>() {

    inner class AllPaymentViewHolder(val binding: ItemPaymentBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<PaymentHistory>() {
        override fun areItemsTheSame(oldItem: PaymentHistory, newItem: PaymentHistory): Boolean {
            return oldItem.transactionId == newItem.transactionId
        }

        override fun areContentsTheSame(oldItem: PaymentHistory, newItem: PaymentHistory): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var paymentHistoryList = emptyList<PaymentHistory>()

    override fun getItemCount() = paymentHistoryList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllPaymentViewHolder {
        return AllPaymentViewHolder(
            ItemPaymentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AllPaymentViewHolder, position: Int) {
        holder.binding.apply {
            val paymentHistory = paymentHistoryList[position]
            transactionId.text = paymentHistory.transactionId
            service.text = paymentHistory.service
            date.text = paymentHistory.date
            transactionStatus.text = paymentHistory.status

            holder.itemView.apply {
                setOnClickListener {
                    onItemClickListener?.let { it(paymentHistory) }

                }
            }
        }

    }

    fun setData(paymentHistories: List<PaymentHistory>) {
        this.paymentHistoryList = paymentHistories
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((PaymentHistory) -> Unit)? = null

    fun setOnItemClickListener(listener: (PaymentHistory) -> Unit) {
        onItemClickListener = listener
    }
}