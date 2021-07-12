package az.zero.azshop.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import az.zero.azshop.R
import az.zero.azshop.adapter.category_adapter.CategoryAdapter
import az.zero.azshop.adapter.parent_adapter.ParentAdapter
import az.zero.azshop.databinding.FragmentHomeBinding
import az.zero.azshop.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)

        val categoryAdapter = CategoryAdapter()
        val parentAdapter = ParentAdapter()
        binding.apply {

            setupCategoryRV(categoryAdapter)

            setupParentRV(parentAdapter)
        }
        categoryAdapter.submitList(viewModel.getFakeDataForHomeItemCategory())
        parentAdapter.submitList(viewModel.getFakeDataForHomeParentItemProduct())

        onProductItemClick(parentAdapter)
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

    private fun onProductItemClick(parentAdapter: ParentAdapter){
        parentAdapter.childAdapter.setOnInnerChildProductClickListener {product->
            viewModel.onProductSelected(product)
        }
    }
}