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

    fun getNumberOfItemsInCart() = dao.getNumberOfItemsInCart()
    fun getTotalPrice() = dao.getTotalPrice()


    // TODO: fake data must be replaced with real data
    fun getFakeDataForHomeItemCategory(): List<Category> = listOf(
        Category("Food", R.drawable.food),
        Category("Drinks", R.drawable.drinks),
        Category("Bakery", R.drawable.bakery),
        Category("Vegetables", R.drawable.vegetables),
        Category("Snacks", R.drawable.snacks),
        Category("Electronics", R.drawable.electronics)
    )


    fun getFakeDataForHomeItemProduct(position: Int): List<Product> =
        when (position) {
            5 -> getFakeDataForElectronics()
            else -> getFoodFakeData()
        }


    fun getFoodFakeData(): List<Product> = listOf(
        Product("Beef Burger", "Tasty burger with cheese", R.drawable.burger2, "Burger", 6.0, 0.0),
        Product(
            "Big Heart Burger",
            "Tasty burger with cheese",
            R.drawable.burger3,
            "Burger",
            6.5,
            0.0
        ),
        Product("State Burger", "Tasty burger with cheese", R.drawable.burger4, "Burger", 7.0, 5.0),

        Product("Banana Crepe", "Tasty Crepe", R.drawable.crepe1, "Crepe", 9.0, 0.0),
        Product("Strawberry Crepe", "Tasty Crepe", R.drawable.crepe2, "Crepe", 8.0, 0.0),
        Product("Chicken Crepe", "Tasty Crepe", R.drawable.crepe3, "Crepe", 11.5, 6.0),
        Product("Cocktail Crepe", "Tasty Crepe", R.drawable.crepe4, "Crepe", 6.0, 0.0),

        Product("Fries", "Tasty Fries", R.drawable.fries1, "Fries", 2.0, 0.0),
        Product("Sauce Fries", "Tasty Fries", R.drawable.fries2, "Fries", 3.0, 0.0),
        Product("Lord Fries", "Tasty Fries", R.drawable.fries3, "Fries", 6.0, 4.0),

        Product("Pasta", "Tasty Pasta", R.drawable.pasta1, "Pasta", 12.0, 0.0),
        Product("Cheese Pasta", "Tasty Pasta with", R.drawable.pasta2, "Pasta", 16.0, 14.0),
        Product("French Pasta", "Tasty Pasta with", R.drawable.pasta3, "Pasta", 20.0, 0.0),
        Product("Shrimp Pasta", "Tasty Pasta with", R.drawable.pasta4, "Pasta", 35.0, 29.0),

        Product("Mushroom Pizza", "Tasty Pizza", R.drawable.pizza1, "Pizza", 26.0, 0.0),
        Product("Big Slice Pizza", "Tasty Pizza", R.drawable.pizza2, "Pizza", 36.0, 26.0),
        Product("Nomad Pizza", "Tasty Pizza", R.drawable.pizza3, "Pizza", 20.0, 0.0)
    )

    fun getFakeDataForElectronics(): List<Product> = listOf(
        Product("Hp Laptop", "Hp original laptop", R.drawable.laptop1, "Laptop", 120.0, 0.0),
        Product("Hp Laptop", "Hp original laptop", R.drawable.laptop2, "Laptop", 130.0, 0.0),
        Product("Hp Laptop", "Hp original laptop", R.drawable.laptop4, "Laptop", 135.0, 0.0),
        Product("Hp Laptop", "Hp original laptop", R.drawable.tablet22, "Laptop", 135.0, 0.0),
        Product("Hp Laptop", "Hp original laptop", R.drawable.tablet11, "Laptop", 135.0, 0.0),
    )

    fun getForYou(): List<Product> = listOf(
        Product("State Burger", "Tasty burger with cheese", R.drawable.burger4, "Burger", 7.0, 5.0),
        Product("Beef Burger", "Tasty burger with cheese", R.drawable.burger2, "Burger", 6.0, 0.0),
        Product("Banana Crepe", "Tasty Crepe", R.drawable.crepe1, "Crepe", 9.0, 0.0),
        Product("Cheese Pasta", "Tasty Pasta with", R.drawable.pasta2, "Pasta", 16.0, 14.0),
        Product("Big Slice Pizza", "Tasty Pizza", R.drawable.pizza2, "Pizza", 36.0, 26.0),
        Product("Hp Laptop", "Hp original laptop", R.drawable.laptop2, "Laptop", 130.0, 0.0),
        Product("Strawberry Crepe", "Tasty Crepe", R.drawable.crepe2, "Crepe", 8.0, 0.0),
        Product("Hp Laptop", "Hp original laptop", R.drawable.laptop2, "Laptop", 130.0, 0.0),
        Product("Sauce Fries", "Tasty Fries", R.drawable.fries2, "Fries", 3.0, 0.0),
        Product("Hp Laptop", "Hp original laptop", R.drawable.laptop2, "Laptop", 130.0, 0.0),
    )

    fun getOffers(): List<Product> = listOf(
        Product("State Burger", "Tasty burger with cheese", R.drawable.burger4, "Burger", 7.0, 5.0),
//        Product("Hp Laptop", "Hp original laptop", R.drawable.laptop3, "Laptop", 160.0, 140.0),
        Product("Big Slice Pizza", "Tasty Pizza", R.drawable.pizza2, "Pizza", 36.0, 26.0),
        Product("Lord Fries", "Tasty Fries", R.drawable.fries3, "Fries", 6.0, 4.0),
        Product("Cheese Pasta", "Tasty Pasta with", R.drawable.pasta2, "Pasta", 16.0, 14.0),
        Product("Fries", "Tasty Fries", R.drawable.fries1, "Fries", 4.0, 2.0),
        Product("Sauce Fries", "Tasty Fries", R.drawable.fries2, "Fries", 3.0, 1.5),
    )

    fun getPopular(): List<Product> = listOf(
        Product("State Burger", "Tasty burger with cheese", R.drawable.burger4, "Burger", 7.0, 5.0),
        Product("Beef Burger", "Tasty burger with cheese", R.drawable.burger2, "Burger", 6.0, 0.0),
        Product("State Burger", "Tasty burger with cheese", R.drawable.burger4, "Burger", 7.0, 5.0),
        Product("Hp Laptop", "Hp original laptop", R.drawable.laptop2, "Laptop", 130.0, 0.0),
        Product("Sauce Fries", "Tasty Fries", R.drawable.fries2, "Fries", 3.0, 1.5),
        Product("Cheese Pasta", "Tasty Pasta with", R.drawable.pasta2, "Pasta", 16.0, 14.0),
    )

    fun getFakeCategoryNames(): List<String> = getFakeDataForHomeItemCategory().map {
        it.name
    }
}