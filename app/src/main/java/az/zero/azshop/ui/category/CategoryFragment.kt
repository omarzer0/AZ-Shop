package az.zero.azshop.ui.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import az.zero.azshop.R
import az.zero.azshop.adapter.single_selection_adapter.SingleSelectionAdapter
import az.zero.azshop.databinding.FragmentCategoryBinding
import az.zero.azshop.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment(R.layout.fragment_category) {
    private val viewModel: CategoryFragmentViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCategoryBinding.bind(view)

        val singleSelectionAdapter = SingleSelectionAdapter(0)
        binding.apply {
            rvCategory.apply {
                adapter = singleSelectionAdapter
                setHasFixedSize(true)
            }
        }

        singleSelectionAdapter.submitList(viewModel.getCategoriesName())
    }
}