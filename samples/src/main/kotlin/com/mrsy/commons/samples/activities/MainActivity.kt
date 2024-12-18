package com.mrsy.commons.samples.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.mrsy.commons.activities.AboutActivity
import com.mrsy.commons.activities.CustomizationActivity
import com.mrsy.commons.activities.ManageBlockedNumbersActivity
import com.mrsy.commons.compose.alert_dialog.AlertDialogState
import com.mrsy.commons.compose.alert_dialog.rememberAlertDialogState
import com.mrsy.commons.compose.extensions.*
import com.mrsy.commons.compose.theme.AppThemeSurface
import com.mrsy.commons.dialogs.ConfirmationDialog
import com.mrsy.commons.dialogs.DonateAlertDialog
import com.mrsy.commons.dialogs.RateStarsAlertDialog
import com.mrsy.commons.extensions.hideKeyboard
import com.mrsy.commons.extensions.launchMoreAppsFromUsIntent
import com.mrsy.commons.extensions.launchViewIntent
import com.mrsy.commons.helpers.*
import com.mrsy.commons.models.FAQItem
import com.mrsy.commons.samples.BuildConfig
import com.mrsy.commons.samples.R
import com.mrsy.commons.samples.screens.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdgeSimple()
        setContent {
            AppThemeSurface {
                val showMoreApps = onEventValue { !resources.getBoolean(com.mrsy.commons.R.bool.hide_google_relations) }

                MainScreen(
                    openColorCustomization = ::startCustomizationActivity,
                    manageBlockedNumbers = {
                        startActivity(Intent(this@MainActivity, ManageBlockedNumbersActivity::class.java))
                    },
                    showComposeDialogs = {
                        startActivity(Intent(this@MainActivity, TestDialogActivity::class.java))
                    },
                    openTestButton = {
                        ConfirmationDialog(
                            this@MainActivity,
                            FAKE_VERSION_APP_LABEL,
                            positive = com.mrsy.commons.R.string.ok,
                            negative = 0
                        ) {
                            launchViewIntent(DEVELOPER_PLAY_STORE_URL)
                        }
                    },
                    showMoreApps = showMoreApps,
                    openAbout = ::launchAbout,
                    moreAppsFromUs = ::launchMoreAppsFromUsIntent
                )
                AppLaunched()
            }
        }
    }

    @Composable
    private fun AppLaunched(
        donateAlertDialogState: AlertDialogState = getDonateAlertDialogState(),
        rateStarsAlertDialogState: AlertDialogState = getRateStarsAlertDialogState(),
    ) {
        LaunchedEffect(Unit) {
            appLaunchedCompose(
                appId = BuildConfig.APPLICATION_ID,
                showDonateDialog = donateAlertDialogState::show,
                showRateUsDialog = rateStarsAlertDialogState::show,
                showUpgradeDialog = {}
            )
        }
    }

    @Composable
    private fun getDonateAlertDialogState() =
        rememberAlertDialogState().apply {
            DialogMember {
                DonateAlertDialog(alertDialogState = this)
            }
        }

    @Composable
    private fun getRateStarsAlertDialogState() = rememberAlertDialogState().apply {
        DialogMember {
            RateStarsAlertDialog(alertDialogState = this, onRating = ::rateStarsRedirectAndThankYou)
        }
    }

    private fun startCustomizationActivity() {
        Intent(applicationContext, CustomizationActivity::class.java).apply {
            putExtra(APP_ICON_IDS, getAppIconIDs())
            putExtra(APP_LAUNCHER_NAME, getAppLauncherName())
            startActivity(this)
        }
    }

    private fun launchAbout() {
        val licenses = LICENSE_AUTOFITTEXTVIEW

        val faqItems = arrayListOf(
            FAQItem(com.mrsy.commons.R.string.faq_1_title_commons, com.mrsy.commons.R.string.faq_1_text_commons),
            FAQItem(com.mrsy.commons.R.string.faq_1_title_commons, com.mrsy.commons.R.string.faq_1_text_commons),
            FAQItem(com.mrsy.commons.R.string.faq_4_title_commons, com.mrsy.commons.R.string.faq_4_text_commons)
        )

        if (!resources.getBoolean(com.mrsy.commons.R.bool.hide_google_relations)) {
            faqItems.add(FAQItem(com.mrsy.commons.R.string.faq_2_title_commons, com.mrsy.commons.R.string.faq_2_text_commons))
            faqItems.add(FAQItem(com.mrsy.commons.R.string.faq_6_title_commons, com.mrsy.commons.R.string.faq_6_text_commons))
        }

        startAboutActivity(R.string.smtco_app_name, licenses, BuildConfig.VERSION_NAME, faqItems, true)
    }

    private fun startAboutActivity(
        appNameId: Int, licenseMask: Long, versionName: String, faqItems: ArrayList<FAQItem>, showFAQBeforeMail: Boolean,
        getAppIconIDs: ArrayList<Int> = getAppIconIDs(),
        getAppLauncherName: String = getAppLauncherName()
    ) {
        hideKeyboard()
        Intent(applicationContext, AboutActivity::class.java).apply {
            putExtra(APP_ICON_IDS, getAppIconIDs)
            putExtra(APP_LAUNCHER_NAME, getAppLauncherName)
            putExtra(APP_NAME, getString(appNameId))
            putExtra(APP_LICENSES, licenseMask)
            putExtra(APP_VERSION_NAME, versionName)
            putExtra(APP_FAQ, faqItems)
            putExtra(SHOW_FAQ_BEFORE_MAIL, showFAQBeforeMail)
            startActivity(this)
        }
    }

    private fun getAppLauncherName() = getString(R.string.smtco_app_name)

    private fun getAppIconIDs(): ArrayList<Int> {
        val ids = ArrayList<Int>()
        ids.add(R.mipmap.commons_launcher)
        return ids
    }
}
