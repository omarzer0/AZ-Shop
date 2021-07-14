package az.zero.azshop.ui.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import az.zero.azshop.R
import az.zero.azshop.adapter.category_adapter.CategoryAdapter
import az.zero.azshop.adapter.child_adapter.ChildAdapter
import az.zero.azshop.adapter.child_adapter.OnProductItemClickLister
import az.zero.azshop.data.Product
import az.zero.azshop.databinding.FragmentHomeBinding
import az.zero.azshop.ui.BaseFragment
import az.zero.azshop.utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class HomeFragment : BaseFragment(R.layout.fragment_home), OnProductItemClickLister {

    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentHomeBinding.bind(view)

        val categoryAdapter = CategoryAdapter()
        // removed nestedRecyclerView duo to saving RV position and child RVs after navigating
        val forYouAdapter = ChildAdapter()
        val offerAdapter = ChildAdapter()
        val popularAdapter = ChildAdapter()
        addListenersToChildAdapters(forYouAdapter, offerAdapter, popularAdapter)
        addListenerToCategoryAdapter(categoryAdapter)

        binding.apply {

            setUpRVs(categoryAdapter, forYouAdapter, offerAdapter, popularAdapter)
            addListenersToViewAllTVs(tvForYouViewAll, tvOfferViewAll, tvPopularViewAll)
            btnOpenCart.setOnClickListener {
                val action = HomeFragmentDirections.actionHomeFragmentToCartFragment()
                findNavController().navigate(action)
            }
        }
        submitAdaptersLists(categoryAdapter, forYouAdapter, offerAdapter, popularAdapter)

        collectProductEvents()
    }

    private fun addListenersToViewAllTVs(vararg tvs: TextView) {
        for (tv in tvs) {
            tv.setOnClickListener {
                viewModel.onViewAllClick(0)
            }
        }
    }

    private fun addListenersToChildAdapters(vararg adapters: ChildAdapter) {
        for (adapter in adapters) {
            adapter.initOnProductItemClickLister(this)
        }
    }

    private fun submitAdaptersLists(
        categoryAdapter: CategoryAdapter,
        forYouAdapter: ChildAdapter,
        offerAdapter: ChildAdapter,
        popularAdapter: ChildAdapter
    ) {
        categoryAdapter.submitList(viewModel.getFakeDataForHomeItemCategory())
        forYouAdapter.submitList(viewModel.getFakeDataForHomeItemProduct())
        offerAdapter.submitList(viewModel.getFakeDataForHomeItemProduct())
        popularAdapter.submitList(viewModel.getFakeDataForHomeItemProduct())
    }

    private fun FragmentHomeBinding.setUpRVs(
        categoryAdapter: CategoryAdapter,
        forYouAdapter: ChildAdapter,
        offerAdapter: ChildAdapter,
        popularAdapter: ChildAdapter
    ) {
        rvCategory.apply {
            adapter = categoryAdapter
            setHasFixedSize(true)
        }
        rvForYou.apply {
            adapter = forYouAdapter
            setHasFixedSize(true)
        }
        rvOffer.apply {
            adapter = offerAdapter
            setHasFixedSize(true)
        }
        rvPopular.apply {
            adapter = popularAdapter
            setHasFixedSize(true)
        }
    }

    private fun addListenerToCategoryAdapter(categoryAdapter: CategoryAdapter) {
        categoryAdapter.setOnImageCategoryClickLister {position->
            viewModel.onCategoryClick(position)
        }
    }

    private fun collectProductEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.productEvent.collect { event ->
                when (event) {
                    is HomeFragmentEvent.NavigateToDetailsFragmentWithHomeFragment -> {
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                                event.product,
                                true
                            )
                        findNavController().navigate(action)
                    }
                    is HomeFragmentEvent.NavigateToCategoryFragment -> {
                        val action = HomeFragmentDirections.actionHomeFragmentToCategoryFragment(event.position)
                        findNavController().navigate(action)
                    }
                }.exhaustive
            }
        }
    }

    override fun onProductClick(product: Product) {
        viewModel.onProductClicked(product)
    }
}