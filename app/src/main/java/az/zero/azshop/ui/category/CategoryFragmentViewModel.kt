package az.zero.azshop.ui.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import az.zero.azshop.data.Product
import az.zero.azshop.repository.ProductRepository
import az.zero.azshop.utils.POSITION_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryFragmentViewModel @Inject constructor(
    private val categoryRepository: ProductRepository,
    private val state: SavedStateHandle
) : ViewModel() {

    var position = state.get<Int>(POSITION_ID) ?: 0
        set(value) {
            field = value
            state.set(POSITION_ID, value)
        }

    private val categoryEventChannel = Channel<CategoryEvent>()
    val categoryEvent = categoryEventChannel.receiveAsFlow()

    fun getCategoriesName() = categoryRepository.getFakeCategoryNames()
    fun getFakeDataForHomeItemProduct(position: Int): List<Product> {
        viewModelScope.launch {
            categoryEventChannel.send(CategoryEvent.UpdateTitleTV(getCategoriesName()[position]))
        }
        return categoryRepository.getFakeDataForHomeItemProduct(position)
    }

    fun onProductItemClicked(product: Product) = viewModelScope.launch {
        categoryEventChannel.send(CategoryEvent.NavigateToDetailsFragmentWithProduct(product))
    }

    fun onCategoryItemClicked(position: Int) = viewModelScope.launch {
        // get new data with name of item at the position passed
        categoryEventChannel.send(CategoryEvent.SubmitNewData(getFakeDataForHomeItemProduct(position)))
    }
}

sealed class CategoryEvent {
    data class NavigateToDetailsFragmentWithProduct(val product: Product) : CategoryEvent()
    data class SubmitNewData(val products: List<Product>) : CategoryEvent()
    data class UpdateTitleTV(val title: String) : CategoryEvent()
}