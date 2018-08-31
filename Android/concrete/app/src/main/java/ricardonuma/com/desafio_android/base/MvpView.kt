package ricardonuma.com.desafio_android.base

/**
 * Created by ricardonuma on 12/21/17.
 */

interface MvpView {

    enum class ProgressType {
        PROGRESS_DIALOG,
        PROGRESS_VIEW,
        PROGRESS_SWIPE
    }

    fun showError(message: String?)

    fun showLoading(type: MvpView.ProgressType)

    fun hideLoading()
}
