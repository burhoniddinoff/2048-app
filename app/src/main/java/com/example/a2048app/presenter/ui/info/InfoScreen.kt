package com.example.a2048app.presenter.ui.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.a2048app.R
import com.example.a2048app.databinding.ScreenInfoBinding

class InfoScreen : Fragment(R.layout.screen_info) {

    private val binding by viewBinding(ScreenInfoBinding::bind)
    private val adapter by lazy { InfoAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewPager.adapter = adapter
        binding.dotsIndicator.attachTo(binding.viewPager)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                when (position) {
                    0 -> binding.btLetsPlay.visibility = View.GONE
                    1 -> binding.btLetsPlay.visibility = View.GONE
                    2 -> binding.btLetsPlay.visibility = View.VISIBLE
                }
            }
        })

        binding.btLetsPlay.setOnClickListener {
            findNavController().popBackStack()
        }

    }


}