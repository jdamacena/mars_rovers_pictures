package com.juniordamacena.marpics.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.juniordamacena.marpics.databinding.FragmentGalleryPageBinding
import com.juniordamacena.marpics.models.main.Photo
import com.juniordamacena.marpics.viewmodels.GalleryPageViewModel

class GalleryPageFragment : Fragment() {

    companion object {
        fun newInstance(photo: Photo) = GalleryPageFragment().also {
            it.photo = photo
        }
    }

    private var photo: Photo? = null

    private var _binding: FragmentGalleryPageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: GalleryPageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentGalleryPageBinding.inflate(inflater, container, false)

        binding.textView.text = photo?.img_src

        Glide.with(this)
            .load(photo?.img_src)
            .into(binding.imageView3)

        return binding.root
    }
}