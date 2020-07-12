package ir.iahrari.githubseeker.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import ir.iahrari.githubseeker.service.RetrofitInterface
import ir.iahrari.githubseeker.service.interceptor.Auth
import ir.iahrari.githubseeker.service.util.getToken
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@InstallIn(ActivityRetainedComponent::class)
@Module
object DIModule {

    @Provides
    @ActivityRetainedScoped
    fun provideClient(@ApplicationContext context: Context): RetrofitInterface {
        val retrofit = Retrofit.Builder()
        .baseUrl(RetrofitInterface.baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            if (context.getToken() == "token ")
                OkHttpClient.Builder().build()
            else
                OkHttpClient()
                    .newBuilder()
                    .addInterceptor(Auth(context.getToken()))
                    .build()
        ).build()



        return retrofit.create(RetrofitInterface::class.java)
    }
}

//@Module
//@InstallIn(ApplicationComponent::class)
//class ApplicationContextModule() {
//    @Provides
//    @ApplicationContext
//    fun provideContext(): Context {
//        return applicationContext
//    }

//    @Provides
//    fun provideApplication(): Application {
//        return applicationContext.applicationContext as Application
//    }

//}