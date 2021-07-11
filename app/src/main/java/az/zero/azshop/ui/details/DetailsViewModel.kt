package az.zero.azshop.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import az.zero.azshop.data.Product
import az.zero.azshop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private fun insertProduct(product: Product) = viewModelScope.launch {
        productRepository.insertProduct(product)
    }

    private fun updateProduct(product: Product) = viewModelScope.launch {
        productRepository.updateProduct(product)
    }

}