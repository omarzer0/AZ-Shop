package az.zero.azshop.repository

import az.zero.azshop.R
import az.zero.azshop.data.Category
import az.zero.azshop.data.Product
import az.zero.azshop.dp.ProductDao
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val dao: ProductDao
) {

    fun getAllProductsInCart() = dao.getAllProductsInCart()

    suspend fun insertProduct(product: Product) = dao.insert(product)
    suspend fun updateProduct(product: Product) = dao.update(product)
    suspend fun deleteProduct(product: Product) = dao.delete(product)

    // TODO 2: use custom application scope to run delete all (to insure not interruption)

    fun getFakeDataForHomeItemCategory(): List<Category> =
        List(10) {
            Category("Item $it", R.drawable.lavender)
        }

    fun getFakeDataForHomeItemProduct(position :Int): List<Product> =
        List(10) {
            Product(
                "Item $it $position$position$position",
                "this is description",
                R.drawable.lavender,
                "fruits",
                12.0,
                0.0
            )
        }

    fun getFakeCategoryNames(): List<String> = getFakeDataForHomeItemCategory().map {
        it.name
    }
}