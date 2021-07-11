package az.zero.azshop.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "product_table")
@Parcelize
data class Product(
    val name: String,
    val description: String,
    val image: String,
    val price: Double,
    val category: String,
    val created: Long = System.currentTimeMillis(),
    val numberOfItemsInCart: Int = 1,
    @PrimaryKey
    val id: Int
) : Parcelable