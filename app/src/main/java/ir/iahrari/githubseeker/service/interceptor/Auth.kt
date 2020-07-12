package ir.iahrari.githubseeker.service.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class Auth(private val token: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response =
        chain.proceed(
            chain.request()
                .newBuilder()
                .addHeader("Authorization", token)
                .build()
        )
}