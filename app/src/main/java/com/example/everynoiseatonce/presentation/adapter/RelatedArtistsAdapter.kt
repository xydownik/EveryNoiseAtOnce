package com.example.everynoiseatonce.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.everynoiseatonce.databinding.ItemArtistRelatedBinding
import com.example.everynoiseatonce.domain.model.Artist

class RelatedArtistsAdapter(
    private val onArtistClick: (Artist) -> Unit
) : ListAdapter<Artist, RelatedArtistsAdapter.RelatedArtistViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RelatedArtistViewHolder {
        val binding = ItemArtistRelatedBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RelatedArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RelatedArtistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class RelatedArtistViewHolder(
        private val binding: ItemArtistRelatedBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(artist: Artist) {
            binding.textViewRelatedArtistName.text = artist.name
            Glide.with(binding.imageViewRelatedArtist.context)
                .load(artist.images?.firstOrNull()?.url)
                .into(binding.imageViewRelatedArtist)

            binding.root.setOnClickListener {
                onArtistClick(artist)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Artist>() {
            override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean =
                oldItem == newItem
        }
    }
}
