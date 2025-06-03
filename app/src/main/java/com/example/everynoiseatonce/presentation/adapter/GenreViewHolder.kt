package com.example.everynoiseatonce.presentation.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.everynoiseatonce.R
import com.example.everynoiseatonce.domain.model.Genre

class GenreViewHolder(
    itemView: View,
    private val onGenreClick: (String) -> Unit
) : RecyclerView.ViewHolder(itemView) {

    private val genreText: TextView = itemView.findViewById(R.id.genreNameTextView)

    fun bind(genre: Genre) {
        genreText.text = genre.name
        itemView.setOnClickListener {
            onGenreClick(genre.name)
        }
    }
}
