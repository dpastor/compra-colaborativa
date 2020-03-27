package com.tuenti.compracolaborativa.ui.products

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.tuenti.compracolaborativa.R
import com.tuenti.compracolaborativa.data.model.OrderProduct

class ProductsAdapter(
    private val userList: List<OrderProduct>
) : RecyclerView.Adapter<OrderProductViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderProductViewHolder {
        return OrderProductViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(
                    R.layout.item_products,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: OrderProductViewHolder, position: Int) {
        val title = holder.view.findViewById<TextView>(
            R.id.title
        )
        title.text = userList[position].name
        val subtitle = holder.view.findViewById<TextView>(
            R.id.quantity
        )
        subtitle.text = "(${userList[position].quantity})"
        holder.view.findViewById<CheckBox>(R.id.checkbox)
            .setOnCheckedChangeListener { checkbox, isSelected ->
                if (isSelected) {
                    title.paintFlags = title.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    subtitle.paintFlags = subtitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                } else {
                    title.paintFlags = title.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                    subtitle.paintFlags = subtitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                }
            }
    }

    override fun getItemCount() = userList.size
}

class OrderProductViewHolder(
    val view: View
) : RecyclerView.ViewHolder(view)