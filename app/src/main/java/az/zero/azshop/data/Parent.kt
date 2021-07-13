package az.zero.azshop.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Parent(
    val name: String,
    val childList: List<Product>
):Parcelable