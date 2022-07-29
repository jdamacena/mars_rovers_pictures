package com.juniordamacena.marpics.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.juniordamacena.marpics.models.main.Rover
import com.juniordamacena.marpics.ui.main.PageFragment

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class RoverTabsAdapter(
    private val context: Context,
    f: FragmentActivity,
    var tabs: List<Rover>,
) : FragmentStateAdapter(f) {

    override fun getItemCount(): Int {
        // Show total of pages.
        return tabs.size
    }

    override fun createFragment(position: Int): Fragment {
        return PageFragment.newInstance(tabs.elementAt(position))
    }
}