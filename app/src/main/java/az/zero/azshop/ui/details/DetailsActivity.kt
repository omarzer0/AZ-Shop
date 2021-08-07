package az.zero.azshop.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import az.zero.azshop.databinding.ActivityDatailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDatailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}