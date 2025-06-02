package com.example.everynoiseatonce.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.everynoiseatonce.EveryNoiseApp
import com.example.everynoiseatonce.domain.usecase.GetGenresUseCase
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var getGenresUseCase: GetGenresUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as EveryNoiseApp)
            .appComponent
            .inject(this)
    }
}
