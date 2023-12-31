package tech.hackcity.educarts.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.ItemPaymentBinding
import tech.hackcity.educarts.domain.model.history.OrderHistoryResponseData
import tech.hackcity.educarts.uitls.formatDateTime

class AllPaymentAdapter(private val context: Context) :
    RecyclerView.Adapter<AllPaymentAdapter.AllPaymentViewHolder>() {

    inner class AllPaymentViewHolder(val binding: ItemPaymentBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<OrderHistoryResponseData>() {
        override fun areItemsTheSame(oldItem: OrderHistoryResponseData, newItem: OrderHistoryResponseData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OrderHistoryResponseData, newItem: OrderHistoryResponseData): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var paymentHistoryList = emptyList<OrderHistoryResponseData>()

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

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AllPaymentViewHolder, position: Int) {
        holder.binding.apply {

            val paymentHistory = paymentHistoryList[position]
            transactionId.text = paymentHistory.order_id
            service.text = paymentHistory.order_type
            date.text = formatDateTime(paymentHistory.date)
            transactionStatus.text = paymentHistory.status

            val orderStatus = when (paymentHistory.status) {
                "Order completed" -> OrderStatus.ORDER_COMPLETED
                "Order in process" -> OrderStatus.ORDER_IN_PROCESS
                "Payment Pending" -> OrderStatus.PAYMENT_PENDING
                "Payment confirmed" -> OrderStatus.PAYMENT_CONFIRMED
                else -> OrderStatus.ORDER_COMPLETED
            }

            transactionStatus.setTextColor(
                ContextCompat.getColor(
                    context,
                    orderStatus.textColourRes
                )
            )
            icon.setColorFilter(ContextCompat.getColor(context, orderStatus.iconColourRes))

            holder.itemView.apply {
                setOnClickListener {
                    onItemClickListener?.let { it(paymentHistory) }
                }
            }
        }

    }

    fun setData(paymentHistories: List<OrderHistoryResponseData>) {
        this.paymentHistoryList = paymentHistories
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((OrderHistoryResponseData) -> Unit)? = null
    private var onViewAllClickListener: ((List<OrderHistoryResponseData>) -> Unit)? = null

    fun setOnItemClickListener(listener: (OrderHistoryResponseData) -> Unit) {
        onItemClickListener = listener
    }
    fun setOnViewAllClickListener(listener: (List<OrderHistoryResponseData>) -> Unit) {
        onViewAllClickListener = listener
    }

}

enum class OrderStatus(val textColourRes: Int, val iconColourRes: Int) {
    ORDER_COMPLETED(R.color.success_green, R.color.success_green),
    ORDER_IN_PROCESS(R.color.primary_color, R.color.primary_color),
    PAYMENT_PENDING(R.color.error_600, R.color.error_600),
    PAYMENT_CONFIRMED(R.color.secondary_color, R.color.secondary_color)
}