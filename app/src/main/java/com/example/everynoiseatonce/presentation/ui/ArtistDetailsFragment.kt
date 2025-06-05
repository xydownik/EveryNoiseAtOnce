package com.example.everynoiseatonce.presentation.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.everynoiseatonce.EveryNoiseApp
import com.example.everynoiseatonce.R
import com.example.everynoiseatonce.domain.model.Album
import com.example.everynoiseatonce.domain.model.ExternalUrls
import com.example.everynoiseatonce.domain.model.Track
import com.example.everynoiseatonce.presentation.adapter.RelatedArtistsAdapter
import com.example.everynoiseatonce.presentation.adapter.TopTracksAdapter
import com.example.everynoiseatonce.presentation.viewmodel.ArtistDetailsViewModel
import com.example.everynoiseatonce.presentation.viewmodel.ArtistDetailsViewModelFactory
import com.example.everynoiseatonce.service.PlayerService
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.core.net.toUri

class ArtistDetailsFragment : Fragment() {

    private lateinit var viewModel: ArtistDetailsViewModel
    private lateinit var topTracksAdapter: TopTracksAdapter
    private lateinit var relatedArtistsAdapter: RelatedArtistsAdapter

    private lateinit var artistImage: ImageView
    private lateinit var artistNameText: TextView
    private lateinit var openInSpotify: TextView
    private lateinit var topTracksRecycler: RecyclerView
    private lateinit var relatedArtistsRecycler: RecyclerView
    private val args: ArtistDetailsFragmentArgs by navArgs()
    private var spotifyUrl: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_artist_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // View binding
        artistImage = view.findViewById(R.id.artistImage)
        artistNameText = view.findViewById(R.id.artistName)
        openInSpotify = view.findViewById(R.id.openInSpotify)
        topTracksRecycler = view.findViewById(R.id.topTracksRecyclerView)
        relatedArtistsRecycler = view.findViewById(R.id.relatedArtistsRecyclerView)

        // Аргументы
        val artistId = args.artistId
        val artistName = args.artistName
        val imageUrl = args.artistImageUrl
        spotifyUrl = args.spotifyUrl

        // Установка данных в UI
        artistNameText.text = artistName
        Glide.with(this).load(imageUrl).into(artistImage)

        // Вьюмодель
        val appComponent = (requireActivity().application as EveryNoiseApp).appComponent
        val repository = appComponent.artistRepository()
        val factory = ArtistDetailsViewModelFactory(repository)
        viewModel = androidx.lifecycle.ViewModelProvider(this, factory)[ArtistDetailsViewModel::class.java]

        val testTrack = Track(
            id = "123",
            name = "Demo",
            preview_url = "android.resource://${requireContext().packageName}/raw/sample_track",
            external_urls = ExternalUrls(""),
            album = Album("Demo", listOf()),
            artists = listOf()
        )
        // Топ треки
        topTracksAdapter = TopTracksAdapter {
            val intent = Intent(context, PlayerService::class.java).apply {
                action = PlayerService.ACTION_PLAY
                putExtra("track", testTrack)
            }
            context?.startService(intent)

        }
        topTracksRecycler.adapter = topTracksAdapter

        topTracksRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = topTracksAdapter
        }

        // Похожие артисты
        relatedArtistsAdapter = RelatedArtistsAdapter { artist ->
            val direction = ArtistDetailsFragmentDirections.actionArtistDetailsFragmentSelf(
                artist.id,
                artist.name,
                artist.images?.firstOrNull()?.url ?: "",
                artist.external_urls.spotify
            )
            findNavController().navigate(direction)
        }
        relatedArtistsRecycler.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        relatedArtistsRecycler.adapter = relatedArtistsAdapter

        // Обновление UI через StateFlow
        lifecycleScope.launch {
            viewModel.topTracks.collectLatest {
                topTracksAdapter.submitList(it)
            }
        }

        lifecycleScope.launch {
            viewModel.relatedArtists.collectLatest {
                relatedArtistsAdapter.submitList(it)
            }
        }

        openInSpotify.setOnClickListener {
            val spotifyIntent = Intent(Intent.ACTION_VIEW, "spotify:artist:${args.artistId}".toUri())
            spotifyIntent.`package` = "com.spotify.music"

            if (spotifyIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(spotifyIntent)
            } else {
                startActivity(Intent(Intent.ACTION_VIEW, args.spotifyUrl.toUri()))
            }
        }


        viewModel.loadArtistDetails(artistId)
    }

}
