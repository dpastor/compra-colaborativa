package com.tuenti.compracolaborativa.ui.purchase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartPurchaseViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Start purchase"
    }
    val text: LiveData<String> = _text
}