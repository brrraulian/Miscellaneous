package ricardonuma.com.desafio_android

import android.app.Application
import com.orm.SugarContext

/**
 * Created by ricardonuma on 30/12/17.
 */

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        SugarContext.init(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        SugarContext.terminate()
    }

}