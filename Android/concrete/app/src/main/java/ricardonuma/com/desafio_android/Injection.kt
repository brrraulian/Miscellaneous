package ricardonuma.com.desafio_android

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ricardonuma.com.desafio_android.data.MainRepository
import ricardonuma.com.desafio_android.data.MainRepositoryImpl
import ricardonuma.com.desafio_android.data.remote.GithubRepositoryRestService

/**
 * Created by ricardonuma on 30/12/17.
 */


object Injection {

    private var okHttpClient: OkHttpClient? = null
    private var userRestService: GithubRepositoryRestService? = null
    private var retrofitInstance: Retrofit? = null


    fun provideUserRepo(): MainRepository {
        return MainRepositoryImpl(provideGithubRepositoryRestService())
    }

    internal fun provideGithubRepositoryRestService(): GithubRepositoryRestService? {
        if (userRestService == null) {
            userRestService = getRetrofitInstance()!!.create<GithubRepositoryRestService>(GithubRepositoryRestService::class.java!!)
        }
        return userRestService
    }

    internal fun getOkHttpClient(): OkHttpClient? {
        if (okHttpClient == null) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC
            okHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()
        }

        return okHttpClient
    }

    internal fun getRetrofitInstance(): Retrofit? {
        if (retrofitInstance == null) {
            val retrofit = Retrofit.Builder().client(Injection.getOkHttpClient()).baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            retrofitInstance = retrofit.build()

        }
        return retrofitInstance
    }
}