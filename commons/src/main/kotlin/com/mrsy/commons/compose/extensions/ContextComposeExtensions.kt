package com.mrsy.commons.compose.extensions

import android.app.Activity
import android.content.Context
import com.mrsy.commons.R
import com.mrsy.commons.extensions.baseConfig
import com.mrsy.commons.extensions.redirectToRateUs
import com.mrsy.commons.extensions.toast
import com.mrsy.commons.helpers.BaseConfig

val Context.config: BaseConfig get() = BaseConfig.newInstance(applicationContext)

fun Activity.rateStarsRedirectAndThankYou(stars: Int) {
    if (stars == 5) {
        redirectToRateUs()
    }
    toast(R.string.thank_you)
    baseConfig.wasAppRated = true
}
