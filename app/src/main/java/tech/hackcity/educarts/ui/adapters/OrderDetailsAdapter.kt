package tech.hackcity.educarts.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.OrderStatusItemBinding
import tech.hackcity.educarts.domain.model.history.OrderDetails
import tech.hackcity.educarts.uitls.formatDateTime

class OrderDetailsAdapter(private val context: Context) :
    RecyclerView.Adapter<OrderDetailsAdapter.OrderStatusViewHolder>() {

    inner class OrderStatusViewHolder(val binding: OrderStatusItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<OrderDetails>() {
        override fun areItemsTheSame(oldItem: OrderDetails, newItem: OrderDetails): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: OrderDetails, newItem: OrderDetails): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var orderDetailsList = emptyList<OrderDetails>()

    override fun getItemCount() = orderDetailsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderStatusViewHolder {
        return OrderStatusViewHolder(
            OrderStatusItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: OrderStatusViewHolder, position: Int) {
        holder.binding.apply {

            val orderHistory = orderDetailsList[position]
            titleTV.text = orderHistory.statusTitle
            descriptionTV.text = orderHistory.statusDescription
            dateTV.text = formatDateTime(orderHistory.date)

            when(orderHistory.step) {
                1 -> activeImageView.setImageResource(R.drawable.done_with_circle_border)
                2 -> activeImageView.setImageResource(R.drawable.done_with_circle_border)
                3 -> activeImageView.setImageResource(R.drawable.done_with_circle_border)
                4 -> activeImageView.setImageResource(R.drawable.done_with_circle_border)
            }
        }

    }

    fun setData(paymentHistories: List<OrderDetails>) {
        this.orderDetailsList = paymentHistories
        notifyDataSetChanged()
    }

}
