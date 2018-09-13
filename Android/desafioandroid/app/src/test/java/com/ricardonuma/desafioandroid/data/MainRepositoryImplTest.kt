package com.ricardonuma.desafioandroid.data

import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.Mockito.anyInt
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import retrofit2.Response
import retrofit2.adapter.rxjava.HttpException
import com.ricardonuma.desafioandroid.Constants
import com.ricardonuma.desafioandroid.data.remote.GithubRepositoryRestService
import com.ricardonuma.desafioandroid.data.remote.model.pullRequest.PullRequest
import com.ricardonuma.desafioandroid.data.remote.model.pullRequest.User
import com.ricardonuma.desafioandroid.data.remote.model.repository.ObjectResponse
import com.ricardonuma.desafioandroid.data.remote.model.repository.Owner
import com.ricardonuma.desafioandroid.data.remote.model.repository.Repository
import rx.Observable
import rx.observers.TestSubscriber
import java.util.ArrayList

/**
 * Created by ricardonuma on 31/12/17.
 */

class MainRepositoryImplTest {

    @Mock
    internal var githubRepositoryRestService: GithubRepositoryRestService? = null

    private var userRepository: MainRepository? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        userRepository = MainRepositoryImpl(githubRepositoryRestService)
    }

    @Test
    fun loadRepositoryList_200OkResponse_InvokesCorrectApiCalls() {
        //Given
        `when`(githubRepositoryRestService!!.loadRepositoryList(anyString(), anyString(), anyInt()))
                .thenReturn(Observable.just<ObjectResponse>(githubRepositoryList()))

        //When
        val repositorySubscriber = TestSubscriber<ObjectResponse>()
        userRepository!!.loadRepositoryList(REPOSITORY_PAGE).subscribe(repositorySubscriber)

        //Then
        repositorySubscriber.awaitTerminalEvent()
        repositorySubscriber.assertNoErrors()

        val repositoryOnNextEvents = repositorySubscriber.onNextEvents
        val repositories = repositoryOnNextEvents[0]
        Assert.assertEquals(githubRepositoryList()!!.items[0]!!.id_repository, repositories!!.items[0]!!.id_repository)
        Assert.assertEquals(githubRepositoryList()!!.items[1]!!.id_repository, repositories!!.items[1]!!.id_repository)

        verify<GithubRepositoryRestService>(githubRepositoryRestService)
                .loadRepositoryList(Constants.LANGUAGE, Constants.SORT, REPOSITORY_PAGE)
    }

    internal fun githubRepositoryList(): ObjectResponse? {

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
    fun loadPullRequestList_200OkResponse_InvokesCorrectApiCalls() {
        //Given
        `when`(githubRepositoryRestService!!.loadPullRequestList(anyString(), anyString()))
                .thenReturn(Observable.just<List<PullRequest>?>(githubPullRequestList()))

        //When
        val pullRequestSubscriber = TestSubscriber<List<PullRequest>?>()
        userRepository!!.loadPullRequestList(PULL_REQUEST_LOGIN, PULL_REQUEST_NAME).subscribe(pullRequestSubscriber)

        //Then
        pullRequestSubscriber.awaitTerminalEvent()
        pullRequestSubscriber.assertNoErrors()

        val pullRequestOnNextEvents = pullRequestSubscriber.onNextEvents
        val pullRequests = pullRequestOnNextEvents[0]
        Assert.assertEquals(githubPullRequestList()!![0].id_pull_request, pullRequests!![0].id_pull_request)
        Assert.assertEquals(githubPullRequestList()!![1].id_pull_request, pullRequests!![1].id_pull_request)

        verify<GithubRepositoryRestService>(githubRepositoryRestService)
                .loadPullRequestList(PULL_REQUEST_LOGIN, PULL_REQUEST_NAME)
    }

    internal fun githubPullRequestList(): List<PullRequest>? {

        var pullRequestList = ArrayList<PullRequest>()

        pullRequestList.add(pullRequestFullDetails(1))
        pullRequestList.add(pullRequestFullDetails(2))

        return pullRequestList
    }

    internal fun pullRequestFullDetails(num: Long): PullRequest {
        return PullRequest(
               num,  num, "title" + num, "body" + num, "0" + num + "/0" + num + "/2018",
                User("login" + num, "url" + num), "url" + num)
    }

    @Test
    fun loadRepositoryList_OtherHttpError_LoadTerminatedWithError() {
        //Given
        `when`(githubRepositoryRestService!!.loadRepositoryList(anyString(), anyString(), anyInt())).thenReturn(getRepository403ForbiddenError())

        //When
        val repositorySubscriber = TestSubscriber<ObjectResponse?>()
        userRepository!!.loadRepositoryList(REPOSITORY_PAGE).subscribe(repositorySubscriber)

        //Then
        repositorySubscriber.awaitTerminalEvent()
        repositorySubscriber.assertError(HttpException::class.java)

        verify<GithubRepositoryRestService>(githubRepositoryRestService)
                .loadRepositoryList(Constants.LANGUAGE, Constants.SORT, REPOSITORY_PAGE)
        verify<GithubRepositoryRestService>(githubRepositoryRestService)
                .loadRepositoryList(Constants.LANGUAGE, Constants.SORT, REPOSITORY_PAGE)
    }

    private fun getRepository403ForbiddenError(): Observable<ObjectResponse> {
        return Observable.error<ObjectResponse>(HttpException(
                Response.error<Any>(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))))
    }

    @Test
    fun loadPullRequestList_OtherHttpError_LoadTerminatedWithError() {
        //Given
        `when`(githubRepositoryRestService!!.loadPullRequestList(anyString(), anyString())).thenReturn(getPullRequest403ForbiddenError())

        //When
        val pullRequestSubscriber = TestSubscriber<List<PullRequest>?>()
        userRepository!!.loadPullRequestList(PULL_REQUEST_LOGIN, PULL_REQUEST_NAME).subscribe(pullRequestSubscriber)

        //Then
        pullRequestSubscriber.awaitTerminalEvent()
        pullRequestSubscriber.assertError(HttpException::class.java)

        verify<GithubRepositoryRestService>(githubRepositoryRestService)
                .loadPullRequestList(PULL_REQUEST_LOGIN, PULL_REQUEST_NAME)
        verify<GithubRepositoryRestService>(githubRepositoryRestService)
                .loadPullRequestList(PULL_REQUEST_LOGIN, PULL_REQUEST_NAME)
    }

    private fun getPullRequest403ForbiddenError(): Observable<List<PullRequest>?> {
        return Observable.error<List<PullRequest>?>(HttpException(
                Response.error<Any>(403, ResponseBody.create(MediaType.parse("application/json"), "Forbidden"))))
    }

    companion object {

        private val REPOSITORY_PAGE = 1
        private val PULL_REQUEST_LOGIN = "login"
        private val PULL_REQUEST_NAME = "name"
    }

}