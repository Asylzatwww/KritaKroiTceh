package fashion.krista.barcodereader.Retrofit;

import java.util.List;

import fashion.krista.barcodereader.model.Item;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {


    //    color-series-search?
    @GET("post-api/color-series-search?")
    Call<List<Item>> getModelWithId(@Query("model_series_id") String code, @Query("from_sklad_id") int from_sklad_id);

    @POST("post-api/color-series-move?")
    Call<ResponseBody> postModel(
            @Query("model_series_id") String code,
            @Query("sklad_id") String skladId,
            @Query("from_sklad_id") int from_skladId,
            @Query("qty") int qty);
}
