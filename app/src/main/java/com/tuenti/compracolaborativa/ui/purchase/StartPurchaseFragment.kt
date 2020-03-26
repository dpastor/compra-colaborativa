package com.tuenti.compracolaborativa.ui.purchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tuenti.compracolaborativa.R

class StartPurchaseFragment : Fragment() {

    private lateinit var startPurchaseViewModel: StartPurchaseViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        startPurchaseViewModel =
                ViewModelProviders.of(this).get(StartPurchaseViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_start_purchase, container, false)
        val textView: TextView = root.findViewById(R.id.text_start_purchase)
        startPurchaseViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
