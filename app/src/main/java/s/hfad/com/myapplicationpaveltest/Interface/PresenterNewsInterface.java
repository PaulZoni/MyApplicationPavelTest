package s.hfad.com.myapplicationpaveltest.Interface;


public interface PresenterNewsInterface<V> {

    void attachView(V mvpView);
    void loadingNews(int position);
    void textParsing(String languageEnum);
}
