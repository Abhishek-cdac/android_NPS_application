package com.nectar.nps.retrofit;



import com.nectarinfotel.utils.AppConstants;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Abhishek on 25/07/17.
 * This class is used to initialize the retrofit, set time out along with the headers with the http request.
 */

public class RetrofitClient {

   public static final String TAG = RetrofitClient.class.getSimpleName();

    private static Retrofit retrofit = null;
    public static OkHttpClient client;
    public static final int DEFAULT_TIMEOUT_SEC = 60;

    public static Retrofit getClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT_SEC, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT_SEC, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT_SEC, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()

                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });


        client = httpClient.build();
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://wfms.timesheet.nectarinfotel.com/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }



        return retrofit;
    }


}
