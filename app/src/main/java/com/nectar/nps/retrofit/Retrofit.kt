package com.nectarinfotel.retrofit

import com.nectar.nps.utils.OAuthInterceptor
import com.nectarinfotel.utils.AppConstants
import com.nectarinfotel.utils.NectarApplication
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

class Retrofit {
    companion object {
        @JvmStatic
        val TAG = com.nectarinfotel.retrofit.Retrofit::class.java.simpleName
        private val DEFAULT_TIMEOUT_SEC = 2000
        var client: OkHttpClient? = null

        fun getClient(): Retrofit? {
            val clientBuilder = getOkHttpBuilder()
            val client = clientBuilder.build()
            return AppConstants.BASE_URL?.let { buildRetrofitInstance(it, client) }
        }

        private fun buildRetrofitInstance(baseUrl: String, client: OkHttpClient): Retrofit {


          /*  val client1 = OkHttpClient.Builder()
                .addInterceptor(OAuthInterceptor("Bearer", NectarApplication.token))
                .build()*/
            return Retrofit.Builder()
                .baseUrl(baseUrl)
               .client(getOkHttpBuilder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

      //  .client(getUnsafeOkHttpClient().build())
        private fun getOkHttpBuilder(): OkHttpClient.Builder {
            return OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)

        }

        fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
            try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }

                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }
                })

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.getSocketFactory()

                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier(object : HostnameVerifier {
                    override fun verify(hostname: String, session: SSLSession): Boolean {
                        return true
                    }
                })
                return builder
            } catch (e: Exception) {
                throw RuntimeException(e)
            }

        }


    }


}