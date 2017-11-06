package com.bancopan.ricardonuma.bancopan.base

/**
 * Created by ricardonuma on 31/10/17.
 */

interface BaseView {

    enum class ProgressType {
        PROGRESS_DIALOG,
        PROGRESS_VIEW,
        PROGRESS_SWIPE
    }


    fun showProgress(type: ProgressType)
    fun hideProgress()
    fun onConnectionFailed()
    fun onAuthError()
}