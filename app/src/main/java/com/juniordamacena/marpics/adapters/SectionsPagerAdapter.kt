package com.juniordamacena.marpics.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.juniordamacena.marpics.models.Rover
import com.juniordamacena.marpics.ui.main.PageFragment

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(
    private val context: Context,
    fm: FragmentManager,
    var tabs: List<Rover>
) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PageFragment.newInstance(position + 1, tabs.elementAt(position))
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabs.elementAt(position).name
    }

    override fun getCount(): Int {
        // Show total of pages.
        return tabs.size
    }
}