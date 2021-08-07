package az.zero.azshop.adapter.single_selection_adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import az.zero.azshop.R
import az.zero.azshop.databinding.ItemSingleSelectionCategoryBinding
import az.zero.azshop.utils.setLayoutMargin

/**
 * Simple Adapter to handle single selection on RecyclerView
 * Implemented using [ListAdapter] with [DiffUtil.ItemCallback]
 * for computing diffs between Lists on a background thread,
 * viewBinding, and clickListeners
 * */
class SingleSelectionAdapter(private var lastCheckedPosition: Int = 0) :
    ListAdapter<String, SingleSelectionAdapter.ItemViewHolder>(COMPARATOR) {

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

                tvSingleCategoryItemName.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onSingleItemUnSelected(binding, itemView)
                        notifyItemChanged(lastCheckedPosition)
                        lastCheckedPosition = adapterPosition
                        onCategoryNameClickListener?.let {
                            it(adapterPosition)
                        }
                    }
                }
            }
        }

        fun bind(currentItem: String) {
            binding.apply {
                tvSingleCategoryItemName.text = currentItem
                if (adapterPosition != lastCheckedPosition) {
                    onSingleItemSelected(binding, itemView)
                } else {
                    onSingleItemUnSelected(binding, itemView)
                }

                if (adapterPosition == 0) {
                    setLayoutMargin(itemView.context, root, 100f, 40f, 16f, 16f)
                }
//
//                if (adapterPosition == itemCount - 1) {
//                    setLayoutMargin(itemView.context, root, 100f, 40f, 0f, 32f)
//
//                }
//                if (adapterPosition != 0 && adapterPosition != itemCount - 1) {
//                    setLayoutMargin(itemView.context, root, 100f, 40f, 0f, 16f)
//                }

            }
        }
    }

    private var onCategoryNameClickListener: ((Int) -> Unit)? = null
    fun setOnCategoryNameClickListener(listener: (Int) -> Unit) {
        onCategoryNameClickListener = listener
    }

    private fun onSingleItemSelected(binding: ItemSingleSelectionCategoryBinding, itemView: View) =
        binding.apply {
            clItemCategory.background =
                ContextCompat.getDrawable(itemView.context, R.drawable.shape_single_selection_white)

            tvSingleCategoryItemName.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.black
                )
            )
        }

    private fun onSingleItemUnSelected(
        binding: ItemSingleSelectionCategoryBinding,
        itemView: View
    ) =
        binding.apply {
            clItemCategory.background = ContextCompat.getDrawable(
                itemView.context,
                R.drawable.shape_single_selection_main_color
            )

            tvSingleCategoryItemName.setTextColor(
                ContextCompat.getColor(
                    itemView.context,
                    R.color.white
                )
            )
        }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem


            override fun areContentsTheSame(oldItem: String, newItem: String) =
                oldItem == newItem
        }
    }
}