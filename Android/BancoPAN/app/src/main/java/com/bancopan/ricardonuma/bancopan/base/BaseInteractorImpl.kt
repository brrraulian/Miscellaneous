package com.bancopan.ricardonuma.bancopan.base

import com.bancopan.ricardonuma.bancopan.webservice.Deserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by ricardonuma on 31/10/17.
 */

open class BaseInteractorImpl {

    private var retrofit: Retrofit? = null

    fun createAPI(domain: String, apiClass: Class<*>): Any {
        val gson = GsonBuilder().registerTypeAdapter(Deserializer::class.java, Deserializer()).create()

        retrofit = Retrofit.Builder().baseUrl(domain)
                .addConverterFactory(GsonConverterFactory.create(gson)).build()

        //        retrofit.client().setConnectTimeout(60, TimeUnit.SECONDS);
        //        retrofit.client().setWriteTimeout(60, TimeUnit.SECONDS);

        return retrofit!!.create(apiClass)

    }

    fun convetJsonToObjct(json: String, clazz: Class<*>): Any? {

        if (json == null)
            return null

        val gson = Gson()
        val fromJson = gson.fromJson(json, clazz)

        return fromJson
    }

}