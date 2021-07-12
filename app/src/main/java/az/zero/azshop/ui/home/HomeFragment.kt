package az.zero.azshop.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import az.zero.azshop.R
import az.zero.azshop.adapter.category_adapter.CategoryAdapter
import az.zero.azshop.adapter.child_adapter.ChildAdapter
import az.zero.azshop.adapter.parent_adapter.ParentAdapter
import az.zero.azshop.databinding.FragmentHomeBinding
import az.zero.azshop.ui.BaseFragment
import az.zero.azshop.utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)

        val categoryAdapter = CategoryAdapter()
        val parentAdapter = ParentAdapter(ChildAdapter())
        binding.apply {

            setupCategoryRV(categoryAdapter)

            setupParentRV(parentAdapter)
        }
        categoryAdapter.submitList(viewModel.getFakeDataForHomeItemCategory())
        parentAdapter.submitList(viewModel.getFakeDataForHomeParentItemProduct())

        onProductItemClick(parentAdapter)
        onViewMoreClick(parentAdapter)
        collectProductEvents()
    }


    private fun FragmentHomeBinding.setupParentRV(parentAdapter: ParentAdapter) {
        rvHomeFragmentParentRv.apply {
            adapter = parentAdapter
            setHasFixedSize(true)
        }
    }

    private fun FragmentHomeBinding.setupCategoryRV(categoryAdapter: CategoryAdapter) {
        rvCategory.apply {
            adapter = categoryAdapter
            setHasFixedSize(true)
        }
    }

    private fun onProductItemClick(parentAdapter: ParentAdapter) {
        parentAdapter.childAdapter.setOnInnerChildProductClickListener { product ->
            viewModel.onProductSelected(product)
        }
    }

    private fun onViewMoreClick(parentAdapter: ParentAdapter) {
        parentAdapter.setOnInnerChildViewAllCategoryClickListener {
            viewModel.onViewMoreSelected(viewModel.getFakeDataForHomeParentItemProduct())
        }
    }

    private fun collectProductEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.productEvent.collect { event ->
                when (event) {
                    is ProductEvent.NavigateToDetailsFragmentWithProduct -> {
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(event.product)
                        findNavController().navigate(action)
                    }
                    is ProductEvent.NavigateToCategoryFragmentWithListCategoryAndNames -> {
                        val action = HomeFragmentDirections.actionHomeFragmentToCategoryFragment(
                            event.categoriesAndNames.toTypedArray()
                        )
                        findNavController().navigate(action)
                    }
                }.exhaustive
            }
        }
    }
}