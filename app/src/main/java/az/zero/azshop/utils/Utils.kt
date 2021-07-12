package az.zero.azshop.utils

/**
 * converts statement of type [Any] into expression of the same type
 * to get the benefit of compile-time safety
 * for ex: turns
 *
 *     fun foo():Boolean { return true } -> fun foo():Boolean = true
 * */
val <T> T.exhaustive: T
    get() = this