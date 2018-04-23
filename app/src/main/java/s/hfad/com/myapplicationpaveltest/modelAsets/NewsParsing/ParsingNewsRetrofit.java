package s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing;


import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing.APIService;
import s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing.Article;
import s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing.ModelNews;

public class ParsingNewsRetrofit {

    private  ArrayList<Article> article;


    public Observable<ArrayList<Article>> getParser() {
        return Observable.create(observableEmitter ->{

            observableEmitter.onNext(parser());
        });
    }

    private Retrofit buildingUrlRetro(){
        Retrofit retrofit =new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getUnsafeOkHttpClient().build())
                .build();
        return retrofit;
    }

    public ArrayList<Article> parser()  {
        Retrofit retrofit=buildingUrlRetro();
        final APIService servis=retrofit.create(APIService.class);
        Call<ModelNews> resp=servis.callBack();

        try {
            Response<ModelNews> modelNews=resp.execute();
            article= (ArrayList<Article>) modelNews.body().getArticles();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return article;
    }

    private  OkHttpClient.Builder getUnsafeOkHttpClient() {
        try {

            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };


            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());


            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier((hostname, session) -> true);
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
