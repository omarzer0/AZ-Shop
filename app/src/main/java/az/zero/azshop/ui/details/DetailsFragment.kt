package az.zero.azshop.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import az.zero.azshop.R
import az.zero.azshop.databinding.FragmentDatailsBinding
import az.zero.azshop.ui.BaseFragment
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : BaseFragment(R.layout.fragment_datails) {
    private val viewModel: DetailsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDatailsBinding.bind(view)

        binding.apply {
            populateViews()
            btnPlus.setOnClickListener { viewModel.onPlusBtnClick() }
            btnMinus.setOnClickListener { viewModel.onMinusBtnClick() }
            btnAddToCart.setOnClickListener { viewModel.onAddOrEditProductToCartClick() }
        }

        viewModel.numberOfItemsInCartObserver.observe(viewLifecycleOwner, {
            binding.tvQuantity.text = "${viewModel.numberOfItemsInCart}"
        })
    }

    private fun FragmentDatailsBinding.populateViews() {
        tvProductName.text = viewModel.name
        tvPrice.text = "$${viewModel.price}"
        tvOfferPrice.text = "$${viewModel.offerPrice}"
        Glide.with(requireContext()).load(viewModel.image).into(ivProductImg)
        tvDescription.text = viewModel.description
        tvQuantity.text = "${viewModel.numberOfItemsInCart}"
        btnAddToCart.text = if (viewModel.isAddNotEdit) getString(R.string.add_to_cart)
        else getString(R.string.edit_product_in_cart)
    }

}