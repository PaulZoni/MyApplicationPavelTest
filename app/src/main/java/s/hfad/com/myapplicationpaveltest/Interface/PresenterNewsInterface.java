package s.hfad.com.myapplicationpaveltest.Interface;


import java.util.ArrayList;

import s.hfad.com.myapplicationpaveltest.model_assets.news_parsing.Article;

public interface PresenterNewsInterface<V> {

    void attachView(V mvpView);
    void loadingNews(int position);
    void textParsing(String languageEnum);
    public ArrayList<Article> getNews();
}
