package com.example.shoppinglistbr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglistbr.data.Product
import com.example.shoppinglistbr.data.Products
import com.example.shoppinglistbr.databinding.ActivityMainBinding
import com.example.shoppinglistbr.databinding.ProductItemBinding

class MyProductRecyclerViewAdapter(
    private val values: List<Product>,
    private val eventListener: ShoppingListListener
    ): RecyclerView.Adapter<MyProductRecyclerViewAdapter.ViewHolder>()
{
        class ViewHolder(binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
        val contentView: TextView = binding.content
        val quantityView: TextView = binding.quantityView
        val categoryView: TextView = binding.categoryView
        val itemContainer: View = binding.root
        val check: CheckBox = binding.content
        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyProductRecyclerViewAdapter.ViewHolder {
        return ViewHolder(
            ProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MyProductRecyclerViewAdapter.ViewHolder, position: Int) {
        val product = values[position]

        holder.contentView.text = product.title
        holder.quantityView.text = product.count.toString() + " sztuk"
        holder.categoryView.text = product.category
        holder.check.isChecked = product.checked

        holder.check.setOnClickListener {
            product.checked = holder.check.isChecked
        }

        holder.itemContainer.setOnClickListener {
            eventListener.onProductClick(position)
        }
        holder.itemContainer.setOnLongClickListener {
            eventListener.onProductLongClick(position)
            return@setOnLongClickListener true
        }
    }

    override fun getItemCount(): Int {
        return values.size
    }
}