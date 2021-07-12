package az.zero.azshop.adapter.category_adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import az.zero.azshop.data.Category
import az.zero.azshop.databinding.ItemCategoryBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlin.math.log

class CategoryAdapter : ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
        }

        fun bind(currentItem: Category) {
            binding.apply {
                tvCategoryItemName.text = currentItem.name
                Glide.with(itemView).load(currentItem.image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivCategoryItemImage)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Category, newItem: Category) =
                oldItem == newItem
        }
    }
}