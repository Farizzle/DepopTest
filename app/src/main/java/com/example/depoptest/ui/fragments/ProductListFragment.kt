package com.example.depoptest.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.depoptest.R
import com.example.depoptest.util.Resource.Status.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_list.*

@AndroidEntryPoint
class ProductListFragment : BaseProductListFragment(R.layout.fragment_product_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        subscribeToObservers()
        setupRecyclerView(rvProducts)
    }

    private fun setupAdapter() {
        productsAdapter.setOnItemClickListener { product ->
            findNavController().navigate(
                ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(
                    product
                )
            )
        }
    }

    private fun subscribeToObservers() {
        viewModel.allProducts.observe(viewLifecycleOwner, { products ->
            productsAdapter.submitList(products)
            configureErrorState()
        })
        viewModel.productsStatus.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    LOADING -> progressBar.isVisible = true
                    SUCCESS -> progressBar.isVisible = false
                    ERROR -> {
                        if (hasInternet) {
                            Snackbar.make(
                                requireView(),
                                "Oops! Something went wrong",
                                Snackbar.LENGTH_LONG
                            ).show()
                        }
                        progressBar.isVisible = false
                    }
                }
            }
        })
    }

    private fun configureErrorState() {
        if (productsAdapter.currentList.isEmpty() && !hasInternet) {
            error_state.visibility = View.VISIBLE
        }
    }

}