package az.zero.azshop.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import az.zero.azshop.R
import az.zero.azshop.databinding.FragmentDatailsBinding
import az.zero.azshop.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment(R.layout.fragment_datails) {
    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDatailsBinding.bind(view)

        binding.apply {
            populateViews()
        }
    }

    private fun FragmentDatailsBinding.populateViews() {

    }

}