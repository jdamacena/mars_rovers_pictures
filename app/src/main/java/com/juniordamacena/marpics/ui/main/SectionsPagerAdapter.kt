package com.juniordamacena.marpics.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.juniordamacena.marpics.R
import com.juniordamacena.marpics.models.Rover

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_1,
    R.string.tab_text_2,
    R.string.tab_text_3
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager, private val tabs: List<Rover>) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1, tabs.elementAt(position))
    }

    override fun getPageTitle(position: Int): CharSequence? {
//        return context.resources.getString(TAB_TITLES[position])
        return tabs.elementAt(position).name
    }

    override fun getCount(): Int {
        // Show total of pages.
//        return TAB_TITLES.size
        return tabs.size
    }
}