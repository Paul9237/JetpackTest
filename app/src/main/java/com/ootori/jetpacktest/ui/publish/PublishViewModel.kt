package com.ootori.jetpacktest.ui.publish

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PublishViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is publish activity"
    }
    val text: LiveData<String> = _text
}