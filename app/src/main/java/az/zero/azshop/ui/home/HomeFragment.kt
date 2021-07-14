package az.zero.azshop.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.isVisible
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
    private lateinit var forYouAdapter: ChildAdapter
    private lateinit var offerAdapter: ChildAdapter
    private lateinit var popularAdapter: ChildAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        setHasOptionsMenu(true)
        initAdapters()
        addAdaptersListener()
        bindViews()
//        observeNumberOfItemsInCart(tvNumberOfItemsInCart)
        submitAdaptersLists(categoryAdapter, forYouAdapter, offerAdapter, popularAdapter)
        collectProductEvents()
    }
//
//    private fun observeNumberOfItemsInCart(tvNumberOfItemsInCart: TextView) {
//        viewModel.getNumberOfItemsInCart().observe(viewLifecycleOwner, { numberOfItemsInCart ->
//            tvNumberOfItemsInCart.text = "$numberOfItemsInCart"
//            tvNumberOfItemsInCart.isVisible =
//                (numberOfItemsInCart != null && numberOfItemsInCart > 0)
//        })
//    }

    private fun initAdapters() {
        categoryAdapter = CategoryAdapter()
        forYouAdapter = ChildAdapter()
        offerAdapter = ChildAdapter()
        popularAdapter = ChildAdapter()
    }

    private fun addAdaptersListener() {
        addListenersToChildAdapters(forYouAdapter, offerAdapter, popularAdapter)
        addListenerToCategoryAdapter()
    }

    private fun bindViews() {
        binding.apply {

            setUpRVs(categoryAdapter, forYouAdapter, offerAdapter, popularAdapter)
            addListenersToViewAllTVs(tvForYouViewAll, tvOfferViewAll, tvPopularViewAll)
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

    private fun addListenerToCategoryAdapter() {
        categoryAdapter.setOnImageCategoryClickLister { position ->
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
                        val action =
                            HomeFragmentDirections.actionHomeFragmentToCategoryFragment(event.position)
                        findNavController().navigate(action)
                    }
                    HomeFragmentEvent.NavigateToCartFragment -> {
                        val action = HomeFragmentDirections.actionHomeFragmentToCartFragment()
                        findNavController().navigate(action)
                    }
                }.exhaustive
            }
        }
    }

    override fun onProductClick(product: Product) {
        viewModel.onProductClicked(product)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.options_menu, menu)

        val menuItem = menu.findItem(R.id.menu_cart).actionView

        val tvNumberOfItemsInCart: TextView =
            menuItem.findViewById(R.id.tv_cart_badge_number_of_items)

        viewModel.getNumberOfItemsInCart().observe(viewLifecycleOwner, {
            tvNumberOfItemsInCart.text = "${it ?: 0}"
            tvNumberOfItemsInCart.isVisible = it != null && it > 0
        })

        val flCartImage: FrameLayout = menuItem.findViewById(R.id.fl_root)
        flCartImage.setOnClickListener {
            viewModel.cartImageClicked()
        }
    }
}