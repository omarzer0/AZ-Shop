package az.zero.azshop.adapter.child_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import az.zero.azshop.R
import az.zero.azshop.data.Product
import az.zero.azshop.databinding.ItemChildProductBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.lang.String.valueOf

class ChildAdapter : ListAdapter<Product, ChildAdapter.ChildAdapterViewHolder>(COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildAdapterViewHolder {
        val binding = ItemChildProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChildAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildAdapterViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) holder.bind(currentItem)
    }


    inner class ChildAdapterViewHolder(private val binding: ItemChildProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        onInnerChildProductClickListener?.let {
                            it(getItem(adapterPosition))
                        }
                    }
                }
            }
        }

        fun bind(currentItem: Product) {
            binding.apply {

                tvProductItemName.text = currentItem.name

                val offerPrice = currentItem.offerPrice
                tvProductItemPrice.isVisible = offerPrice == 0.0
                tvProductItemPrice.text = valueOf("$${currentItem.price}")

                tvProductItemDiscountPrice.isVisible = offerPrice != 0.0
                tvProductItemDiscountPrice.text = valueOf("$$offerPrice")

                Glide.with(itemView).load(currentItem.image).error(R.drawable.ic_no_image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivProductItemImage)
            }
        }
    }

    private var onInnerChildProductClickListener: ((Product) -> Unit)? = null
    fun setOnInnerChildProductClickListener(listener: (Product) -> Unit) {
        onInnerChildProductClickListener = listener
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