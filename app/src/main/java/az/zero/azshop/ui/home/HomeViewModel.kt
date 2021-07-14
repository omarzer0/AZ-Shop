package az.zero.azshop.ui.home

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
class HomeViewModel @Inject constructor(
    private val homeRepository: ProductRepository
) : ViewModel() {

    private val productEventChannel = Channel<HomeFragmentEvent>()
    val productEvent = productEventChannel.receiveAsFlow()

    fun getNumberOfItemsInCart() = homeRepository.getNumberOfItemsInCart().asLiveData()

    fun getFakeDataForHomeItemCategory() = homeRepository.getFakeDataForHomeItemCategory()

    fun getFakeDataForHomeItemProduct() = homeRepository.getFakeDataForHomeItemProduct(0)

    fun onProductClicked(product: Product) = viewModelScope.launch {
        productEventChannel.send(HomeFragmentEvent.NavigateToDetailsFragmentWithHomeFragment(product))
    }

    fun onViewAllClick(position: Int) = viewModelScope.launch {
        productEventChannel.send(
            HomeFragmentEvent.NavigateToCategoryFragment(position)
        )
    }

    fun onCategoryClick(position: Int) = viewModelScope.launch {
        productEventChannel.send(
            HomeFragmentEvent.NavigateToCategoryFragment(position)
        )
    }

    fun cartImageClicked() = viewModelScope.launch {
        productEventChannel.send(HomeFragmentEvent.NavigateToCartFragment)
    }
}

sealed class HomeFragmentEvent {
    data class NavigateToDetailsFragmentWithHomeFragment(val product: Product) : HomeFragmentEvent()
    data class NavigateToCategoryFragment(val position: Int) : HomeFragmentEvent()
    object NavigateToCartFragment : HomeFragmentEvent()
}