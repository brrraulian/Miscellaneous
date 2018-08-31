package ricardonuma.com.desafio_android.data.remote.model.pullRequest

import com.orm.SugarRecord
import java.io.Serializable

/**
 * Created by ricardonuma on 12/18/17.
 */

class User(

        login: String?,
        avatar_url: String?

) : Serializable, SugarRecord() {

    constructor() : this(null, null)

    var login: String? = null
    var avatar_url: String? = null

    companion object {

        fun getUserbyId(id: Long): User? {
            val owner = SugarRecord.findWithQuery<User>(User::class.java,
                    "SELECT * FROM USER WHERE id = ?",
                    id.toString()).first()

            return owner
        }
    }
}