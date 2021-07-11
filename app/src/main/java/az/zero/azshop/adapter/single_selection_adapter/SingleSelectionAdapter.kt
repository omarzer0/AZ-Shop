package az.zero.azshop.adapter.single_selection_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import az.zero.azshop.data.Product
import az.zero.azshop.databinding.ItemSingleSelectionCategoryBinding

/**
 * Simple Adapter to handle single selection on RecyclerView
 * Implemented using [ListAdapter] with [DiffUtil.ItemCallback]
 * for computing diffs between Lists on a background thread,
 * viewBinding, and clickListeners
 * */
class SingleSelectionAdapter :
    ListAdapter<Product, SingleSelectionAdapter.ItemViewHolder>(COMPARATOR) {

    private var lastCheckedPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemSingleSelectionCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) holder.bind(currentItem)
    }

    inner class ItemViewHolder(private val binding: ItemSingleSelectionCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // add clickListener in init block to limit the number of clickListeners being instantiated
        init {
            binding.apply {

                onSingleItemUnSelected(binding)
                notifyItemChanged(lastCheckedPosition)
                lastCheckedPosition = adapterPosition
            }
        }

        fun bind(currentItem: Product) {
            binding.apply {
                if (adapterPosition != lastCheckedPosition) {
                    onSingleItemSelected(binding)
                } else {
                    onSingleItemUnSelected(binding)
                }
            }
        }
    }

    private fun onSingleItemSelected(binding: ItemSingleSelectionCategoryBinding) = binding.apply {

    }

    private fun onSingleItemUnSelected(binding: ItemSingleSelectionCategoryBinding) =
        binding.apply {

        }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product) =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: Product, newItem: Product) =
                oldItem == newItem
        }
    }
}