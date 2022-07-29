package com.juniordamacena.marpics.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.juniordamacena.marpics.adapters.RoverTabsAdapter
import com.juniordamacena.marpics.databinding.FragmentMainBinding
import com.juniordamacena.marpics.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModel()
    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            viewModel.queryRovers()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        val root = binding.root
        val fab: FloatingActionButton = binding.fab
        val viewPager: ViewPager2 = binding.viewPager
        val tabs: TabLayout = binding.tabs

        val roverTabsAdapter = RoverTabsAdapter(
            requireContext(), requireActivity(), emptyList()
        )

        viewPager.adapter = roverTabsAdapter

        TabLayoutMediator(tabs, viewPager) { tab: TabLayout.Tab, position: Int ->
            tab.text = position.toString()
        }.attach()

        fab.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToApodFragment()
            it.findNavController().navigate(action)
        }

        viewModel.getIsLoading().observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
            binding.tabs.isVisible = !it
        }

        viewModel.getListRovers().observe(viewLifecycleOwner) { listRovers ->
            if (listRovers == null) return@observe

            (viewPager.adapter as RoverTabsAdapter).apply {
                this.tabs = listRovers
                notifyDataSetChanged()
            }

            viewPager.currentItem = viewModel.selectedTabIndex
        }

        return root
    }

    override fun onStop() {
        super.onStop()

        viewModel.selectedTabIndex = binding.tabs.selectedTabPosition
    }
}