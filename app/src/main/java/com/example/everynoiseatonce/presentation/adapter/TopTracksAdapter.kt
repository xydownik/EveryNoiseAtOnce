package com.example.everynoiseatonce.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.everynoiseatonce.databinding.ItemTopTrackBinding
import com.example.everynoiseatonce.domain.model.Track

class TopTracksAdapter : RecyclerView.Adapter<TopTracksAdapter.TopTrackViewHolder>() {

    private val tracks = mutableListOf<Track>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newTracks: List<Track>) {
        tracks.clear()
        tracks.addAll(newTracks)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopTrackViewHolder {
        val binding = ItemTopTrackBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TopTrackViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TopTrackViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount(): Int = tracks.size

    inner class TopTrackViewHolder(private val binding: ItemTopTrackBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(track: Track) {
            binding.trackNameTextView.text = track.name
            Glide.with(binding.root).load(track.album.images.firstOrNull()?.url).into(binding.trackImageView)
        }
    }
}
