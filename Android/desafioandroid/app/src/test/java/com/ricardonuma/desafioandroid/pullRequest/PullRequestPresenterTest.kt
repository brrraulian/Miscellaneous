package com.ricardonuma.desafioandroid.pullRequest

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never
import org.mockito.Mockito.anyString
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import com.ricardonuma.desafioandroid.base.MvpView
import com.ricardonuma.desafioandroid.data.MainRepository
import com.ricardonuma.desafioandroid.data.remote.model.pullRequest.PullRequest
import com.ricardonuma.desafioandroid.data.remote.model.pullRequest.User
import rx.Observable
import rx.schedulers.Schedulers
import java.io.IOException
import java.util.ArrayList

/**
 * Created by ricardonuma on 12/22/17.
 */
class PullRequestPresenterTest {

    @Mock
    internal var userRepository: MainRepository? = null
    @Mock
    internal var view: PullRequestContract.View? = null

    internal var pullRequestPresenter: PullRequestPresenter? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        pullRequestPresenter = PullRequestPresenter(userRepository, Schedulers.immediate(), Schedulers.immediate())
        pullRequestPresenter!!.attachView(view)
    }

    @Test
    fun loadPullRequestList_ValidLoginAndName_ReturnsResults() {
        val pullRequestList = getDummyPullRequestList()
        Mockito.`when`(userRepository!!.loadPullRequestList(anyString(), anyString())).thenReturn(Observable.just<List<PullRequest>?>(pullRequestList))

        pullRequestPresenter!!.loadPullRequestList("", "", false)

        Mockito.verify<PullRequestContract.View>(view).showLoading(MvpView.ProgressType.PROGRESS_DIALOG)
        Mockito.verify<PullRequestContract.View>(view).hideLoading()
        Mockito.verify<PullRequestContract.View>(view).showPullRequestResults(pullRequestList)
        Mockito.verify<PullRequestContract.View>(view, Mockito.never()).showError(Mockito.anyString())
    }

    internal fun getDummyPullRequestList(): List<PullRequest>? {

        var pullRequestList = ArrayList<PullRequest>()

        pullRequestList.add(pullRequestFullDetails(1))
        pullRequestList.add(pullRequestFullDetails(2))

        return pullRequestList
    }

    internal fun pullRequestFullDetails(num: Long): PullRequest {
        return PullRequest(
                num, num, "title" + num, "body" + num, "0" + num + "/0" + num + "/2018",
                User("login" + num, "url" + num), "url" + num)
    }

    @Test
    fun loadPullRequestList_InvalidLoginAndName_ReturnsResults() {
        val errorMsg = "No internet"
        `when`(userRepository!!.loadPullRequestList(anyString(), anyString())).thenReturn(Observable.error<List<PullRequest>?>(IOException(errorMsg)))

        pullRequestPresenter!!.loadPullRequestList("", "", false)

        verify<PullRequestContract.View>(view).showLoading(MvpView.ProgressType.PROGRESS_DIALOG)
        verify<PullRequestContract.View>(view).hideLoading()
        verify<PullRequestContract.View>(view, never()).showPullRequestResults(getDummyPullRequestList())
        verify<PullRequestContract.View>(view).showError(errorMsg)
    }
}