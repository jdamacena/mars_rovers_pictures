package com.juniordamacena.marpics.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.juniordamacena.marpics.databinding.FragmentApodBinding
import com.juniordamacena.marpics.viewmodels.ApodViewModel

class ApodFragment : Fragment() {

    companion object {
        fun newInstance() = ApodFragment()
    }

    private lateinit var viewModel: ApodViewModel

    private var _binding: FragmentApodBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ApodViewModel::class.java)

        viewModel.queryApod()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentApodBinding.inflate(inflater, container, false)
        val root = binding.root

        viewModel.photoOfTheDay.observe(viewLifecycleOwner, Observer {
            _binding?.textView?.text = it.explanation

            Glide.with(this)
                .load(it.url)
                .into(_binding!!.imageView2)
        })

        return root
    }
}