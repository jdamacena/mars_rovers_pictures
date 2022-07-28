package com.juniordamacena.marpics.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.juniordamacena.marpics.models.main.Photo
import com.juniordamacena.marpics.ui.main.GalleryPageFragment

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class GalleryAdapter(
    private val context: Context,
    fm: FragmentManager,
    var pages: List<Photo>
) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return GalleryPageFragment.newInstance(pages[position])
    }

    override fun getPageTitle(position: Int): CharSequence {
        return pages.elementAt(position).id.toString()
    }

    override fun getCount(): Int {
        // Show total of pages.
        return pages.size
    }
}