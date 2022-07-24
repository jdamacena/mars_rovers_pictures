package com.juniordamacena.marpics.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.juniordamacena.marpics.databinding.FragmentApodBinding
import com.juniordamacena.marpics.viewmodels.ApodViewModel

class ApodFragment : Fragment() {

    companion object {
        fun newInstance() = ApodFragment()
    }

    private val viewModel: ApodViewModel by viewModels()

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

        viewModel.photoOfTheDay.observe(viewLifecycleOwner) {
            if (it == null) return@observe

            _binding?.textView?.text = it.explanation

            Glide.with(this)
                .load(it.url)
                .into(_binding!!.imageView2)
        }

        return root
    }
}