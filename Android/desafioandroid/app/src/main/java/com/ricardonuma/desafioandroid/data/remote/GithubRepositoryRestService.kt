package com.ricardonuma.desafioandroid.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.ricardonuma.desafioandroid.data.remote.model.pullRequest.PullRequest
import com.ricardonuma.desafioandroid.data.remote.model.repository.ObjectResponse
import rx.Observable

/**
 * Created by ricardonuma on 30/12/17.
 */

interface GithubRepositoryRestService {

    @GET("/search/repositories")
    fun loadRepositoryList(@Query("q") language: String,
                          @Query("sort") sort: String,
                          @Query("page") page: Int): Observable<ObjectResponse>

    @GET("/repos/{criador}/{repositorio}/pulls")
    fun loadPullRequestList(@Path("criador") login: String?,
                @Path("repositorio") name: String?): Observable<List<PullRequest>?>
}
