// ArtistsAdapter.kt
package com.example.everynoiseatonce.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.everynoiseatonce.R
import com.example.everynoiseatonce.databinding.ItemArtistBinding
import com.example.everynoiseatonce.domain.model.Artist

class ArtistsAdapter(
    private val onArtistClick: (Artist) -> Unit,
    private val onFavoriteClick: (Artist) -> Unit,
    private val onItemClick: (Artist) -> Unit
) : ListAdapter<Artist, ArtistsAdapter.ArtistViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = ItemArtistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ArtistViewHolder(private val binding: ItemArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val circularProgressDrawable = CircularProgressDrawable(binding.artistImage.context).apply {
            strokeWidth = 5f
            centerRadius = 30f
            start()
        }
        fun bind(artist: Artist) {
            binding.artistName.text = artist.name
            Glide.with(binding.artistImage.context)
                .load(artist.images?.firstOrNull()?.url)
                .placeholder(circularProgressDrawable)
                .into(binding.artistImage)

            val iconRes = if (artist.isFavorite) R.drawable.added else R.drawable.add_to_fav
            binding.favoriteIcon.setImageResource(iconRes)

            binding.root.setOnClickListener {
                onArtistClick(artist)
            }
            binding.favoriteIcon.setOnClickListener {
                val updated = artist.copy(isFavorite = !artist.isFavorite)
                onFavoriteClick(updated)
            }
            binding.root.setOnClickListener {
                onItemClick(artist)
            }

        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<Artist>() {
            override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean = oldItem == newItem
        }
    }
}
