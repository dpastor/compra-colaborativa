package com.tuenti.compracolaborativa.ui.products

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuenti.compracolaborativa.R
import com.tuenti.compracolaborativa.data.model.OrderProduct

class ProductsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        val list: RecyclerView = findViewById(R.id.list)
        list.layoutManager = LinearLayoutManager(this);
        list.adapter = ProductsAdapter(
            listOf(
                OrderProduct("Leche", 12),
                OrderProduct("Huevos", 6),
                OrderProduct("Pan de molde", 1),
                OrderProduct("Carne picada (100gr)", 2),
                OrderProduct("Pavo en lonchas", 3),
                OrderProduct("Chorizo", 1),
                OrderProduct("Lubina", 2),
                OrderProduct("Yogur natural", 6),
                OrderProduct("Dulce de leche", 13)
            )
        )
    }
}