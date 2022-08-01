package com.juniordamacena.marpics.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.juniordamacena.marpics.R
import com.juniordamacena.marpics.adapters.PhotoListAdapter
import com.juniordamacena.marpics.databinding.FragmentPageBinding
import com.juniordamacena.marpics.models.main.Photo
import com.juniordamacena.marpics.models.main.Rover
import com.juniordamacena.marpics.viewmodels.PageViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PageFragment : Fragment() {
    private lateinit var rover: Rover

    private val pageViewModel: PageViewModel by viewModel(parameters = { parametersOf(rover) })

    // This property is only valid between onCreateView and
    // onDestroyView.
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val binding = FragmentPageBinding.inflate(inflater, container, false)

        val photoListAdapter = PhotoListAdapter(PhotoComparator, this@PageFragment::adapterOnClick)

        photoListAdapter.addLoadStateListener { loadState ->
            val isLoading = loadState.refresh is LoadState.Loading

            if (!binding.swipeRefresh.isRefreshing) {
                binding.progressBar.isVisible = isLoading
            } else if (!isLoading) {
                binding.swipeRefresh.isRefreshing = false
            }
        }

        binding.rvPhotosList.apply {
            adapter = photoListAdapter
            layoutManager =
                GridLayoutManager(context, resources.getInteger(R.integer.photo_list_num_columns))
        }

        binding.swipeRefresh.setOnRefreshListener {
            pageViewModel.invalidatePagingSource()
        }

        lifecycleScope.launch {
            pageViewModel.flow.collectLatest { pagingData ->
                photoListAdapter.submitData(pagingData)
            }
        }

        return binding.root
    }

    private fun adapterOnClick(photo: Photo) {
        val action =
            MainFragmentDirections.actionMainFragmentToGalleryFragment(imageUrl = photo.img_src)

        findNavController().navigate(action)
    }

    companion object {
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(rover: Rover): PageFragment {
            return PageFragment().also {
                it.rover = rover
            }
        }
    }

    object PhotoComparator : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            // Id is unique.
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }
}