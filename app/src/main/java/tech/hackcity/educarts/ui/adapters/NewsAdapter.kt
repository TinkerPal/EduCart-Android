package tech.hackcity.educarts.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import tech.hackcity.educarts.R
import tech.hackcity.educarts.databinding.NewsItemBinding
import tech.hackcity.educarts.domain.model.news.News

class NewsAdapter(private val context: Context) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val diffCallback = object : DiffUtil.ItemCallback<News>() {
        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    var newsList = emptyList<News>()

    override fun getItemCount() = newsList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            NewsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private val backgrounds = listOf(
        R.drawable.news_item_bg_1,
        R.drawable.news_item_bg_2,
        R.drawable.news_item_bg_3
    )

    private val textColours = listOf(
        R.color.primary_color,
        R.color.primary_color,
        R.color.secondary_color,
    )

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.binding.apply {
            val news = newsList[position]
            title.text = news.title
            description.text = news.description
            Glide.with(context).load(news.image).into(imageView)

            // Calculate the index of the drawable background to use
            val backgroundIndex = position % backgrounds.size
            val backgroundDrawable = backgrounds[backgroundIndex]
            holder.itemView.setBackgroundResource(backgroundDrawable)

            val textIndex = position % textColours.size
            val textColourResId = textColours[textIndex]
            val textColour = ContextCompat.getColor(context, textColourResId)
            title.setTextColor(textColour)
            description.setTextColor(textColour)

            setOnItemClickListener {
                onItemClickListener?.let {it(news)}
            }
        }

    }

    fun setData(news: List<News>) {
        this.newsList = news
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((News) -> Unit)? = null

    fun setOnItemClickListener(listener: (News) -> Unit) {
        onItemClickListener = listener
    }
}