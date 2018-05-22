package s.hfad.com.myapplicationpaveltest.modelAsets.TranslateLanguage;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import io.reactivex.Observable;

public class YandexTranslate {


    public Observable<String>getTranslateText(String text,String format){
        return Observable.create(observableEmitter ->{

            observableEmitter.onNext(regulationTranslate(text,format));
        });
    }

    private String regulationTranslate(String text,String format){
        String result;
        try {
            result = translate(text,format,"plain","1",null);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "NOConnect";
    }

    private String translate(String text,String lang,String format,String options,String cal) throws IOException {

        String url2 = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20180518T213902Z.c6aef9f1d7e1c1b9.b7205913e2fd2690ee538d23a3e353ebaf26420e"
                +"&text="+ URLEncoder.encode(text,"UTF-8")+"&lang="+lang;

        URL url1 = new URL(url2);
        HttpURLConnection httpConnection = (HttpURLConnection) url1.openConnection();
        int rc = httpConnection.getResponseCode();

        if (rc == 200) {
            String line = null;
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            StringBuilder strBuilder = new StringBuilder();
            while ((line = buffReader.readLine()) != null) {
                strBuilder.append(line + '\n');
            }

            return strBuilder.toString();
        }
        return "NO";
    }
}
