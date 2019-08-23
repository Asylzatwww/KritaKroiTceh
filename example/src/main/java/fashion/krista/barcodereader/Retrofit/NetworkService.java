package fashion.krista.barcodereader.Retrofit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static final String BASE_URL = "http://my.krista.fashion/";
    private static NetworkService mInstance;
    private Retrofit mRetrofit;

    private NetworkService() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder
                client = new OkHttpClient.Builder()
                .addInterceptor(interceptor);

        client.interceptors().add(interceptor);
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public ApiService getApi() {
        return mRetrofit.create(ApiService.class);
    }
}
