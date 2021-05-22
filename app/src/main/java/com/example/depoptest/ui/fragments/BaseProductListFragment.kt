package com.example.depoptest.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.depoptest.R
import com.example.depoptest.adapters.ProductAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

open class BaseProductListFragment(layout: Int) : BaseProductFragment(layout) {

    @Inject
    internal lateinit var productsAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(false)
        showBottomNavBar()
    }

    private fun showBottomNavBar() {
        val navBar = activity?.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        navBar?.let {
            it.visibility = View.VISIBLE
        }
    }

    internal fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.apply {
            adapter = productsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun noInternet() {
        hasInternet = false
        Snackbar.make(
            requireView(),
            "No internet connection",
            Snackbar.LENGTH_LONG
        ).show()
    }

}