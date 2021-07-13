package az.zero.azshop.ui.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import az.zero.azshop.R
import az.zero.azshop.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment(R.layout.fragment_category) {
    private val viewModel:CategoryFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}