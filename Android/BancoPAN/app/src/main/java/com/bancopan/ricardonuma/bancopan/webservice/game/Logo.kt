package com.bancopan.ricardonuma.bancopan.webservice.game

import com.orm.SugarRecord
import java.io.Serializable

/**
 * Created by ricardonuma on 31/10/17.
 */

class Logo : Serializable, SugarRecord() {

    var large: String? = null
    var medium: String? = null
    var small: String? = null
    var template: String? = null

    companion object {

        fun getLogoById(id: Long): Logo? {
            val logo = SugarRecord.findWithQuery<Logo>(Logo::class.java,
                    "SELECT * FROM LOGO WHERE id = ?",
                    id.toString()).first()

            return logo
        }
    }
}