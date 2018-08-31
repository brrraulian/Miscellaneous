package ricardonuma.com.desafio_android.data

import ricardonuma.com.desafio_android.data.remote.GithubRepositoryRestService
import ricardonuma.com.desafio_android.Constants
import ricardonuma.com.desafio_android.data.remote.model.pullRequest.PullRequest
import ricardonuma.com.desafio_android.data.remote.model.repository.ObjectResponse
import rx.Observable

/**
 * Created by ricardonuma on 12/21/17.
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