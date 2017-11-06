package com.bancopan.ricardonuma.bancopan.webservice.game

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * Created by ricardonuma on 31/10/17.
 */
interface GameAPI {

    @Headers("Accept: application/vnd.twitchtv.v5+json", "Client-ID: em6v58vppuoctpgq21ji8osbd78ss4")
    @GET("/kraken/games/top")
    fun loadGameList(@Query("limit") limit: Int,
                     @Query("offset") offset: Int): Call<ObjectResponse>

}