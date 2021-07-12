package az.zero.azshop.ui.home

import androidx.lifecycle.ViewModel
import az.zero.azshop.R
import az.zero.azshop.data.Category
import az.zero.azshop.data.Parent
import az.zero.azshop.data.Product
import az.zero.azshop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    fun getFakeDataForHomeItemCategory()= productRepository.getFakeDataForHomeItemCategory()

    fun getFakeDataForHomeItemProduct()= productRepository.getFakeDataForHomeItemProduct()

    fun getFakeDataForHomeParentItemProduct()= productRepository.getFakeDataForHomeParentItemProduct()

    fun onProductSelected(product: Product) {

    }
}