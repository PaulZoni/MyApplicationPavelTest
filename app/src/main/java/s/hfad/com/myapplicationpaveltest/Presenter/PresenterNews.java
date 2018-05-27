package s.hfad.com.myapplicationpaveltest.Presenter;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.ArrayList;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import s.hfad.com.myapplicationpaveltest.Interface.MVPView;
import s.hfad.com.myapplicationpaveltest.Interface.PresenterNewsInterface;
import s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing.Article;
import s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing.ParsingNewsRetrofit;
import s.hfad.com.myapplicationpaveltest.modelAsets.TranslateLanguage.YandexTranslate;

public class PresenterNews implements PresenterNewsInterface<MVPView> {

    private MVPView mvpView;
    private ParsingNewsRetrofit mParsingNewsRetrofit;
    private ArrayList<Article> listNews;


    public PresenterNews() {
        listNews = new ArrayList<>();
    }

    @Override
    public void attachView(MVPView mvpView) {
        this.mvpView = mvpView;
    }


    @Override
    public void loadingNews(int position) {
        mParsingNewsRetrofit = new ParsingNewsRetrofit(position);
        mParsingNewsRetrofit.getParser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list1 -> {
                    listNews =list1;
                    mvpView.initializationMenu(list1);
                });
    }

    @Override
    public ArrayList<Article> getNews() {
        return listNews;
    }

    @Override
    public void textParsing(){
        StringBuilder allDescription = new StringBuilder();
        StringBuilder allTitle = new StringBuilder();
        if (listNews!=null){
            for (int i = 0; i <listNews.size(); i++) {
                allDescription.append(":").append(listNews.get(i).getDescription());
                allTitle.append(":").append(listNews.get(i).getTitle());
            }

        }
        String trans = "|"+allDescription+"|"+allTitle+"|";

         new YandexTranslate()
                 .getTranslateText(trans,"en-ru")
                 .subscribeOn(Schedulers.io())
                 .observeOn(AndroidSchedulers.mainThread())
                 .subscribe(this::text);
    }

    private void text(String str){

        JSONParser parser = new JSONParser();
        JSONObject object = null;
        try {
            object = (JSONObject) parser.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        String answer = (String) object.get("text");
        System.out.println(answer);

    }
}

















