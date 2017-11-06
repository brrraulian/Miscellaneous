package com.bancopan.ricardonuma.bancopan.game

import android.app.Activity
import android.content.Context
import com.bancopan.ricardonuma.bancopan.base.BaseView
import com.bancopan.ricardonuma.bancopan.util.Utilities
import com.bancopan.ricardonuma.bancopan.webservice.game.ObjectResponse
import kotlin.properties.Delegates

/**
 * Created by ricardonuma on 31/10/17.
 */

class GamePresenterImpl : GamePresenter, OnGameFinishedListener {

    var context: Context by Delegates.notNull()
    var viewGame: GameView by Delegates.notNull()
    var interactor: GameInteractor by Delegates.notNull()


    constructor(context: Context, view: GameView) {
        this.viewGame = view
        this.context = context
        this.interactor = GameInteractorImpl()
    }

    override fun loadGameList(limit: Int, offset: Int, updateFromSwipe: Boolean) {

        if (!Utilities.isOnline(context)) {
            viewGame.onConnectionFailed()
            return
        }

        if (updateFromSwipe)
            viewGame.showProgress(BaseView.ProgressType.PROGRESS_SWIPE)
        else
            viewGame.showProgress(BaseView.ProgressType.PROGRESS_DIALOG)

        interactor.loadGameList(context, limit, offset, this)
    }

    override fun onLoadGameListSuccess(objectResponse: ObjectResponse?) {
        (context as Activity).runOnUiThread {
            viewGame.hideProgress()
            viewGame.onLoadGameListSuccess(objectResponse)
        }
    }

    override fun onLoadGameListError(errorMessage: String?) {
        (context as Activity).runOnUiThread {
            viewGame.hideProgress()
            viewGame.onLoadGameListError(errorMessage)
        }
    }

}

open interface GamePresenter {

    fun loadGameList(limit: Int, offset: Int, updateFromSwipe: Boolean)

}