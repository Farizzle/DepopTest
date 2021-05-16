package com.example.depoptest.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.depoptest.R
import com.example.depoptest.adapters.ProductAdapter
import com.example.depoptest.util.Resource.Status.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_list.*

@AndroidEntryPoint
class ProductListFragment : BaseProductFragment(R.layout.fragment_product_list) {

    private lateinit var productAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        subscribeToObservers()
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.removeCurrentProduct()
    }

    private fun setupAdapter() {
        productAdapter = ProductAdapter(glide)
        productAdapter.setOnItemClickListener { product ->
            findNavController().navigate(
                ProductListFragmentDirections.actionProductListFragmentToProductDetailFragment(
                    product
                )
            )
        }
    }

    private fun subscribeToObservers() {
        viewModel.allProducts.observe(viewLifecycleOwner, { products ->
            productAdapter.submitList(products)
            configureErrorState()
        })
        viewModel.productsStatus.observe(viewLifecycleOwner, { event ->
            event.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    LOADING -> progressBar.isVisible = true
                    SUCCESS -> progressBar.isVisible = false
                    ERROR -> {
                        progressBar.isVisible = false
                    }
                }
            }
        })
    }

    private fun setupRecyclerView() {
        rvProducts.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun configureErrorState() {
        if (productAdapter.currentList.isEmpty() && !hasInternet) {
            error_state.visibility = View.VISIBLE
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