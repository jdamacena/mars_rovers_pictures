package com.juniordamacena.marpics.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.juniordamacena.marpics.databinding.FragmentApodBinding
import com.juniordamacena.marpics.viewmodels.ApodViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ApodFragment : Fragment() {

    companion object {
        fun newInstance() = ApodFragment()
    }

    private val viewModel: ApodViewModel by viewModel()

    private var _binding: FragmentApodBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.queryApod()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApodBinding.inflate(inflater, container, false)
        val root = binding.root

        viewModel.getPhotoOfTheDay().observe(viewLifecycleOwner) {
            if (it == null) return@observe

            binding.textView.text = "${it.title}\n\n${it.explanation}\n\nMedia type: ${it.media_type}"

            Glide.with(this)
                .load(it.url)
                .error(android.R.drawable.stat_notify_error)
                .placeholder(android.R.drawable.ic_menu_myplaces)
                .into(binding.imageView2)
        }

        viewModel.getIsLoading().observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
            binding.scrollView3.isVisible = !it
        }

        return root
    }
}