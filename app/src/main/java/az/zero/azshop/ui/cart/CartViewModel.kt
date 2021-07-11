package az.zero.azshop.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import az.zero.azshop.data.Product
import az.zero.azshop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private fun deleteProduct(product: Product) = viewModelScope.launch {
        productRepository.deleteProduct(product)
    }

    private fun getAllProductsInCart() = productRepository.getAllProductsInCart().asLiveData()
}