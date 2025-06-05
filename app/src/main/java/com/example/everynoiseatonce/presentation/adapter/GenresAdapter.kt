package com.example.everynoiseatonce.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.everynoiseatonce.R
import com.example.everynoiseatonce.databinding.ItemFavoriteGenreBinding
import com.example.everynoiseatonce.databinding.ItemGenreBinding
import com.example.everynoiseatonce.domain.model.Genre

class GenresAdapter(
    private val onGenreClick: (Genre) -> Unit,
    private val onFavoriteClick: (Genre) -> Unit
) : ListAdapter<Genre, GenresAdapter.GenreViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val binding = ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class GenreViewHolder(private val binding: ItemGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre) {
            binding.genreNameTextView.text = genre.name

            val iconRes = if (genre.isFavorite) R.drawable.added else R.drawable.add_to_fav
            binding.favoriteIcon.setImageResource(iconRes)
            binding.root.setOnClickListener {
                onGenreClick(genre)
            }
            binding.favoriteIcon.setOnClickListener {
                val updatedGenre = genre.copy(isFavorite = !genre.isFavorite)
                onFavoriteClick(updatedGenre)
                binding.favoriteIcon.setImageResource(
                    if (updatedGenre.isFavorite) R.drawable.added else R.drawable.add_to_fav
                )
            }
        }
    }


    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Genre>() {
            override fun areItemsTheSame(oldItem: Genre, newItem: Genre) = oldItem.name == newItem.name
            override fun areContentsTheSame(oldItem: Genre, newItem: Genre) = oldItem == newItem
        }
    }
}
