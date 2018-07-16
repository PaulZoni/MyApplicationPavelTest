package s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing;


import java.io.IOException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing.API.ChoiceAPI;


public class ParsingNewsRetrofit {

    private  ArrayList<Article> article;
    public final static int NON_PARS = -1;
    private  int pasition;
    private ChoiceAPI mChoiceAPI;
    private Call<ModelNews> resp;

    public ParsingNewsRetrofit(int position) {
        this.pasition=position;
    }


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


    private ArrayList<Article> parser()  {
        Retrofit retrofit=buildingUrlRetro();
        mChoiceAPI=new ChoiceAPI(retrofit);
        switch (pasition){
            case 0:
                resp = mChoiceAPI.getAPIServiceCanada();
                break;
            case 1:
                resp = mChoiceAPI.getAPIServiceFrance();
                break;
            case 2:
                resp = mChoiceAPI.getAPIServiceAustralia();
                break;
            case 3:
                resp = mChoiceAPI.getAPIServiceRussia();
                break;
            case 4:
                resp = mChoiceAPI.getAPIServiceABCNews();
                break;
            case 5:
                resp = mChoiceAPI.getAPIServiceArsTechnica();
                break;
            case 6:
                resp = mChoiceAPI.getAPIServiceAssociatedPress();
                break;
            case 7:
                resp = mChoiceAPI.getAPIServiceBBCNews();
                break;
            case 8:
                resp = mChoiceAPI.getAPIServiceBloomberg();
                break;
            case 9:
                resp = mChoiceAPI.getAPIServiceInfoMoney();
                break;
            case NON_PARS:
                 resp = mChoiceAPI.getAPIServiceRussia();
                break;
            default:
                resp = mChoiceAPI.getAPIServiceRussia();
                break;
        }

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
