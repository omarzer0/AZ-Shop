package az.zero.azshop.ui

import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

open class BaseFragment(layout: Int) : Fragment(layout) {
    //        (requireActivity() as AppCompatActivity).supportActionBar?.hide()


//    @Suppress("DEPRECATION")
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//        requireActivity().window.insetsController?.hide(WindowInsets.Type.statusBars())
//    } else {
//        requireActivity().window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )
//    }
}