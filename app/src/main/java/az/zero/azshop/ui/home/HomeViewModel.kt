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

    fun getFakeDataForHomeItemCategory(): List<Category> =
        List(100) {
            Category("Item $it", R.drawable.lavender)
        }

    private fun getFakeDataForHomeItemProduct(): List<Product> =
        List(100) {
            Product(
                "Item $it",
                "this is description",
                R.drawable.lavender,
                "fruits",
                12.0,
                0.0,
                id = it
            )
        }

    fun getFakeDataForHomeParentItemProduct(): List<Parent> =
        List(4) {
            Parent(
                "Item $it",
                getFakeDataForHomeItemProduct()
            )
        }
}