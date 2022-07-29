package com.juniordamacena.marpics.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.juniordamacena.marpics.databinding.FragmentGalleryBinding
import com.juniordamacena.marpics.viewmodels.GalleryViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class GalleryFragment : Fragment() {
    private val args: GalleryFragmentArgs by navArgs()

    private val viewModel: GalleryViewModel by viewModel()

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)

        Glide.with(this)
            .load(args.imageUrl)
            .into(binding.ivPhoto)

        return binding.root
    }
}