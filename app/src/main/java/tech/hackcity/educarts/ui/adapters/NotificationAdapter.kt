package tech.hackcity.educarts.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.NotificationItemBinding
import tech.hackcity.educarts.domain.model.notification.Notification
import tech.hackcity.educarts.domain.model.payment.School

class NotificationAdapter(private val context: Context) :
    RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder>() {

    inner class NotificationViewHolder(val binding: NotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var notificationList = emptyList<Notification>()

    override fun getItemCount() = notificationList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        return NotificationViewHolder(
            NotificationItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        holder.binding.apply {
            val notification = notificationList[position]
            if (notification.image.isEmpty()) {
                imageView.setImageResource(R.drawable.notification)
            } else {
                Glide.with(context).load(notification.image).into(imageView)
            }
            title.text = notification.title
            description.text = notification.description

            holder.itemView.apply {
                setOnClickListener {
                    onItemClickListener?.let { it(notification) }
                }
            }
        }

    }

    fun setData(notifications: List<Notification>) {
        this.notificationList = notifications
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((Notification) -> Unit)? = null

    fun setOnItemClickListener(listener: (Notification) -> Unit) {
        onItemClickListener = listener
    }
}