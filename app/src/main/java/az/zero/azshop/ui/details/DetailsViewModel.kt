package az.zero.azshop.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.zero.azshop.R
import az.zero.azshop.data.Product
import az.zero.azshop.repository.ProductRepository
import az.zero.azshop.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val detailsRepository: ProductRepository,
    private val state: SavedStateHandle
) : ViewModel() {
    private val detailsEventChannel = Channel<DetailFragmentEvent>()
    val detailsEvent = detailsEventChannel.receiveAsFlow()

    // the id must be the same as the args name in navGraph
    var product = state.get<Product>(PRODUCT_ID)

    var isAddNotEdit = state.get<Boolean>(IS_ADD_NOT_EDIT) ?: true
        set(value) {
            field = value
            state.set(IS_ADD_NOT_EDIT, value)
        }

    var name = state.get<String>(NAME) ?: product?.name ?: "No name"
        set(value) {
            field = value
            state.set(NAME, value)
        }

    var description = state.get<String>(DISCRIPTION) ?: product?.description ?: "No description"
        set(value) {
            field = value
            state.set(DISCRIPTION, value)
        }

    var image = state.get<Int>(IMAGE) ?: product?.image ?: R.drawable.ic_no_image
        set(value) {
            field = value
            state.set(IMAGE, value)
        }

    var category = state.get<String>(CATEGORY) ?: product?.category ?: ""
        set(value) {
            field = value
            state.set(CATEGORY, value)
        }

    var price = state.get<Double>(PRICE) ?: product?.originalPrice ?: 0.0
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

    val numberOfItemsInCartObserver = MutableLiveData(numberOfItemsInCart)

    private fun insertProduct(product: Product) = viewModelScope.launch {
        detailsRepository.insertProduct(product)
    }

    private fun updateProduct(product: Product) = viewModelScope.launch {
        detailsRepository.updateProduct(product)
    }

    fun onPlusBtnClick() {
        numberOfItemsInCart++
        product?.numberOfItemsInCart = numberOfItemsInCart
        numberOfItemsInCartObserver.postValue(numberOfItemsInCart)
    }

    fun onMinusBtnClick() {
        if (numberOfItemsInCart > 1) {
            numberOfItemsInCart--
            product?.numberOfItemsInCart = numberOfItemsInCart
            numberOfItemsInCartObserver.postValue(numberOfItemsInCart)
        }
    }

    fun onAddOrEditProductToCartClick() {
        product?.let { product ->
            if (isAddNotEdit) insertProduct(product)
            else updateProduct(product)

            viewModelScope.launch {
                detailsEventChannel.send(DetailFragmentEvent.NavigateBack)
            }
        }
    }
}

sealed class DetailFragmentEvent {
    object NavigateBack : DetailFragmentEvent()
}