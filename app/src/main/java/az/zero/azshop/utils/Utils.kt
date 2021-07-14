package az.zero.azshop.utils

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout


/**
 * converts statement of type [Any] into expression of the same type
 * to get the benefit of compile-time safety
 * for ex: turns
 *
 *     fun foo():Boolean { return true } -> fun foo():Boolean = true
 * */
val <T> T.exhaustive: T
    get() = this


fun convertDpToPx(context: Context, dp: Float): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        context.resources.getDisplayMetrics()
    ).toInt()
}

fun setLayoutMargin(
    context: Context,
    view: View,
    widthInDp: Float,
    heightInDp: Float,
    marginStart: Float,
    marginEnd: Float
) {
    val widthInPX = convertDpToPx(context, widthInDp)
    val heightInPX = convertDpToPx(context, heightInDp)
    val params = ConstraintLayout.LayoutParams(widthInPX, heightInPX)

    params.marginStart = convertDpToPx(context, marginStart)
    params.marginEnd = convertDpToPx(context, marginEnd)
    view.layoutParams = params
}