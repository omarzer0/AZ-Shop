package az.zero.azshop.ui

import android.annotation.SuppressLint
import android.os.Build
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import az.zero.azshop.R
import az.zero.azshop.utils.changeActionBarColor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

open class BaseFragment(layout: Int) : Fragment(layout) {

    fun enterChangeAppBarAndStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requireActivity().window.statusBarColor =
                ContextCompat.getColor(requireContext(), R.color.app_background_color)
        }

        changeActionBarColor(
            R.color.app_background_color, true,
            (activity as MainActivity).supportActionBar, (activity as MainActivity)
        )

    }

    fun exitChangeAppBarAndStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            requireActivity().window.statusBarColor =
                ContextCompat.getColor(requireContext(), R.color.main_color)
        }
        changeActionBarColor(
            R.color.main_color, false, (activity as MainActivity).supportActionBar,
            (activity as MainActivity)
        )
    }

    @SuppressLint("RestrictedApi")
    fun enterFullScreen() {
        // hide appBar without animation
        (requireActivity() as AppCompatActivity).supportActionBar?.setShowHideAnimationEnabled(false)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()

        // hide statusBar
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            requireActivity().window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    fun exitFullScreen() {
        // show appBar
        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        // show statusBar
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            requireActivity().window.insetsController?.show(WindowInsets.Type.statusBars())
        } else {
            requireActivity().window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
}