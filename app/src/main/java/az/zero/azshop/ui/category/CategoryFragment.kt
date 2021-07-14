package az.zero.azshop.ui.category

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import az.zero.azshop.R
import az.zero.azshop.adapter.child_adapter.ChildAdapter
import az.zero.azshop.adapter.child_adapter.OnProductItemClickLister
import az.zero.azshop.adapter.single_selection_adapter.SingleSelectionAdapter
import az.zero.azshop.data.Product
import az.zero.azshop.databinding.FragmentCategoryBinding
import az.zero.azshop.ui.BaseFragment
import az.zero.azshop.utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class CategoryFragment : BaseFragment(R.layout.fragment_category), OnProductItemClickLister {
    private val viewModel: CategoryFragmentViewModel by viewModels()
    private lateinit var singleSelectionAdapter: SingleSelectionAdapter
    private lateinit var childAdapter: ChildAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCategoryBinding.bind(view)

        initAdapters()
        bindViews(binding)
        submitInitialLists()
        collectEvents()
        setSingleSelectionAdapterListener()
    }

    private fun initAdapters() {
        singleSelectionAdapter = SingleSelectionAdapter(viewModel.position)
        childAdapter = ChildAdapter(needsMatchParent = true)
        childAdapter.initOnProductItemClickLister(this)
    }

    private fun bindViews(binding: FragmentCategoryBinding) {
        binding.apply {
            setRVs()
        }
    }

    private fun submitInitialLists() {
        singleSelectionAdapter.submitList(viewModel.getCategoriesName())
        childAdapter.submitList(viewModel.getFakeDataForHomeItemProduct(viewModel.position))
    }

    private fun FragmentCategoryBinding.setRVs() {
        rvCategory.apply {
            adapter = singleSelectionAdapter
            setHasFixedSize(true)
            itemAnimator = null
            scrollToPosition(viewModel.position)
        }

        rvSelectedCategory.apply {
            adapter = childAdapter
            setHasFixedSize(true)
        }
    }

    private fun setSingleSelectionAdapterListener() {
        singleSelectionAdapter.setOnCategoryNameClickListener { position ->
            viewModel.position = position
            viewModel.onCategoryItemClicked(position)
        }
    }

    private fun collectEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.categoryEvent.collect { event ->
                when (event) {
                    is CategoryEvent.NavigateToDetailsFragmentWithProduct -> {
                        val action = CategoryFragmentDirections
                            .actionCategoryFragmentToDetailsFragment(event.product, true)
                        findNavController().navigate(action)
                    }
                    is CategoryEvent.SubmitNewData -> {
                        val products = event.products
                        childAdapter.submitList(products)
                        childAdapter.notifyDataSetChanged()
                    }
                }.exhaustive
            }
        }
    }

    override fun onProductClick(product: Product) {
        viewModel.onProductItemClicked(product)
    }
}