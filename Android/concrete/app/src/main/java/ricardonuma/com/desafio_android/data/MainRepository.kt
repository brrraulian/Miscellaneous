package ricardonuma.com.desafio_android.data

import ricardonuma.com.desafio_android.data.remote.model.pullRequest.PullRequest
import ricardonuma.com.desafio_android.data.remote.model.repository.ObjectResponse
import rx.Observable

/**
 * Created by ricardonuma on 12/21/17.
 */

interface MainRepository {

    fun loadRepositoryList(page: Int): Observable<ObjectResponse>

    fun loadPullRequestList(login: String?, name: String?): Observable<List<PullRequest>?>
}
