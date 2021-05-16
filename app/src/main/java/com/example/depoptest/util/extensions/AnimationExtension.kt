package com.example.depoptest.util.extensions

import android.animation.TimeInterpolator
import android.animation.ValueAnimator
import android.view.animation.AccelerateDecelerateInterpolator

const val STANDARD_DURATION = 1000L

inline fun getValueAnimator(
    forward: Boolean = true,
    duration: Long = STANDARD_DURATION,
    interpolator: TimeInterpolator = AccelerateDecelerateInterpolator(),
    crossinline updateListener: (progress: Float) -> Unit
): ValueAnimator =
    if (!forward) {
        ValueAnimator.ofFloat(0f, 1f)
    } else {
        ValueAnimator.ofFloat(1f, 0f)
    }.apply {
        addUpdateListener { updateListener(it.animatedValue as Float) }
        this.duration = duration
        this.interpolator = interpolator
    }
