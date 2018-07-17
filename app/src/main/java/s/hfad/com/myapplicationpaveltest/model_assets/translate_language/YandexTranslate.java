package s.hfad.com.myapplicationpaveltest.model_assets.translate_language;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import io.reactivex.Observable;
import s.hfad.com.myapplicationpaveltest.model_assets.news_parsing.Article;

public class YandexTranslate {

    private int size;
    public YandexTranslate(int size) {
        this.size = size;
    }

    public Observable<String>getTranslateText(ArrayList<Article> text,
                                              int position,
                                              String languageEnum){
        String language;
        language = selectLanguage(position,languageEnum);
        ArrayList<Article> list = text;
        return Observable.create(observableEmitter ->{
            for (int i = 0; i <size ; i++) {
                observableEmitter.onNext(regulationTranslate(list.get(i).getDescription(),language));
            }

        });
    }

    private String selectLanguage(int position, String languageEnum) {
        if (languageEnum==null)languageEnum ="ru";
        String language;
        switch (position){
            case 0:
                language = "en-"+languageEnum;
                break;
            case 1:
                language = "fr-"+languageEnum;
                break;
            case 2:
                language = "en-"+languageEnum;
                break;
            case 3:
                language = "ru-"+languageEnum;
                break;
            case 4:
                language = "en-"+languageEnum;
                break;
            case 5:
                language = "en-"+languageEnum;
                break;
            case 6:
                language = "en-"+languageEnum;
                break;
            case 7:
                language = "en-"+languageEnum;
                break;
            case 8:
                language = "en-"+languageEnum;
                break;
            case 9:
                language = "en-"+languageEnum;
                break;
            case -1:
                language = "en-"+languageEnum;
                break;
            default:
                language = "en-"+languageEnum;
                break;
        }
        return language;
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

        String url = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20180518T213902Z.c6aef9f1d7e1c1b9.b7205913e2fd2690ee538d23a3e353ebaf26420e"
                +"&text="+ URLEncoder.encode(text,"UTF-8")+"&lang="+lang;

        URL url1 = new URL(url);
        HttpURLConnection httpConnection = (HttpURLConnection) url1.openConnection();
        int rc = httpConnection.getResponseCode();

        if (rc == 200) {
            String line = null;
            BufferedReader buffReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            StringBuilder strBuilder = new StringBuilder();
            while ((line = buffReader.readLine()) != null) {
                strBuilder.append(line).append('\n');
            }

            return strBuilder.toString();
        }
        return "NO";
    }
}
