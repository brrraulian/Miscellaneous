package ricardonuma.com.desafio_android.pullRequest

import ricardonuma.com.desafio_android.base.MvpPresenter
import ricardonuma.com.desafio_android.base.MvpView
import ricardonuma.com.desafio_android.data.remote.model.pullRequest.PullRequest

/**
 * Created by ricardonuma on 12/21/17.
 */

interface PullRequestContract {

    interface View : MvpView {

        fun showPullRequestResults(pullRequest: List<PullRequest>?)
    }

    interface Presenter : MvpPresenter<View> {

        fun loadPullRequestList(login: String?, name: String?, updateFromSwipe: Boolean)
    }
}