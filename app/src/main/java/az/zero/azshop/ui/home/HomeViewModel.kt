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

    private val productEventChannel = Channel<HomeFragmentEvent>()
    val productEvent = productEventChannel.receiveAsFlow()


    fun getFakeDataForHomeItemCategory() = productRepository.getFakeDataForHomeItemCategory()

    fun getFakeDataForHomeItemProduct() = productRepository.getFakeDataForHomeItemProduct(0)

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
}

sealed class HomeFragmentEvent {
    /* we need not to pass anything so we use object for better performance
       (can also use data class with no args)*/
    data class NavigateToDetailsFragmentWithHomeFragment(val product: Product) : HomeFragmentEvent()
//    data class NavigateToDetailsFragmentWithHomeFragment(val product: Product) : HomeFragmentEvent()
    data class NavigateToCategoryFragment(val position:Int) : HomeFragmentEvent()

}