package az.zero.azshop.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.zero.azshop.data.Product
import az.zero.azshop.repository.ProductRepository
import az.zero.azshop.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    val product = state.get<Product>(PRODUCT_ID)


    var name = state.get<String>(NAME) ?: product?.name ?: ""
        set(value) {
            field = value
            state.set(NAME, value)
        }

    var description = state.get<String>(DISCRIPTION) ?: product?.description ?: ""
        set(value) {
            field = value
            state.set(DISCRIPTION, value)
        }

    var image = state.get<Int>(IMAGE) ?: product?.image ?: -1
        set(value) {
            field = value
            state.set(IMAGE, value)
        }

    var category = state.get<String>(CATEGORY) ?: product?.category ?: ""
        set(value) {
            field = value
            state.set(CATEGORY, value)
        }

    var price = state.get<Double>(PRICE) ?: product?.price ?: 0.0
        set(value) {
            field = value
            state.set(PRICE, value)
        }

    var offerPrice = state.get<Double>(OFFER_PRICE) ?: product?.offerPrice ?: 0.0
        set(value) {
            field = value
            state.set(OFFER_PRICE, value)
        }

    var numberOfItemsInCart =
        state.get<Int>(NUMBER_OF_ITEMS_IN_CART) ?: product?.numberOfItemsInCart ?: 1
        set(value) {
            field = value
            state.set(NUMBER_OF_ITEMS_IN_CART, value)
        }

    var created = state.get<Long>(CREATED) ?: product?.created ?: System.currentTimeMillis()
        set(value) {
            field = value
            state.set(CREATED, value)
        }

    var id = state.get<Int>(ID) ?: product?.id ?: 1
        set(value) {
            field = value
            state.set(ID, value)
        }

    private fun insertProduct(product: Product) = viewModelScope.launch {
        productRepository.insertProduct(product)
    }

    private fun updateProduct(product: Product) = viewModelScope.launch {
        productRepository.updateProduct(product)
    }

}