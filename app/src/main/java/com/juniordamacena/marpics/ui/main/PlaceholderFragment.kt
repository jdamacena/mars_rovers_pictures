package com.juniordamacena.marpics.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import com.juniordamacena.marpics.R
import com.juniordamacena.marpics.databinding.FragmentMainBinding
import com.juniordamacena.marpics.models.Photo
import com.juniordamacena.marpics.models.Rover
import com.juniordamacena.marpics.services.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var pageViewModel: PageViewModel
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pageViewModel = ViewModelProvider(this).get(PageViewModel::class.java).apply {
            setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root = binding.root

        val textView: TextView = binding.sectionLabel
        pageViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        _binding!!.imageView.setImageResource(R.mipmap.ic_launcher)

        pageViewModel.viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.listPhotos(
                    "Curiosity",
                    RetrofitInstance.API_KEY,
                    sol = (10..100).random(),
                    earth_date = null
                )
                val listPhotos: List<Photo>? = response.body()?.photos
                val firstPhoto = listPhotos?.firstOrNull()
                val photoUrl = firstPhoto?.img_src
                    ?: "https://www.iconsdb.com/icons/download/red/error-7-128.jpg"

                Glide.with(this@PlaceholderFragment)
                    .load(photoUrl)
                    .into(_binding!!.imageView)

            } catch (e: IOException) {
                Log.e("MainActivity", e.message ?: "IOException")
            } catch (e: HttpException) {
                Log.e("MainActivity", e.message ?: "HttpException")
            }
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
        fun newInstance(sectionNumber: Int, rover: Rover): PlaceholderFragment {
            Log.d("PlaceHolderFragment", rover.name)

            return PlaceholderFragment().apply {
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