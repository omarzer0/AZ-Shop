package az.zero.azshop.dp

import androidx.room.Database
import androidx.room.RoomDatabase
import az.zero.azshop.data.Product

@Database(entities = [Product::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}