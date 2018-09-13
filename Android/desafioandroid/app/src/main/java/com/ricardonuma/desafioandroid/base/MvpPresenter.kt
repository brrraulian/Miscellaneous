package com.ricardonuma.desafioandroid.base

/**
 * Created by ricardonuma on 29/12/17.
 */

interface MvpPresenter<V : MvpView> {

    fun attachView(mvpView: V?)

    fun detachView()
}