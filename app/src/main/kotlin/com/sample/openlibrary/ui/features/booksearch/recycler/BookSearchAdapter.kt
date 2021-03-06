package com.sample.openlibrary.ui.features.booksearch.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.sample.openlibrary.R
import com.sample.openlibrary.databinding.ViewHolderBookBinding
import com.sample.openlibrary.domain.model.Book
import com.sample.openlibrary.domain.model.coverSmallUrl
import com.sample.openlibrary.ui.extension.inflater

class BookSearchAdapter constructor(
    private val clickListener: (Book) -> Unit
) : ListAdapter<Book, BookSearchAdapter.BookViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ViewHolderBookBinding.inflate(parent.inflater(), parent, false)
        return BookViewHolder(binding).apply {
            itemView.setOnClickListener {
                val item = getItem(bindingAdapterPosition)
                clickListener(item)
            }
        }
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class BookViewHolder(
        private val binding: ViewHolderBookBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Book) = with(binding) {
            title.text = item.title
            author.text = item.authorsNames.joinToString(", ")
            subtitle.text = item.subtitle

            item.coverSmallUrl?.also {
                cover.load(it) {
                    error(R.drawable.ic_baseline_broken_image_24)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Book>() {
        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem == newItem
        }
    }
}
