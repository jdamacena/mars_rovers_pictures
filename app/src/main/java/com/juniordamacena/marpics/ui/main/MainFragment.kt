package com.juniordamacena.marpics.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayout
import com.juniordamacena.marpics.databinding.ActivityMainBinding
import com.juniordamacena.marpics.models.Rover
import com.juniordamacena.marpics.services.RetrofitInstance
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var _binding: ActivityMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityMainBinding.inflate(inflater, container, false)
        val root = binding.root

        val fab: FloatingActionButton = binding.fab

        fab.setOnClickListener { view ->
            Snackbar
                .make(view, "roverName", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }

        viewModel.viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.listRovers(RetrofitInstance.API_KEY)
                val listRovers: List<Rover> = response.body()?.rovers ?: emptyList()

                val sectionsPagerAdapter = SectionsPagerAdapter(
                    requireContext(),
                    parentFragmentManager,
                    listRovers
                )
                val viewPager: ViewPager = binding.viewPager
                viewPager.adapter = sectionsPagerAdapter
                val tabs: TabLayout = binding.tabs
                tabs.setupWithViewPager(viewPager)

            } catch (e: IOException) {
                Log.e("MainFragment", e.message ?: "IOException")
            } catch (e: HttpException) {
                Log.e("MainFragment", e.message ?: "HttpException")
            }
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}