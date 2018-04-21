package s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing;


import retrofit2.Call;
import retrofit2.http.GET;

public interface APIService {
    @GET("v2/top-headlines?sources=google-news-ru&apiKey=502081a306e24199bd0aa097800a13e7")
    Call<ModelNews> callBack();
}
