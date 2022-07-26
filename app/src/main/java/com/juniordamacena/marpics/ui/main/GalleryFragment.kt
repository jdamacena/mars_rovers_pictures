package com.juniordamacena.marpics.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.juniordamacena.marpics.adapters.GalleryAdapter
import com.juniordamacena.marpics.databinding.FragmentGalleryBinding
import com.juniordamacena.marpics.viewmodels.PageViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class GalleryFragment : Fragment() {

    companion object {
        fun newInstance() = GalleryFragment()
    }

    private val pageViewModel: PageViewModel by sharedViewModel()

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root = binding.root

        val viewPager: ViewPager = binding.viewPager

        val galleryAdapter = GalleryAdapter(
            requireContext(), childFragmentManager, emptyList()
        )

        viewPager.adapter = galleryAdapter

        pageViewModel.getPhotos().observe(viewLifecycleOwner) { list ->
            val indexOfSelectedPhoto = list.indexOfFirst { photo -> pageViewModel.selectedId == photo.id }
Log.d("index of selected ID", indexOfSelectedPhoto.toString())
            galleryAdapter.tabs = list.map { photo -> photo.img_src }
            galleryAdapter.notifyDataSetChanged()

            viewPager.currentItem = indexOfSelectedPhoto
        }

        return root
    }
}