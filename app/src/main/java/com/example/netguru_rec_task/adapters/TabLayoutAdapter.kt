package com.example.netguru_rec_task.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.netguru_rec_task.fragments.ArchivedListFragment
import com.example.netguru_rec_task.fragments.ShoppingListFragment

/**
 * ViewPager adapter
 */
class TabLayoutAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ShoppingListFragment()
            else -> ArchivedListFragment()
        }
    }

}