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
    val image: Int,
    val category: String,
    val price: Double,
    val offerPrice: Double = 0.0,
    var numberOfItemsInCart: Int = 1,
    var created: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null
) : Parcelable