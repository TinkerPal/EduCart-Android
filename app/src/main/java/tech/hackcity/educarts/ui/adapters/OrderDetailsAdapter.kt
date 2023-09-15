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
import tech.hackcity.educarts.domain.model.history.OrderStage
import tech.hackcity.educarts.uitls.formatDateTime

class OrderDetailsAdapter(private val context: Context) :
    RecyclerView.Adapter<OrderDetailsAdapter.OrderStatusViewHolder>() {

    inner class OrderStatusViewHolder(val binding: OrderStatusItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<OrderStage>() {
        override fun areItemsTheSame(oldItem: OrderStage, newItem: OrderStage): Boolean {
            return oldItem.stage == newItem.stage
        }

        override fun areContentsTheSame(oldItem: OrderStage, newItem: OrderStage): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var orderStageList = emptyList<OrderStage>()

    override fun getItemCount() = orderStageList.size

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

            val orderStage = orderStageList[position]
            titleTV.text = orderStage.stage
            descriptionTV.text = orderStage.description
            dateTV.text = orderStage.date?.let { formatDateTime(it) }

            if (orderStage.is_completed) {
                activeImageView.setImageResource(R.drawable.done_with_circle_border)
            }
        }

    }

    fun setData(orderDetails: List<OrderStage>) {
        this.orderStageList = orderDetails
        notifyDataSetChanged()
    }

}
