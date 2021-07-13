package az.zero.azshop.ui.category

import androidx.lifecycle.ViewModel
import az.zero.azshop.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryFragmentViewModel @Inject constructor(
    private val productRepository: ProductRepository
): ViewModel() {
}