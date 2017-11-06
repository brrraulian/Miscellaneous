package com.bancopan.ricardonuma.bancopan.game

import com.bancopan.ricardonuma.bancopan.base.BaseView
import com.bancopan.ricardonuma.bancopan.webservice.game.ObjectResponse

/**
 * Created by ricardonuma on 31/10/17.
 */

interface GameView : BaseView {

    fun onLoadGameListSuccess(objectResponse: ObjectResponse?)

    fun onLoadGameListError(msg: String?)
}