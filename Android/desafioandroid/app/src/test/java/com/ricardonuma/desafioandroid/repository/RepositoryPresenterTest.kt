package com.ricardonuma.desafioandroid.repository

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.anyString
import org.mockito.Mockito.anyInt
import org.mockito.MockitoAnnotations
import com.ricardonuma.desafioandroid.base.MvpView
import com.ricardonuma.desafioandroid.data.MainRepository
import com.ricardonuma.desafioandroid.data.remote.model.repository.ObjectResponse
import com.ricardonuma.desafioandroid.data.remote.model.repository.Owner
import com.ricardonuma.desafioandroid.data.remote.model.repository.Repository
import rx.Observable
import rx.schedulers.Schedulers
import java.io.IOException
import java.util.ArrayList

/**
 * Created by ricardonuma on 12/22/17.
 */
class RepositoryPresenterTest {

    @Mock
    internal var userRepository: MainRepository? = null
    @Mock
    internal var view: RepositoryContract.View? = null

    internal var repositoryPresenter: RepositoryPresenter? = null

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repositoryPresenter = RepositoryPresenter(userRepository, Schedulers.immediate(), Schedulers.immediate())
        repositoryPresenter!!.attachView(view)
    }

    @Test
    fun loadRepositoryList_ValidPage_ReturnsResults() {
        val repositoryList = getDummyObjectResponse()
        `when`(userRepository!!.loadRepositoryList(anyInt())).thenReturn(Observable.just<ObjectResponse>(repositoryList))

        repositoryPresenter!!.loadRepositoryList((Math.random() * 100).toInt(), false)

        verify<RepositoryContract.View>(view).showLoading(MvpView.ProgressType.PROGRESS_DIALOG)
        verify<RepositoryContract.View>(view).hideLoading()
        verify<RepositoryContract.View>(view).showRepositoryResults(repositoryList)
        verify<RepositoryContract.View>(view, never()).showError(anyString())
    }

    internal fun getDummyObjectResponse(): ObjectResponse? {

        var repositoryList = ArrayList<Repository?>()

        repositoryList.add(repositoryFullDetails(1))
        repositoryList.add(repositoryFullDetails(2))

        var githubObjectResponse: ObjectResponse? = ObjectResponse(2, false, repositoryList)

        return githubObjectResponse
    }

    internal fun repositoryFullDetails(num: Long): Repository {
        return Repository(
                num, "name" + num, "description" + num, num, num,
                Owner("login" + num, "url" + num))
    }

    @Test
    fun loadRepositoryList_InvalidPage_ReturnsResults() {
        val errorMsg = "No internet"
        `when`(userRepository!!.loadRepositoryList(anyInt())).thenReturn(Observable.error<ObjectResponse>(IOException(errorMsg)))

        repositoryPresenter!!.loadRepositoryList((Math.random() * 100).toInt(), false)

        verify<RepositoryContract.View>(view).showLoading(MvpView.ProgressType.PROGRESS_DIALOG)
        verify<RepositoryContract.View>(view).hideLoading()
        verify<RepositoryContract.View>(view, never()).showRepositoryResults(getDummyObjectResponse())
        verify<RepositoryContract.View>(view).showError(errorMsg)
    }
}