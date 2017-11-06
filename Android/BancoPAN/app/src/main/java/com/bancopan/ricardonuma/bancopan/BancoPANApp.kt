package com.bancopan.ricardonuma.bancopan

import android.app.Application
import com.orm.SugarContext

/**
 * Created by ricardonuma on 05/11/17.
 */


class BancoPANApp : Application() {

    override fun onCreate() {
        super.onCreate()
        SugarContext.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        SugarContext.terminate()
    }

}