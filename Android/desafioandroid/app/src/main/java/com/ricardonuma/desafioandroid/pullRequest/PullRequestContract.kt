package com.ricardonuma.desafioandroid.pullRequest

import com.ricardonuma.desafioandroid.base.MvpPresenter
import com.ricardonuma.desafioandroid.base.MvpView
import com.ricardonuma.desafioandroid.data.remote.model.pullRequest.PullRequest

/**
 * Created by ricardonuma on 30/12/17.
 */

interface PullRequestContract {

    interface View : MvpView {

        fun showPullRequestResults(pullRequest: List<PullRequest>?)
    }

    interface Presenter : MvpPresenter<View> {

        fun loadPullRequestList(login: String?, name: String?, updateFromSwipe: Boolean)
    }
}