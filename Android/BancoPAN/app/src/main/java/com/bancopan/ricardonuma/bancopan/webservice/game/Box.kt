package com.bancopan.ricardonuma.bancopan.webservice.game

import com.orm.SugarRecord
import java.io.Serializable

/**
 * Created by ricardonuma on 31/10/17.
 */

class Box : Serializable, SugarRecord() {

    var large: String? = null
    var medium: String? = null
    var small: String? = null
    var template: String? = null

    companion object {

        fun getBoxById(id: Long): Box? {
            val box = SugarRecord.findWithQuery<Box>(Box::class.java,
                    "SELECT * FROM BOX WHERE id = ?",
                    id.toString()).first()

            return box
        }
    }
}