package ricardonuma.com.desafio_android.base

/**
 * Created by ricardonuma on 12/21/17.
 */

interface MvpPresenter<V : MvpView> {

    fun attachView(mvpView: V?)

    fun detachView()
}