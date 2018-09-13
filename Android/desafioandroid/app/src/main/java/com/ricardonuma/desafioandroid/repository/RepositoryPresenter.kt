package com.ricardonuma.desafioandroid.repository

import com.ricardonuma.desafioandroid.base.BasePresenter
import com.ricardonuma.desafioandroid.base.MvpView
import com.ricardonuma.desafioandroid.data.MainRepository
import com.ricardonuma.desafioandroid.data.remote.model.repository.ObjectResponse
import rx.Scheduler
import rx.Subscriber

/**
 * Created by ricardonuma on 30/12/17.
 */

class RepositoryPresenter(private val userRepository: MainRepository?, private val ioScheduler: Scheduler, private val mainScheduler: Scheduler) : BasePresenter<RepositoryContract.View>(), RepositoryContract.Presenter {

    override fun loadRepositoryList(page: Int, updateFromSwipe: Boolean) {
        checkViewAttached()

        if (updateFromSwipe)
            view!!.showLoading(MvpView.ProgressType.PROGRESS_SWIPE)
        else
            view!!.showLoading(MvpView.ProgressType.PROGRESS_DIALOG)

        addSubscription(userRepository!!.loadRepositoryList(page).subscribeOn(ioScheduler).observeOn(mainScheduler).subscribe(object : Subscriber<ObjectResponse>() {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {
                view!!.hideLoading()
                view!!.showError(e.message) //TODO You probably don't want this error to show to users - Might want to show a friendlier message :)
            }

            override fun onNext(objectResponse: ObjectResponse) {
                view!!.hideLoading()
                view!!.showRepositoryResults(objectResponse)
            }
        }))
    }

}
