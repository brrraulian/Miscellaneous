package com.ricardonuma.desafioandroid.data

import com.ricardonuma.desafioandroid.data.remote.GithubRepositoryRestService
import com.ricardonuma.desafioandroid.Constants
import com.ricardonuma.desafioandroid.data.remote.model.pullRequest.PullRequest
import com.ricardonuma.desafioandroid.data.remote.model.repository.ObjectResponse
import rx.Observable

/**
 * Created by ricardonuma on 30/12/17.
 */


class MainRepositoryImpl(private val githubUserRestService: GithubRepositoryRestService?) : MainRepository {

    override fun loadRepositoryList(page: Int): Observable<ObjectResponse> {
        return Observable.defer<ObjectResponse> {
            githubUserRestService!!.loadRepositoryList(Constants.LANGUAGE, Constants.SORT, page)
        }
    }

    override fun loadPullRequestList(login: String?, name: String?): Observable<List<PullRequest>?> {
        return Observable.defer<List<PullRequest>> {
            githubUserRestService!!.loadPullRequestList(login, name)
        }
    }
}