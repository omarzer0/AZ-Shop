package az.zero.azshop.adapter.child_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import az.zero.azshop.R
import az.zero.azshop.data.Product
import az.zero.azshop.databinding.ItemCartBinding
import az.zero.azshop.databinding.ItemChildProductBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.lang.String.valueOf

class ChildAdapter(
    private val inCartFragment: Boolean = false,
    private val needsMatchParent: Boolean = false
) :
    ListAdapter<Product, ChildAdapter.ChildAdapterViewHolder>(COMPARATOR) {
    private var listener: OnProductItemClickLister? = null

    fun initOnProductItemClickLister(listener: OnProductItemClickLister) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildAdapterViewHolder {
        val binding = if (inCartFragment) {
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        } else {
            ItemChildProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        }
        return ChildAdapterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildAdapterViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) holder.bind(currentItem)
    }


    inner class ChildAdapterViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            when (binding) {
                is ItemChildProductBinding ->
                    binding.apply {
                        root.setOnClickListener {
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                listener?.onProductClick(getItem(adapterPosition))
                            }
                        }

                        if (needsMatchParent) {
                            cvRoot.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                        }
                    }

                is ItemCartBinding -> {
                    binding.apply {
                        root.setOnClickListener {
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                onCartBodyClickListener?.let { it(getItem(adapterPosition)) }
                            }
                        }

                        btnRemoveProduct.setOnClickListener {
                            if (adapterPosition != RecyclerView.NO_POSITION) {
                                onCartDeleteProductClickListener?.let { it(getItem(adapterPosition)) }
                            }
                        }
                    }
                }
            }
        }

        fun bind(currentItem: Product) {
            when (binding) {
                is ItemChildProductBinding ->
                    binding.apply {
                        tvProductItemName.text = currentItem.name

                        val offerPrice = currentItem.offerPrice
                        tvProductItemPrice.isVisible = offerPrice == 0.0
                        tvProductItemPrice.text = valueOf("$${currentItem.price}")

                        tvOfferPrice.isVisible = offerPrice != 0.0
                        tvOfferPrice.text = valueOf("$$offerPrice")

                        Glide.with(itemView).load(currentItem.image).error(R.drawable.ic_no_image)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(ivProductItemImage)
                    }

                is ItemCartBinding -> {
                    binding.apply {
                        tvProductItemName.text = currentItem.name
                        Glide.with(itemView).load(currentItem.image).into(ivProductItemImage)

                        val offerPrice = currentItem.offerPrice
                        tvProductItemPrice.isVisible = offerPrice == 0.0
                        tvProductItemPrice.text = valueOf("$${currentItem.price}")

                        tvOfferPrice.isVisible = offerPrice != 0.0
                        tvOfferPrice.text = valueOf("$$offerPrice")

                        tvNumberOfItemsInCart.text = "x${currentItem.numberOfItemsInCart}"
                    }
                }
            }
        }
    }

    private var onCartBodyClickListener: ((Product) -> Unit)? = null
    fun setOnCartBodyClickListener(listener: (Product) -> Unit) {
        onCartBodyClickListener = listener
    }

    private var onCartDeleteProductClickListener: ((Product) -> Unit)? = null
    fun setOnCartDeleteProductClickListener(listener: (Product) -> Unit) {
        onCartDeleteProductClickListener = listener
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

interface OnProductItemClickLister {
    fun onProductClick(product: Product)
}