package tech.hackcity.educarts.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tech.hackcity.educarts.R
import tech.hackcity.educarts.domain.model.AddressBook
import tech.hackcity.educarts.databinding.AddressBookItemBinding

class AddressBookAdapter(private val context: Context) :
    RecyclerView.Adapter<AddressBookAdapter.AddressBookViewHolder>() {

    inner class AddressBookViewHolder(val binding: AddressBookItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<AddressBook>() {
        override fun areItemsTheSame(oldItem: AddressBook, newItem: AddressBook): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AddressBook, newItem: AddressBook): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var addressList = emptyList<AddressBook>()

    override fun getItemCount() = addressList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressBookViewHolder {
        return AddressBookViewHolder(
            AddressBookItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: AddressBookViewHolder, position: Int) {
        holder.binding.apply {
            val address = addressList[position]
            addressTV.text = "${address.apartmentNumber}, ${address.homeAddress}, ${address.city}, ${address.country}"

            editAddress.setOnClickListener {
                onEditClickListener?.let {it(address)}
            }
            activateAddress.setOnClickListener {
                onActivateClickListener?.let {it(address)}
            }

            when(address.isAddressActive) {
                true ->  activeAddressIndicator.setImageResource(R.drawable.tick_circle)
                false -> activeAddressIndicator.setImageResource(R.drawable.round_bg_gray)
            }

        }

    }

    fun setData(addresses: List<AddressBook>) {
        differ.submitList(addresses)
    }

    private var onEditClickListener: ((AddressBook) -> Unit)? = null
    private var onActivateClickListener: ((AddressBook) -> Unit)? = null

    fun setOnEditClickListener(listener: (AddressBook) -> Unit) {
        onEditClickListener = listener
    }
    fun setOnActivateClickListener(listener: (AddressBook) -> Unit) {
        onActivateClickListener = listener
    }
}