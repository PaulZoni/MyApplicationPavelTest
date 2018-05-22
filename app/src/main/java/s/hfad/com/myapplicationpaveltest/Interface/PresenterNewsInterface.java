package s.hfad.com.myapplicationpaveltest.Interface;


import java.util.ArrayList;

import s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing.Article;

public interface PresenterNewsInterface<V> {

    void attachView(V mvpView);
    void loadingNews(int position);
    ArrayList<Article> getNews();
    void textParsing();
}
