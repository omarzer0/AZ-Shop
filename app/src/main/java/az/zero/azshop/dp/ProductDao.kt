package az.zero.azshop.dp

import androidx.room.*
import az.zero.azshop.data.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table ORDER BY created DESC")
    fun getAllProductsInCart(): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: Product)

    @Update
    suspend fun update(product: Product)

    @Delete
    suspend fun delete(product: Product)

    @Query("SELECT sum(numberOfItemsInCart) FROM product_table")
    fun getNumberOfItemsInCart(): Flow<Int>

    @Query("SELECT sum(savedPrice * numberOfItemsInCart) FROM product_table")
    fun getTotalPrice(): Flow<Double>

}