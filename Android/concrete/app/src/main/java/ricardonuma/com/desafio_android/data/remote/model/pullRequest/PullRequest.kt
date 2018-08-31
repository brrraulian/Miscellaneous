package ricardonuma.com.desafio_android.data.remote.model.pullRequest

import com.orm.SugarRecord
import ricardonuma.com.desafio_android.data.remote.model.pullRequest.User.Companion.getUserbyId
import java.io.Serializable

/**
 * Created by ricardonuma on 12/18/17.
 */

class PullRequest(

        id_repository: Long?,
        id_pull_request: Long?,
        title: String?,
        body: String?,
        created_at: String?,
        user: User?,
        html_url: String?

) : Serializable, SugarRecord() {

    constructor() : this(null, null, null, null, null, null, null)

    var id_repository: Long? = null
    var id_pull_request: Long? = null
    var title: String? = null
    var body: String? = null
    var created_at: String? = null
    var user: User? = null
    var html_url: String? = null

    companion object {

        fun getListPullRequest(id_repository: Long?): List<PullRequest> {

            val listPullRequest = SugarRecord.findWithQuery<PullRequest>(PullRequest::class.java,
                    "SELECT * FROM PULL_REQUEST WHERE idrepository = ? ORDER BY createdat DESC",
                    id_repository.toString())

            for (pullRequest in listPullRequest) {
                pullRequest.user = getUserbyId(pullRequest.id)
            }

            return listPullRequest
        }

    }
}

