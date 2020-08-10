package ir.iahrari.githubseeker.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import ir.iahrari.githubseeker.service.RetrofitInterface
import ir.iahrari.githubseeker.service.TrendingServiceInterface
import ir.iahrari.githubseeker.service.interceptor.ApiVersion
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
        var ok = OkHttpClient().newBuilder()
            .addInterceptor(ApiVersion())

        if (context.getToken() != "token ")
            ok = ok.addInterceptor(Auth(context.getToken()))

        val retrofit = Retrofit.Builder()
        .baseUrl(RetrofitInterface.baseURL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(ok.build()).build()

        return retrofit.create(RetrofitInterface::class.java)
    }

    @Provides
    @ActivityRetainedScoped
    fun provideTrendingClient(): TrendingServiceInterface{
        return Retrofit.Builder()
            .baseUrl(TrendingServiceInterface.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(TrendingServiceInterface::class.java)
    }
}
