package com.example.everynoiseatonce.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.everynoiseatonce.EveryNoiseApp
import com.example.everynoiseatonce.R
import com.example.everynoiseatonce.databinding.FragmentFavoritesBinding
import com.example.everynoiseatonce.domain.model.Artist
import com.example.everynoiseatonce.domain.model.Genre
import com.example.everynoiseatonce.presentation.adapter.FavoritesAdapter
import com.example.everynoiseatonce.presentation.viewmodel.FavoritesViewModel
import com.example.everynoiseatonce.presentation.viewmodel.FavoritesViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoritesFragment : Fragment() {

    @Inject lateinit var viewModelFactory: FavoritesViewModelFactory
    private lateinit var viewModel: FavoritesViewModel
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavoritesAdapter


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EveryNoiseApp).appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = viewModels<FavoritesViewModel> { viewModelFactory }.value
        adapter = FavoritesAdapter { item ->
            when (item) {
                is Artist -> viewModel.toggleFavoriteArtist(item)
                is Genre -> viewModel.toggleFavoriteGenre(item)
            }
        }
        binding.favoritesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.favoritesRecyclerView.adapter = adapter

        binding.filterGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.filterAll -> viewModel.setFilter(FavoritesViewModel.Filter.ALL)
                R.id.filterGenres -> viewModel.setFilter(FavoritesViewModel.Filter.GENRES)
                R.id.filterArtists -> viewModel.setFilter(FavoritesViewModel.Filter.ARTISTS)
            }
        }

        lifecycleScope.launch {
            viewModel.favorites.collect {
                adapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
