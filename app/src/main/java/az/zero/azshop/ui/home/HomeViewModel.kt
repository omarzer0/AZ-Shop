package az.zero.azshop.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.zero.azshop.data.Product
import az.zero.azshop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val productEventChannel = Channel<ProductEvent>()
    val productEvent = productEventChannel.receiveAsFlow()


    fun getFakeDataForHomeItemCategory() = productRepository.getFakeDataForHomeItemCategory()

    fun getFakeDataForHomeItemProduct() = productRepository.getFakeDataForHomeItemProduct()

    fun getFakeDataForHomeParentItemProduct() =
        productRepository.getFakeDataForHomeParentItemProduct()

    fun onProductSelected(product: Product) = viewModelScope.launch {
        productEventChannel.send(ProductEvent.NavigateToDetailsFragmentWithProduct(product))
    }
}

sealed class ProductEvent {
    /* we need not to pass anything so we use object for better performance
       (can also use data class with no args)*/
    data class NavigateToDetailsFragmentWithProduct(val product: Product) : ProductEvent()

}