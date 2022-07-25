package com.juniordamacena.marpics.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.juniordamacena.marpics.R
import com.juniordamacena.marpics.adapters.PhotoListAdapter
import com.juniordamacena.marpics.databinding.FragmentPageBinding
import com.juniordamacena.marpics.models.Rover
import com.juniordamacena.marpics.viewmodels.PageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * A placeholder fragment containing a simple view.
 */
class PageFragment : Fragment() {
    var rover: Rover? = null

    private val pageViewModel: PageViewModel by viewModel { parametersOf(rover) }
    private var _binding: FragmentPageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            pageViewModel.queryPhotos()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPageBinding.inflate(inflater, container, false)
        val root = binding.root


        pageViewModel.getIsLoading().observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        val repositoriesAdapter = PhotoListAdapter(mutableListOf())

        binding.rvPhotosList.apply {
            adapter = repositoriesAdapter
            layoutManager =
                GridLayoutManager(context, resources.getInteger(R.integer.photo_list_num_columns))
        }

        pageViewModel.getPhotos().observe(viewLifecycleOwner) {
            if (it == null) return@observe

            repositoriesAdapter.list.addAll(it)
            repositoriesAdapter.notifyDataSetChanged()
        }

        return root
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}