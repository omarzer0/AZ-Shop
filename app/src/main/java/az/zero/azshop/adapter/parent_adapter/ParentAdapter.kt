package az.zero.azshop.adapter.parent_adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import az.zero.azshop.adapter.child_adapter.ChildAdapter
import az.zero.azshop.data.Parent
import az.zero.azshop.databinding.ItemParentProductBinding

class ParentAdapter(val childAdapter: ChildAdapter) :
    ListAdapter<Parent, ParentAdapter.ParentAdapterViewHolder>(COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentAdapterViewHolder {
        val binding = ItemParentProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ParentAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ParentAdapterViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }


    inner class ParentAdapterViewHolder(private val binding: ItemParentProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(currentItem: Parent) {
            binding.apply {
                tvItemRvTitle.text = currentItem.name
                rvItemRvInnerRv.apply {
                    adapter = childAdapter
                    setHasFixedSize(true)
                }

                childAdapter.submitList(currentItem.childList)
            }
        }

    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Parent>() {
            override fun areItemsTheSame(oldItem: Parent, newItem: Parent) =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Parent, newItem: Parent) =
                oldItem == newItem
        }
    }
}