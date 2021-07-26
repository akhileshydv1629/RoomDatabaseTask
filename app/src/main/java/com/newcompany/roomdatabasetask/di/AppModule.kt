package com.newcompany.roomdatabasetask.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.newcompany.roomdatabasetask.api.UserApi
import com.newcompany.roomdatabasetask.db.AppDatabase
import com.newcompany.roomdatabasetask.db.UserDAO
import com.newcompany.roomdatabasetask.repository.UserRepository
import com.newcompany.roomdatabasetask.utils.PreferenceHelper
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import javax.inject.Singleton


@Module
class AppModule(val application: Application) {


    @Singleton
    @Provides
    fun getUserDao():UserDAO
    {
        return getDatabaseInstance().getUserDAO()
    }
    @Singleton
    @Provides
    fun getUserRepository(): UserRepository
    {
        return UserRepository()
    }
    @Singleton
    @Provides
    fun getDatabaseInstance():AppDatabase
    {
       return AppDatabase.getDbInstance(provideAppContext())
    }

    @Singleton
    @Provides
    fun provideAppContext():Context
    {
        return application.applicationContext
    }


    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://newsapi.org/")
                .client(okHttpClient)
                .build()
    }

    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): UserApi =
            retrofit.create(UserApi::class.java)

    @Singleton
    @Provides
    fun provideOkHttpClient(
            interceptors: ArrayList<Interceptor>
    ): OkHttpClient {
        val clientBuilder = OkHttpClient.Builder()
                .followRedirects(false)
        interceptors.forEach {
            clientBuilder.addInterceptor(it)
        }
        return clientBuilder.build()
    }


    @Singleton
    @Provides
    fun provideInterceptors(token: String?): ArrayList<Interceptor> {
        val interceptors = arrayListOf<Interceptor>()
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            HttpLoggingInterceptor.Level.BODY
        }
        interceptors.add(loggingInterceptor)
        if (!token.isNullOrEmpty()){
            val authInterceptor = object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()
                    val request = original.newBuilder()
                            .header("token", "$token")
                            .build()
                    return chain.proceed(request)
                }
            }
            interceptors.add(authInterceptor)
        }
        return interceptors
    }

    @Singleton
    @Provides
    fun provideSharePrefs(context: Context): SharedPreferences = PreferenceHelper.appPrefs(context)

    @Singleton
    @Provides
    fun provideToken(prefs: SharedPreferences): String? = prefs.getString(PreferenceHelper.SharedPrefKeys.USER_TOKEN.toString(),"")

}