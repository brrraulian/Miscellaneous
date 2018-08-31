package ricardonuma.com.desafio_android.pullRequest

import ricardonuma.com.desafio_android.base.BasePresenter
import ricardonuma.com.desafio_android.base.MvpView
import ricardonuma.com.desafio_android.data.MainRepository
import ricardonuma.com.desafio_android.data.remote.model.pullRequest.PullRequest
import rx.Scheduler
import rx.Subscriber

/**
 * Created by ricardonuma on 12/21/17.
 */

class PullRequestPresenter(private val userRepository: MainRepository?, private val ioScheduler: Scheduler, private val mainScheduler: Scheduler) : BasePresenter<PullRequestContract.View>(), PullRequestContract.Presenter {

    override fun loadPullRequestList(login: String?, name: String?, updateFromSwipe: Boolean) {
        checkViewAttached()

        if (updateFromSwipe)
            view!!.showLoading(MvpView.ProgressType.PROGRESS_SWIPE)
        else
            view!!.showLoading(MvpView.ProgressType.PROGRESS_DIALOG)

        addSubscription(userRepository!!.loadPullRequestList(login, name).subscribeOn(ioScheduler).observeOn(mainScheduler).subscribe(object : Subscriber<List<PullRequest>?>() {
            override fun onCompleted() {

            }

            override fun onError(e: Throwable) {
                view!!.hideLoading()
                view!!.showError(e.message) //TODO You probably don't want this error to show to users - Might want to show a friendlier message :)
            }

            override fun onNext(pullRequest: List<PullRequest>?) {
                view!!.hideLoading()
                view!!.showPullRequestResults(pullRequest)
            }
        }))
    }
}