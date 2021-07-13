package az.zero.azshop.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import az.zero.azshop.R
import az.zero.azshop.adapter.child_adapter.ChildAdapter
import az.zero.azshop.databinding.FragmentCartBinding
import az.zero.azshop.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

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
    }
}