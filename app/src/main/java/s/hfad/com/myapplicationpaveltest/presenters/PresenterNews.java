package s.hfad.com.myapplicationpaveltest.presenters;


import android.util.Log;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import s.hfad.com.myapplicationpaveltest.Interface.MVPView;
import s.hfad.com.myapplicationpaveltest.Interface.PresenterNewsInterface;
import s.hfad.com.myapplicationpaveltest.model_assets.news_parsing.Article;
import s.hfad.com.myapplicationpaveltest.model_assets.news_parsing.ParsingNewsRetrofit;
import s.hfad.com.myapplicationpaveltest.model_assets.translate_language.YandexTranslate;

public class PresenterNews implements PresenterNewsInterface<MVPView> {

    private MVPView mvpView;
    private ParsingNewsRetrofit mParsingNewsRetrofit;
    private ArrayList<Article> listNews;
    private int positionNewsChoice;


    public PresenterNews() {
        listNews = new ArrayList<>();
    }

    @Override
    public void attachView(MVPView mvpView) {
        this.mvpView = mvpView;
    }


    @Override
    public void loadingNews(int position) {
        positionNewsChoice= position;
        mParsingNewsRetrofit = new ParsingNewsRetrofit(position);
        mParsingNewsRetrofit.getParser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list1 -> {
                    listNews = list1;
                    mvpView.initializationMenu(list1);
                });
    }

    @Override
    public ArrayList<Article> getNews() {
        return listNews;
    }


    @Override
    public void textParsing(String languageEnum){
        List<String> allDescription = new ArrayList<>();
            new YandexTranslate(listNews.size())
                    .getTranslateText(listNews,positionNewsChoice,languageEnum)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(s -> {
                        int x = 0;
                        allDescription.add(x++,s);
                        if (allDescription.size()==10){
                            text(allDescription);
                        }
                    });

    }


    private void text(List<String> str){
        List<String> allDescription = new ArrayList<>();
        List<Article> articleListTranslate = new ArrayList<>();
        JSONParser parser = new JSONParser();
        JSONObject object = null;
        for (int i = 0; i<str.size() ; i++) {
            try {
                object = (JSONObject) parser.parse(str.get(i));
                org.json.simple.JSONArray objectText = (org.json.simple.JSONArray) object.get("text");
                allDescription.add(objectText.toJSONString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        int j = 0;
        for (int i = allDescription.size(); i>0 ; i--) {
            try {
                articleListTranslate.add(listNews.get(j).clone());
            } catch (CloneNotSupportedException e) {
                Log.e("waring","Clone do not work");
            }
            articleListTranslate.get(j).setDescription(allDescription.get(i-1));
            j++;
        }
        mvpView.initializationMenu(articleListTranslate);
    }
}

