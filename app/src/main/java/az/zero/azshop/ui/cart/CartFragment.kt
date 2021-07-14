package az.zero.azshop.ui.cart

import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCartBinding.bind(view)

        val childAdapter = ChildAdapter(true)
        binding.apply {
            rvCart.apply {
                adapter = childAdapter
                setHasFixedSize(true)
            }
        }

        viewModel.getAllProductsInCart().observe(viewLifecycleOwner, {
            childAdapter.submitList(it)
        })

        childAdapter.setOnCartBodyClickListener { viewModel.setOnCartBodyClick(it) }
        childAdapter.setOnCartDeleteProductClickListener { viewModel.setOnCartDeleteProductClick(it) }

        collectCartEvents()
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
                }.exhaustive
            }
        }
    }
}