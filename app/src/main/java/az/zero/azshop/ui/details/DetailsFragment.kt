package az.zero.azshop.ui.details

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import az.zero.azshop.R
import az.zero.azshop.databinding.FragmentDatailsBinding
import az.zero.azshop.ui.BaseFragment
import az.zero.azshop.utils.exhaustive
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


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

        observeNumberOdItems(binding)
        collectDetailsEvents()
    }

    private fun observeNumberOdItems(binding: FragmentDatailsBinding) {
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

    private fun collectDetailsEvents() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.detailsEvent.collect { event ->
                when (event) {
                    DetailFragmentEvent.NavigateBack -> findNavController().popBackStack()
                }.exhaustive
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        enterFullScreen()
    }

    override fun onDetach() {
        super.onDetach()
        exitFullScreen()
    }
}