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
    val relatedItems: List<Product>,
    val numberOfItemsInCart: Int = 1,
    @PrimaryKey
    val id: Int
) : Parcelable