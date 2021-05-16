package com.example.depoptest.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.example.depoptest.R
import com.example.depoptest.adapters.ProductImagesAdapter
import com.example.depoptest.data.local.model.Product
import com.example.depoptest.ui.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_detail.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailFragment : Fragment(R.layout.fragment_product_detail) {

    private val viewModel: ProductViewModel by viewModels()

    private lateinit var productImagesAdapter: ProductImagesAdapter

    @Inject
    lateinit var glide: RequestManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fetchCurrentProductAndUpdate()
        setupAdapter()
        subscribeToObservers()
        setupRecyclerView()
    }

    private fun fetchCurrentProductAndUpdate() {
        arguments?.let { bundle ->
            val product = ProductDetailFragmentArgs.fromBundle(bundle).product
            viewModel.assignCurrentProduct(product)
        }!!
    }

    private fun setupAdapter() {
        productImagesAdapter = ProductImagesAdapter(glide)
    }

    private fun subscribeToObservers() {
        viewModel.currentProduct.observe(viewLifecycleOwner, { product ->
            product?.let { safeProduct ->
                lifecycleScope.launch {
                    val delayDuration = requireContext().resources.getInteger(R.integer.transition_duration) + 100
                    delay(delayDuration.toLong())
                    configureProductDetails(safeProduct)
                    productImagesAdapter.submitList(safeProduct.pictures_data)
                }
            }
        })
    }

    private fun configureProductDetails(product: Product) {
        product_details_container.visibility = View.VISIBLE
        buy_banner.visibility = View.VISIBLE
        product_price.text = product.productCost
        user_id.text = product.user_id.toString()
        product_description.text = product.description
        hand_delivery_status.setImageDrawable(isValidService(product.hand_delivery))
        paypal_status.setImageDrawable(isValidService(product.purchase_via_paypal))
        if (product.international_shipping_cost != "0.00") {
            international_shipping_cost.visibility = View.VISIBLE
            international_shipping_cost.text =
                "International shipping cost - ${product.internationalShippingCost}"
        }
        if (product.national_shipping_cost != "0.00") {
            national_shipping_cost.visibility = View.VISIBLE
            national_shipping_cost.text = "National shipping cost - ${product.nationalShippingCost}"
        }
    }

    private fun isValidService(status: Boolean): Drawable {
        return if (status) {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_check)
        } else {
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_cross)
        }!!
    }

    private fun setupRecyclerView() {
        rvProductImages.apply {
            adapter = productImagesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }


}