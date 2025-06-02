package com.example.everynoiseatonce.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.everynoiseatonce.EveryNoiseApp
import com.example.everynoiseatonce.databinding.FragmentProfileBinding
import com.example.everynoiseatonce.presentation.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as EveryNoiseApp).appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = FirebaseAuth.getInstance().currentUser
        binding.tvNickname.text = user?.displayName ?: "Гость"

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

        binding.btnSupport.setOnClickListener {
            val supportNumber = "+77477840612"
            val uri = "https://wa.me/${supportNumber.replace("+", "")}"
            val intent = Intent(Intent.ACTION_VIEW).apply { data = android.net.Uri.parse(uri) }
            startActivity(intent)
        }

        binding.btnAbout.setOnClickListener {
            val aboutFragment = AboutBottomSheetFragment()
            aboutFragment.show(parentFragmentManager, "AboutBottomSheet")
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
