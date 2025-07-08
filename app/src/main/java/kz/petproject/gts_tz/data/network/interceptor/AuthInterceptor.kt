package kz.petproject.gts_tz.data.network.interceptor

import kz.petproject.gts_tz.data.local.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val sessionManager: SessionManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sessionManager.getToken()
        val originalRequest = chain.request()

        val requestWithHeader = if (token != null) {
            originalRequest.newBuilder()
                .header("x-access-token", token)
                .build()
        } else {
            originalRequest
        }

        return chain.proceed(requestWithHeader)
    }
}