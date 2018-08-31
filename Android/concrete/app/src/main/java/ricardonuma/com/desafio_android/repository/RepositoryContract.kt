package ricardonuma.com.desafio_android.repository

import ricardonuma.com.desafio_android.base.MvpPresenter
import ricardonuma.com.desafio_android.base.MvpView
import ricardonuma.com.desafio_android.data.remote.model.repository.ObjectResponse

/**
 * Created by ricardonuma on 12/21/17.
 */

interface RepositoryContract {

    interface View : MvpView {

        fun showRepositoryResults(objectResponse: ObjectResponse?)
    }

    interface Presenter : MvpPresenter<View> {

        fun loadRepositoryList(page: Int, updateFromSwipe: Boolean)
    }
}