package com.ricardonuma.desafioandroid.repository

import com.ricardonuma.desafioandroid.base.MvpPresenter
import com.ricardonuma.desafioandroid.base.MvpView
import com.ricardonuma.desafioandroid.data.remote.model.repository.ObjectResponse

/**
 * Created by ricardonuma on 30/12/17.
 */

interface RepositoryContract {

    interface View : MvpView {

        fun showRepositoryResults(objectResponse: ObjectResponse?)
    }

    interface Presenter : MvpPresenter<View> {

        fun loadRepositoryList(page: Int, updateFromSwipe: Boolean)
    }
}