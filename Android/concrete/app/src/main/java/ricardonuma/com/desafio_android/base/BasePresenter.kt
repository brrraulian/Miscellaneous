package ricardonuma.com.desafio_android.base

import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by ricardonuma on 12/21/17.
 */

open class BasePresenter<T : MvpView> : MvpPresenter<T> {

    var view: T? = null
        private set

    private val compositeSubscription = CompositeSubscription()

    override fun attachView(mvpView: T?) {
        view = mvpView
    }

    override fun detachView() {
        compositeSubscription.clear()
        view = null
    }

    fun checkViewAttached() {
        if (!isViewAttached) {
            throw MvpViewNotAttachedException()
        }
    }

    private val isViewAttached: Boolean
        get() = view != null

    protected fun addSubscription(subscription: Subscription) {
        this.compositeSubscription.add(subscription)
    }

    class MvpViewNotAttachedException : RuntimeException("Please call Presenter.attachView(MvpView) before" + " requesting data to the Presenter")
}