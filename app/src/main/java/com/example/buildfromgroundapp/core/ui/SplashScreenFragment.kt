package com.example.buildfromgroundapp.core.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.buildfromgroundapp.R
import com.example.buildfromgroundapp.databinding.FragmentSplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch



@AndroidEntryPoint
class SplashScreenFragment : Fragment() {

    private lateinit var binding: FragmentSplashScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSplashScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val slideAnim = AnimationUtils.loadAnimation(requireContext(), R.anim.zoom_in_zoom_out)
        val rightToLeft = AnimationUtils.loadAnimation(requireContext(), R.anim.right_to_left)


        lifecycleScope.launch {
            val anim1 = launch {
                binding.notes.apply {
                    startAnimation(slideAnim)
                    delay(1000)
                }
            }
            val anim2 = launch {
                binding.pen.apply {
                    startAnimation(rightToLeft)
                    delay(1000)
                }
            }
            anim1.join()
            anim2.join()
            findNavController().navigate(R.id.action_splashFeedFragment_to_newsFeedFragment)
        }
    }
}
// launch, most use launch to start a coroutine, and usually for non-result background tasks operation, especially for UI related or fire and forget tasks.
// error handling: exception will be propagate to parent coroutine unless be handled
// Async, most use async to start a coroutine for  multiple running tasks, and usually for result-returning background tasks operation, especially for parallel tasks.
// error handling: exception will be encapsulated in Deferred, and can be handled by await() or try-catch block
// You MUST NOT forget about the coroutine you’ve started with async. If you forget to call await() on the Deferred, the coroutine will be cancelled and the exception will be ignored.