package com.bancopan.ricardonuma.bancopan.webservice.game

import com.orm.SugarRecord
import java.io.Serializable
import com.bancopan.ricardonuma.bancopan.webservice.game.Box.Companion.getBoxById
import com.bancopan.ricardonuma.bancopan.webservice.game.Logo.Companion.getLogoById

/**
 * Created by ricardonuma on 31/10/17.
 */

class Game : Serializable, SugarRecord() {

    var name: String? = null
    var popularity: Long? = null
    var _id: Long? = null
    var giantbomb_id: Long? = null
    var box: Box? = null
    var logo: Logo? = null
    var localized_name: String? = null
    var locale: String? = null

    companion object {

        fun getGamebyId(id: Long): Game? {
            val game = SugarRecord.findWithQuery<Game>(Game::class.java,
                    "SELECT * FROM GAME WHERE id = ?",
                    id.toString()).first()

            game.box = getBoxById(id)
            game.logo = getLogoById(id)

            return game
        }
    }
}