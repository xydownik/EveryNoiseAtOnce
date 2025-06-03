package com.example.everynoiseatonce.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.everynoiseatonce.databinding.ItemArtistBinding
import com.example.everynoiseatonce.domain.model.Artist

class ArtistsAdapter : ListAdapter<Artist, ArtistsAdapter.ArtistViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val binding = ItemArtistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ArtistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ArtistViewHolder(private val binding: ItemArtistBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(artist: Artist) {
            binding.artistName.text = artist.name
            val imageUrl = artist.images?.firstOrNull()?.url
            Glide.with(binding.root.context).load(imageUrl).into(binding.artistImage)
            binding.root.setOnClickListener {
                // Можно открыть Spotify-ссылку: artist.external_urls.spotify
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