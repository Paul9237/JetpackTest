package com.ootori.jetpacktest.ui.sofa

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.ootori.jetpacktest.R
import com.ootori.libnavannotation.FragmentDestination

@FragmentDestination(pageUrl = "main/tabs/sofa", needLogin = true)
class SofaFragment : Fragment() {

    private lateinit var sofaViewModel: SofaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sofaViewModel =
            ViewModelProvider(this).get(SofaViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_sofa, container, false)
        val textView: TextView = root.findViewById(R.id.tv_hello)
        sofaViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}