package com.juniordamacena.marpics.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.juniordamacena.marpics.databinding.FragmentPageBinding
import com.juniordamacena.marpics.models.Rover
import com.juniordamacena.marpics.viewmodels.PageViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class PageFragment : Fragment() {

    private val pageViewModel: PageViewModel by viewModels()
    private var _binding: FragmentPageBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pageViewModel.setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)

        if (savedInstanceState == null) {
            pageViewModel.queryPhotoUrl()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentPageBinding.inflate(inflater, container, false)
        val root = binding.root

        val textView: TextView = binding.sectionLabel
        pageViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        pageViewModel.photoUrl.observe(viewLifecycleOwner) {
            if(it == null) return@observe

            Glide.with(this@PageFragment)
                .load(it)
                .into(_binding!!.imageView)
        }

        return root
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int, rover: Rover): PageFragment {
            Log.d("PlaceHolderFragment", rover.name)

            return PageFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}