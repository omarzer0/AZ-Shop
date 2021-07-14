package az.zero.azshop.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import az.zero.azshop.data.Product
import az.zero.azshop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val cartEventChannel = Channel<CartEvent>()
    val cartEvent = cartEventChannel.receiveAsFlow()


    fun getAllProductsInCart() = productRepository.getAllProductsInCart().asLiveData()

    fun setOnCartBodyClick(product: Product) = viewModelScope.launch {
        cartEventChannel.send(CartEvent.NavigateToDetailsFragmentWithProduct(product))
    }

    fun setOnCartDeleteProductClick(product: Product) = viewModelScope.launch {
        productRepository.deleteProduct(product)
    }
}

sealed class CartEvent {
    data class NavigateToDetailsFragmentWithProduct(val product: Product) : CartEvent()
}