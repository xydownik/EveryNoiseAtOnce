package com.example.everynoiseatonce.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.everynoiseatonce.R
import com.example.everynoiseatonce.databinding.ItemFavoriteArtistBinding
import com.example.everynoiseatonce.databinding.ItemFavoriteGenreBinding
import com.example.everynoiseatonce.domain.model.Artist
import com.example.everynoiseatonce.domain.model.Genre

class FavoritesAdapter(private val onFavoriteClick: (Any) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val items = mutableListOf<Any>()

    companion object {
        private const val TYPE_ARTIST = 0
        private const val TYPE_GENRE = 1
    }

    fun submitList(newItems: List<Any>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Artist -> TYPE_ARTIST
            is Genre -> TYPE_GENRE
            else -> throw IllegalArgumentException("Unknown item type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ARTIST -> {
                val binding = ItemFavoriteArtistBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                ArtistViewHolder(binding)
            }
            TYPE_GENRE -> {
                val binding = ItemFavoriteGenreBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                GenreViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ArtistViewHolder -> holder.bind(items[position] as Artist)
            is GenreViewHolder -> holder.bind(items[position] as Genre)
        }
    }

    inner class ArtistViewHolder(private val binding: ItemFavoriteArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist) {
            binding.artistName.text = artist.name
            Glide.with(binding.artistImage.context)
                .load(artist.images?.firstOrNull()?.url)
                .placeholder(R.drawable.ic_default_avatar)
                .into(binding.artistImage)

            val icon = if (artist.isFavorite) R.drawable.added else R.drawable.add_to_fav
            binding.favoriteIcon.setImageResource(icon)

            binding.favoriteIcon.setOnClickListener {
                artist.isFavorite = !artist.isFavorite
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    notifyItemChanged(position)
                }
                onFavoriteClick(artist)
            }
        }
    }

    inner class GenreViewHolder(private val binding: ItemFavoriteGenreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre) {
            binding.genreName.text = genre.name
            val icon = if (genre.isFavorite) R.drawable.added else R.drawable.add_to_fav
            binding.favoriteIcon.setImageResource(icon)

            binding.favoriteIcon.setOnClickListener {
                genre.isFavorite = !genre.isFavorite
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    notifyItemChanged(position)
                }
                onFavoriteClick(genre)
            }
        }
    }

}
