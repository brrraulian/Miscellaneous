package ricardonuma.com.desafio_android.data.remote.model.repository

import com.orm.SugarRecord
import java.io.Serializable

/**
 * Created by ricardonuma on 12/18/17.
 */

class Owner(

        login: String?,
        avatar_url: String?

) : Serializable, SugarRecord() {

    constructor() : this(null, null)

    var login: String? = null
    var avatar_url: String? = null

    companion object {

        fun getOwnerbyId(id: Long?): Owner? {
            val owner = SugarRecord.findWithQuery<Owner>(Owner::class.java,
                    "SELECT * FROM OWNER WHERE id = ?",
                    id.toString()).first()

            return owner
        }
    }
}