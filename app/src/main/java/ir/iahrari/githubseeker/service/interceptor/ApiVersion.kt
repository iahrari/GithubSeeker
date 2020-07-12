package ir.iahrari.githubseeker.service.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class ApiVersion: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val r = chain.request().newBuilder()
            .addHeader("Accept", "application/vnd.github.v3+json")
            .build()

        return chain.proceed(r)
    }
}