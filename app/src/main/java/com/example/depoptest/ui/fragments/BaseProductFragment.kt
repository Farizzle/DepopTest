package com.example.depoptest.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.RequestManager
import com.example.depoptest.ui.ProductViewModel
import com.example.depoptest.util.hasInternetConnection
import javax.inject.Inject

abstract class BaseProductFragment(layout: Int) : Fragment(layout) {

    internal val viewModel: ProductViewModel by viewModels()

    @Inject
    lateinit var glide: RequestManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!hasInternetConnection(requireContext())){
            noInternet()
        }
    }

    abstract fun noInternet()
}