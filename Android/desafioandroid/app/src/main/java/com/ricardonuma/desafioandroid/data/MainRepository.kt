package com.ricardonuma.desafioandroid.data

import com.ricardonuma.desafioandroid.data.remote.model.pullRequest.PullRequest
import com.ricardonuma.desafioandroid.data.remote.model.repository.ObjectResponse
import rx.Observable

/**
 * Created by ricardonuma on 30/12/17.
 */

interface MainRepository {

    fun loadRepositoryList(page: Int): Observable<ObjectResponse>

    fun loadPullRequestList(login: String?, name: String?): Observable<List<PullRequest>?>
}
