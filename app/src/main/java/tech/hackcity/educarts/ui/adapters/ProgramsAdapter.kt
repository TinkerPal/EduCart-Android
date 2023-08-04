package tech.hackcity.educarts.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tech.hackcity.educarts.databinding.ProgramItemBinding
import tech.hackcity.educarts.domain.model.payment.Program

class ProgramsAdapter(private val context: Context) :
    RecyclerView.Adapter<ProgramsAdapter.ProgramViewHolder>() {

    inner class ProgramViewHolder(val binding: ProgramItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<Program>() {
        override fun areItemsTheSame(oldItem: Program, newItem: Program): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Program, newItem: Program): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var programList = emptyList<Program>()

    override fun getItemCount() = programList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        return ProgramViewHolder(
            ProgramItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        holder.binding.apply {
            val program = programList[position]
            duration.text = program.duration
            course.text = program.course
            val uniAndState = "${program.university} - ${program.state}"
            universityAndState.text = uniAndState
            tuitionFeeTV.text = program.tuitionFee
            applicationFeeTV.text = program.applicationFee

            holder.itemView.apply {
                setOnClickListener {
                    onItemClickListener?.let { it(program) }
                }
            }
        }

    }

    fun setData(programs: List<Program>) {
        differ.submitList(programs)
    }

    private var onItemClickListener: ((Program) -> Unit)? = null

    fun setOnItemClickListener(listener: (Program) -> Unit) {
        onItemClickListener = listener
    }
}