package com.example.netguru_rec_task.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.netguru_rec_task.R
import com.example.netguru_rec_task.adapters.TabLayoutAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainFragment : Fragment() {

    private lateinit var adapter: TabLayoutAdapter
    private lateinit var viewPager: ViewPager2


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = TabLayoutAdapter(this)
        viewPager = view.findViewById(R.id.pager)
        viewPager.adapter = adapter

        val tabLayout = view.findViewById<TabLayout>(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Shopping List"
                    tab.icon =
                        ContextCompat.getDrawable(requireContext(), R.drawable.ic_baseline_list_24)
                }
                1 -> {
                    tab.text = "Archived shopping list"
                    tab.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_archive_24
                    )
                }
            }
        }.attach()

    }
}