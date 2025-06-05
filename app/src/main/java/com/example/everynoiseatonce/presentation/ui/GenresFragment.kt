package com.example.everynoiseatonce.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.everynoiseatonce.EveryNoiseApp
import com.example.everynoiseatonce.databinding.FragmentGenresBinding
import com.example.everynoiseatonce.domain.model.Genre
import com.example.everynoiseatonce.presentation.adapter.GenresAdapter
import com.example.everynoiseatonce.presentation.viewmodel.GenresViewModel
import com.example.everynoiseatonce.presentation.viewmodel.GenresViewModelFactory
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class GenresFragment : Fragment() {

    private var _binding: FragmentGenresBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: GenresAdapter
    private lateinit var viewModel: GenresViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenresBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val appComponent = (requireActivity().application as EveryNoiseApp).appComponent
        val factory = GenresViewModelFactory(appComponent.favoritesRepository())
        viewModel = ViewModelProvider(this, factory)[GenresViewModel::class.java]

        val genres = loadGenresFromAssets()
        viewModel.setGenres(genres)

        adapter = GenresAdapter(
            onGenreClick = { genre ->
                val action = GenresFragmentDirections.actionGenresFragmentToArtistsFragment(genre.name)
                findNavController().navigate(action)
            },
            onFavoriteClick = { genre ->
                viewModel.toggleFavoriteGenre(genre)
            },
            onItemClick = { genre ->
                val action = GenresFragmentDirections.actionGenresFragmentToArtistsFragment(genre.name)
                findNavController().navigate(action)
            }
        )

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@GenresFragment.adapter
        }

        binding.searchInput.addTextChangedListener {
            viewModel.onQueryChanged(it.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.filteredGenres.collect { genres ->
                    adapter.submitList(genres)
                }
            }
        }
    }

    private fun loadGenresFromAssets(): List<Genre> {
        val genres = mutableListOf<Genre>()
        try {
            val inputStream = requireContext().assets.open("genres.csv")
            val reader = BufferedReader(InputStreamReader(inputStream))
            reader.readLine() // пропускаем заголовок
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                val parts = line?.split(",") ?: continue
                if (parts.isNotEmpty()) {
                    val genreName = parts[0].trim()
                    if (genreName.isNotEmpty()) {
                        genres.add(Genre(genreName))
                    }
                }
            }
            reader.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return genres
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}