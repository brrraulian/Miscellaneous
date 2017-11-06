package com.bancopan.ricardonuma.bancopan.webservice.game

import com.orm.SugarRecord
import java.io.Serializable
import com.bancopan.ricardonuma.bancopan.webservice.game.Game.Companion.getGamebyId

/**
 * Created by ricardonuma on 31/10/17.
 */

class Top : Serializable, SugarRecord() {

    var game: Game? = null
    var channels: Long? = null
    var viewers: Long? = null

    companion object {

        fun getListTop(limit: Int, offset: Int): List<Top> {

            val listTop = SugarRecord.find<Top>(Top::class.java,
                    null, arrayOf(), null, "viewers DESC", if (offset == 0) limit.toString() else offset.toString())

            for (top in listTop) {
                top.game = getGamebyId(top.id)
            }

            return listTop
        }
    }
}