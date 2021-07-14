package az.zero.azshop.ui.cart

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import az.zero.azshop.R
import az.zero.azshop.adapter.child_adapter.ChildAdapter
import az.zero.azshop.databinding.FragmentCartBinding
import az.zero.azshop.ui.BaseFragment
import az.zero.azshop.utils.exhaustive
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class CartFragment : BaseFragment(R.layout.fragment_cart) {
    val viewModel: CartViewModel by viewModels()
    private lateinit var childAdapter: ChildAdapter
    private lateinit var binding: FragmentCartBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCartBinding.bind(view)
        childAdapter = ChildAdapter(true)

        bindViews()
        setObservers()
        setClickListeners()
        collectCartEvents()
    }

    private fun setObservers() {
        viewModel.getAllProductsInCart().observe(viewLifecycleOwner, {
            childAdapter.submitList(it)
            viewModel.receivedEmptyList(it.isEmpty())
        })

        viewModel.getTotalPrice().observe(viewLifecycleOwner, { totalPrice ->
            viewModel.onTotalPriceReceived(totalPrice)
        })
    }

    private fun setClickListeners() {
        childAdapter.setOnCartBodyClickListener { viewModel.setOnCartBodyClick(it) }
        childAdapter.setOnCartDeleteProductClickListener { viewModel.setOnCartDeleteProductClick(it) }
    }

    private fun bindViews() {
        binding.apply {
            rvCart.apply {
                adapter = childAdapter
                setHasFixedSize(true)
            }
        }
    }

    private fun collectCartEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.cartEvent.collect { event ->
                when (event) {
                    is CartEvent.NavigateToDetailsFragmentWithProduct -> {
                        val action =
                            CartFragmentDirections.actionCartFragmentToDetailsFragment(
                                event.product,
                                false
                            )
                        findNavController().navigate(action)
                    }
                    is CartEvent.ShowOrHideNoItemsInCartImage -> {
                        binding.ivEmptyCart.isVisible = event.shouldShowImage
                    }
                    is CartEvent.ShowButtonTotalText -> {
                        binding.btnCheckOut.text = "Total: $${event.totalPrice ?: 0}"
                        binding.btnCheckOut.isVisible = event.totalPrice != null
                    }
                }.exhaustive
            }
        }
    }
}