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
import tech.hackcity.educarts.databinding.SchoolItemBinding
import tech.hackcity.educarts.domain.model.payment.School

class SchoolsAdapter(private val context: Context) :
    RecyclerView.Adapter<SchoolsAdapter.SchoolsViewHolder>() {

    inner class SchoolsViewHolder(val binding: SchoolItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<School>() {
        override fun areItemsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: School, newItem: School): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var schoolList = emptyList<School>()

    override fun getItemCount() = schoolList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SchoolsViewHolder {
        return SchoolsViewHolder(
            SchoolItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SchoolsViewHolder, position: Int) {
        holder.binding.apply {
            val school = schoolList[position]
            if (school.flag.isNullOrEmpty()) {
                flag.visibility = View.INVISIBLE
            } else {
                Glide.with(context).load(school.flag).into(flag)
            }
            name.text = school.name
            state.text = school.state
            countryCode.text = school.countryCode

            holder.itemView.apply {
                setOnClickListener {
                    onItemClickListener?.let { it(school) }
                }
            }
        }

    }

    fun setData(schools: List<School>) {
        differ.submitList(schools)
    }

    private var onItemClickListener: ((School) -> Unit)? = null

    fun setOnItemClickListener(listener: (School) -> Unit) {
        onItemClickListener = listener
    }
}