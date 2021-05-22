package com.example.depoptest.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.depoptest.R
import com.example.depoptest.adapters.ProductAdapter
import com.example.depoptest.data.local.model.Product
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_favourite_list.*

@AndroidEntryPoint
class ProductFavouriteListFragment : BaseProductListFragment(R.layout.fragment_product_favourite_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        subscribeToObservers()
        setupRecyclerView(rvFavouriteProducts)
    }

    private fun setupAdapter() {
        productsAdapter.setOnItemClickListener { product ->
            findNavController().navigate(
                ProductFavouriteListFragmentDirections.actionProductFavouriteListFragmentToProductDetailFragment(
                    product
                )
            )
        }
    }

    private fun subscribeToObservers() {
        viewModel.favouriteProducts.observe(viewLifecycleOwner) { products ->
            productsAdapter.submitList(products)
            configureEmptyState(products)
        }
    }

    private fun configureEmptyState(products: List<Product>) {
        if (products.isEmpty()) {
            empty_state.visibility = View.VISIBLE
        }
    }

}