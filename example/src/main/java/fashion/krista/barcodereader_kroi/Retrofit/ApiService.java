package fashion.krista.barcodereader_kroi.Retrofit;

import java.util.List;

import fashion.krista.barcodereader_kroi.model.Item;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {


    //    color-series-search?
    @GET("post-api/color-series-search?")
    Call<List<Item>> getModelWithId(@Query("model_series_id") String code, @Query("from_sklad_id") int from_sklad_id);

    // get /post-api/search?model_size_id=408042&sklad_id=985
    @GET("post-api/search?")
    Call<List<Item>> getSewer(@Query("model_size_id") String code, @Query("klad_id") int from_sklad_id);

    @POST("post-api/color-series-move?")
    Call<ResponseBody> postModel(
            @Query("model_series_id") String code,
            @Query("sklad_id") String skladId,
            @Query("from_sklad_id") int from_skladId,
            @Query("qty") int qty);
}
