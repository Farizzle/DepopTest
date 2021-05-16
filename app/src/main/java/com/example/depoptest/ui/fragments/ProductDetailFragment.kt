package com.example.depoptest.ui.fragments

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.depoptest.R
import com.example.depoptest.adapters.ProductImagesAdapter
import com.example.depoptest.data.local.model.Product
import com.example.depoptest.util.extensions.getValueAnimator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_detail.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductDetailFragment : BaseProductFragment(R.layout.fragment_product_detail) {

    private lateinit var productImagesAdapter: ProductImagesAdapter

    private var hasInternet = true

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
                    val delayDuration =
                        requireContext().resources.getInteger(R.integer.transition_duration) + 100
                    delay(delayDuration.toLong())
                    configureProductDetails(safeProduct)
                    productImagesAdapter.submitList(safeProduct.pictures_data)
                    if (!hasInternet) showNoInternetCard()
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
            val linearLayoutManager =
                object : LinearLayoutManager(requireContext(), HORIZONTAL, false) {
                    override fun canScrollHorizontally(): Boolean {
                        return hasInternet
                    }
                }
            layoutManager = linearLayoutManager
        }
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(rvProductImages)
    }

    private fun showNoInternetCard() {
        getValueAnimator(hasInternet) {
            no_internet_container.alpha = it
        }.start()
    }

    override fun noInternet() {
        hasInternet = false
    }

}