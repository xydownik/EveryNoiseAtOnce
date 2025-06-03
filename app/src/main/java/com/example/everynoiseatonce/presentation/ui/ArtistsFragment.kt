package com.example.everynoiseatonce.presentation.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.everynoiseatonce.EveryNoiseApp
import com.example.everynoiseatonce.databinding.FragmentArtistsBinding
import com.example.everynoiseatonce.presentation.adapter.ArtistsAdapter
import com.example.everynoiseatonce.presentation.viewmodel.ArtistsViewModel
import com.example.everynoiseatonce.presentation.viewmodel.ArtistsViewModelFactory
import kotlinx.coroutines.launch
import javax.inject.Inject

class ArtistsFragment : Fragment() {

    private var _binding: FragmentArtistsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ArtistsViewModelFactory
    private lateinit var viewModel: ArtistsViewModel
    private val args: ArtistsFragmentArgs by navArgs()
    private val adapter = ArtistsAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EveryNoiseApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = viewModels<ArtistsViewModel> { viewModelFactory }.value

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter = adapter

        viewModel.loadArtists(args.genreName)

        lifecycleScope.launch {
            viewModel.artists.collect { artists ->
                adapter.submitList(artists)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
