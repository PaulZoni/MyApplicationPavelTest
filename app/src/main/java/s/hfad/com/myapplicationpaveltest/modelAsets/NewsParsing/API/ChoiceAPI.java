package s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing.API;


import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing.ModelNews;

public  class ChoiceAPI {

    private Retrofit retrofit;

    public ChoiceAPI(Retrofit retrofit) {
        this.retrofit=retrofit;
    }

    private interface APIServiceRussia  {
        @GET("v2/top-headlines?sources=google-news-ru&apiKey=502081a306e24199bd0aa097800a13e7")
        Call<ModelNews> callBack();
    }

    private interface APIServiceCanada {
        @GET("v2/top-headlines?sources=google-news-ca&apiKey=502081a306e24199bd0aa097800a13e7")
        Call<ModelNews> callBack();
    }

    private interface APIServiceAustralia {
        @GET("v2/top-headlines?sources=google-news-au&apiKey=502081a306e24199bd0aa097800a13e7")
        Call<ModelNews> callBack();
    }

    private interface APIServiceFrance {
        @GET("v2/top-headlines?sources=google-news-fr&apiKey=502081a306e24199bd0aa097800a13e7")
        Call<ModelNews> callBack();
    }

    private interface APIServiceABCNews {
        @GET("v2/top-headlines?sources=abc-news&apiKey=502081a306e24199bd0aa097800a13e7")
        Call<ModelNews> callBack();
    }

    private interface APIServiceArsTechnica {
        @GET("v2/top-headlines?sources=ars-technica&apiKey=502081a306e24199bd0aa097800a13e7")
        Call<ModelNews> callBack();
    }

    private interface APIServiceAssociatedPress {
        @GET("v2/top-headlines?sources=associated-press&apiKey=502081a306e24199bd0aa097800a13e7")
        Call<ModelNews> callBack();
    }

    private interface APIServiceBBCNews {
        @GET("v2/top-headlines?sources=bbc-news&apiKey=502081a306e24199bd0aa097800a13e7")
        Call<ModelNews> callBack();
    }

    private interface APIServiceBloomberg {
        @GET("v2/top-headlines?sources=bloomberg&apiKey=502081a306e24199bd0aa097800a13e7")
        Call<ModelNews> callBack();
    }

    private interface APIServiceInfoMoney {
        @GET("v2/top-headlines?sources=info-money&apiKey=502081a306e24199bd0aa097800a13e7")
        Call<ModelNews> callBack();
    }


    public Call<ModelNews> getAPIServiceInfoMoney(){
        APIServiceInfoMoney service = retrofit.create( APIServiceInfoMoney.class);
        return service.callBack();
    }

    public Call<ModelNews> getAPIServiceBloomberg(){
        APIServiceBloomberg service = retrofit.create( APIServiceBloomberg.class);
        return service.callBack();
    }

    public Call<ModelNews> getAPIServiceCanada(){
        APIServiceCanada service = retrofit.create( APIServiceCanada.class);
        return service.callBack();
    }

    public Call<ModelNews> getAPIServiceRussia(){
        APIServiceRussia service = retrofit.create(APIServiceRussia.class);
        return service.callBack();
    }

    public Call<ModelNews> getAPIServiceAustralia(){
        APIServiceAustralia service = retrofit.create(APIServiceAustralia.class);
        return service.callBack();
    }

    public Call<ModelNews> getAPIServiceFrance(){
        APIServiceFrance service = retrofit.create(APIServiceFrance.class);
        return service.callBack();
    }

    public Call<ModelNews> getAPIServiceABCNews(){
        APIServiceABCNews service = retrofit.create(APIServiceABCNews.class);
        return service.callBack();
    }

    public Call<ModelNews> getAPIServiceArsTechnica(){
        APIServiceArsTechnica service = retrofit.create(APIServiceArsTechnica.class);
        return service.callBack();
    }

    public Call<ModelNews> getAPIServiceAssociatedPress(){
        APIServiceAssociatedPress service = retrofit.create(APIServiceAssociatedPress.class);
        return service.callBack();
    }

    public Call<ModelNews> getAPIServiceBBCNews(){
        APIServiceBBCNews service = retrofit.create(APIServiceBBCNews.class);
        return service.callBack();
    }
}












