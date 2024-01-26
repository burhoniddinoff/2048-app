package com.example.a2048app.presenter.ui.info

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class InfoAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> InfoPageOne()
        1 -> InfoPageTwo()
        else -> InfoPageThree()
    }


}