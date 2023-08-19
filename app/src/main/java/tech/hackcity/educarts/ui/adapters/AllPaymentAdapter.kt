package tech.hackcity.educarts.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tech.hackcity.educarts.R
import tech.hackcity.educarts.domain.model.payment.PaymentHistory
import tech.hackcity.educarts.databinding.ItemPaymentBinding
import java.util.Locale

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

            val paymentStatus = when (paymentHistory.status) {
                "Order completed" -> PaymentStatus.ORDER_COMPLETED
                "Order in process" -> PaymentStatus.ORDER_IN_PROCESS
                "Payment confirmed" -> PaymentStatus.PAYMENT_CONFIRMED
                else -> PaymentStatus.ORDER_COMPLETED
            }

            transactionStatus.setTextColor(
                ContextCompat.getColor(
                    context,
                    paymentStatus.textColourRes
                )
            )
            icon.setColorFilter(ContextCompat.getColor(context, paymentStatus.iconColourRes))

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

enum class PaymentStatus(val textColourRes: Int, val iconColourRes: Int) {
    ORDER_COMPLETED(R.color.success_green, R.color.success_green),
    ORDER_IN_PROCESS(R.color.primary_color, R.color.primary_color),
    PAYMENT_CONFIRMED(R.color.secondary_color, R.color.secondary_color)
}