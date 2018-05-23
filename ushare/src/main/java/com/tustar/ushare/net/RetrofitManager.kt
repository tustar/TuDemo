package com.tustar.ushare.net

import com.tustar.ushare.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

class RetrofitManager {

    companion object {

        private const val API_URL = BuildConfig.API_HOST
        private const val READ_TIMEOUT = 5L
        private const val WRITE_TIMEOUT = 5L
        private const val CONNECT_TIMEOUT = 5L
        val service: ApiService
            get() {

                val client = getClient()

                val retrofit = Retrofit.Builder()
                        .baseUrl(API_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(client)
                        .build()

                // Create an instance of our BlockChain API interface.
                return retrofit.create(ApiService::class.java)
            }

        private fun getClient(): OkHttpClient? {
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(null as KeyStore?)
            val trustManagers = trustManagerFactory.trustManagers
            if (trustManagers.size != 1 || trustManagers[0] !is X509TrustManager) {
                throw IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers))
            }
            val trustManager = trustManagers[0] as X509TrustManager
            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, arrayOf<TrustManager>(trustManager), null)
            val sslSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient().newBuilder()
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .sslSocketFactory(sslSocketFactory, trustManager)
                    .hostnameVerifier(HostnameVerifier { _, _ ->
                        return@HostnameVerifier true
                    })

            if (BuildConfig.DEBUG) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(addHeaderInterceptor())
            
            return builder.build()
        }

        private fun addHeaderInterceptor(): Interceptor {
            return Interceptor { chain ->
                val originalRequest = chain.request()
                val requestBuilder = originalRequest.newBuilder()
                        // Provide your custom header here
//                        .header(CommonDefine.HEAD_ACCESS_TOKEN, )
                        .method(originalRequest.method(), originalRequest.body())
                val request = requestBuilder.build()
                chain.proceed(request)
            }
        }
    }


    class TrustAnyTrustManager : X509TrustManager {

        override fun getAcceptedIssuers(): Array<X509Certificate> {
            return return arrayOf()
        }

        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }
    }
}