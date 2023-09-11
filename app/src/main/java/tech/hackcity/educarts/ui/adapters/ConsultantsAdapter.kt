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
import tech.hackcity.educarts.databinding.ConsultantItemBinding
import tech.hackcity.educarts.domain.model.support.Consultant

class ConsultantsAdapter() :
    RecyclerView.Adapter<ConsultantsAdapter.ConsultantsViewHolder>() {

    inner class ConsultantsViewHolder(val binding: ConsultantItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Consultant>() {
        override fun areItemsTheSame(oldItem: Consultant, newItem: Consultant): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Consultant, newItem: Consultant): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var consultantList = emptyList<Consultant>()

    override fun getItemCount() = consultantList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConsultantsViewHolder {
        return ConsultantsViewHolder(
            ConsultantItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ConsultantsViewHolder, position: Int) {
        val consultant = consultantList[position]
        val context = holder.itemView.context
        val resources = context.resources

        with(holder.binding) {
            if (consultant.profile_picture.isNullOrEmpty()) {
                profileImageView.setImageResource(R.drawable.default_profile)
            } else {
                Glide.with(context).load(consultant.profile_picture).into(profileImageView)
            }

            priceTV.text = "$${consultant.price_per_hour?.takeIf { true } ?: resources.getString(R.string.nil)}/hour"
            fullNameTV.text = consultant.name?.takeIf { it.isNotEmpty() } ?: resources.getString(R.string.nil)
            specializationTV.text = consultant.specialization?.takeIf { it.isNotEmpty() } ?: resources.getString(R.string.nil)
            experienceTV.text = "${consultant.years_of_experience?.takeIf { true } ?: resources.getString(R.string.nil)} years"

            val locationText = "${consultant.state?.takeIf { it.isNotEmpty() } ?: resources.getString(R.string.nil)}, ${consultant.country?.takeIf { it.isNotEmpty() } ?: resources.getString(R.string.nil)}"
            locationTV.text = locationText

            availabilityTV.text = consultant.availability?.takeIf { it.isNotEmpty() } ?: resources.getString(R.string.nil)

            when (consultant.rate) {
                1 -> star1.setImageResource(R.drawable.rating_star_filled)
                2 -> star2.setImageResource(R.drawable.rating_star_filled)
                3 -> star3.setImageResource(R.drawable.rating_star_filled)
                4 -> star4.setImageResource(R.drawable.rating_star_filled)
                5 -> star5.setImageResource(R.drawable.rating_star_filled)
            }

            holder.itemView.setOnClickListener {
                onItemClickListener?.invoke(consultant)
            }
        }
    }


    fun setData(notifications: List<Consultant>) {
        this.consultantList = notifications
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((Consultant) -> Unit)? = null

    fun setOnItemClickListener(listener: (Consultant) -> Unit) {
        onItemClickListener = listener
    }
}