package com.ootori.jetpacktest.ui.publish

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.ootori.jetpacktest.R
import com.ootori.libnavannotation.ActivityDestination

@ActivityDestination(pageUrl = "main/tabs/publish", needLogin = true)
class PublishActivity : AppCompatActivity() {

    private lateinit var viewModel: PublishViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish)

        viewModel = ViewModelProvider(this).get(PublishViewModel::class.java)
    }

}