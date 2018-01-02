package com.bancopan.ricardonuma.bancopan.game

import android.content.Context
import com.bancopan.ricardonuma.bancopan.base.BaseInteractorImpl
import com.bancopan.ricardonuma.bancopan.webservice.Constants
import com.bancopan.ricardonuma.bancopan.webservice.game.GameAPI
import com.bancopan.ricardonuma.bancopan.webservice.game.ObjectResponse
import com.bancopan.ricardonuma.bancopan.webservice.status.ServerStatus
import com.bancopan.ricardonuma.bancopan.webservice.status.ServerStatusEnum
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bancopan.ricardonuma.bancopan.webservice.Error

/**
 * Created by ricardonuma on 31/10/17.
 */

class GameInteractorImpl : BaseInteractorImpl(), GameInteractor {

    override fun loadGameList(context: Context, limit: Int, offset: Int, listener: OnGameFinishedListener) {

        val api = createAPI(Constants.DOMAIN_URL, GameAPI::class.java) as GameAPI
        val call = api.loadGameList(limit, offset)

        call.enqueue(object : Callback<ObjectResponse> {
            override fun onResponse(call: Call<ObjectResponse>, response: Response<ObjectResponse>) {
                if (response.code() == ServerStatusEnum.STATUS_OK.code ||
                        response.code() == ServerStatusEnum.STATUS_CREATED.code) {

                    ServerStatus.isOnline = true

                    var objectResponse = response.body()

                    listener.onLoadGameListSuccess(objectResponse)

                    return
                }

                if (response.code() == ServerStatusEnum.STATUS_FORBIDDEN.code || response.code() == ServerStatusEnum.STATUS_NOT_FOUND.code) {
                    ServerStatus.isOnline = false
                }


                var error = convetJsonToObjct(response.errorBody().string(), Error::class.java) as Error

                var errorMessage: String? = ""
                if (error != null && error.errors != null && error.errors!![0] != null)
                    errorMessage = error.errors!![0].message
                else
                    errorMessage = response.message()


                listener.onLoadGameListError(errorMessage)
            }

            override fun onFailure(call: Call<ObjectResponse>, t: Throwable) {
                if (t.message != null) {
                    listener.onLoadGameListError(t.message.toString())
                } else {
                    listener.onLoadGameListError("")
                }

            }
        })
    }

    companion object {

        val TAG = "GameInteractorImpl"
    }

}

interface GameInteractor {

    fun loadGameList(context: Context, limit: Int, offset: Int, listener: OnGameFinishedListener)

}