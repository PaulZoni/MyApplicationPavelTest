package s.hfad.com.myapplicationpaveltest.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import s.hfad.com.myapplicationpaveltest.Activity.Activity_Web;
import s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing.Article;
import s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing.ParsingNewsRetrofit;
import s.hfad.com.myapplicationpaveltest.R;
import s.hfad.com.myapplicationpaveltest.modelAsets.AdapterHome;
import s.hfad.com.myapplicationpaveltest.modelAsets.Menu;

public class BlankFragmentHome extends Fragment {


    private AdapterHome adapterHome;
    private View view;
    private NestedScrollView mScrollView;
    private FloatingActionButton mFloatingActionButton;

    public BlankFragmentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_blank_fragment_home, container, false);

        if (isNetWorkAvailable()){
            parsinNews();
        }else {
            initializationMenu(null);
        }

        initializationComponent();
        scrollFunction();
        return view;
    }


    private void initializationComponent(){
        mFloatingActionButton = view.findViewById(R.id.scrollViewButtonHome);
        mScrollView = view.findViewById(R.id.nestedScrollView);
    }

    private void scrollFunction(){
        mFloatingActionButton.setOnClickListener
                (v -> mScrollView.scrollTo(0,mScrollView.getMaxScrollAmount()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private boolean isNetWorkAvailable(){
        ConnectivityManager connectivityManager=(ConnectivityManager)getActivity()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }else {
            return false;
        }

        if (networkInfo==null){
            return false;

        }else if (!networkInfo.isConnected()){
            return false;
        }else {
            return true;
        }

    }

    private void initializationMenu(ArrayList<Article> parser){
        List<Menu> mMenus=new ArrayList<>();

        if (parser!=null){
            for (int i = 0; i <parser.size() ; i++) {
                String title;

                if (testWrongs(parser.get(i).getDescription())){

                    if (testWrongs(parser.get(i).getTitle())){
                        title= String.valueOf(R.string.noTitle);
                    }else {
                        title=parser.get(i).getTitle();
                    }

                }else {
                    title=parser.get(i).getDescription();
                }

                mMenus.add(new Menu(title,
                        parser.get(i).getUrlToImage(),
                        parser.get(i).getUrl()));
            }
        }else {
            mMenus.add(new Menu("Network Unavailable",null,null));
        }


        RecyclerView rv = view.findViewById(R.id.LIst_homePage);
        rv.setHasFixedSize(true);
        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        adapterHome=new AdapterHome(getActivity(),mMenus);
        rv.setAdapter(adapterHome);
        listnerAdapterMqnu();
    }

    private void parsinNews(){

        new ParsingNewsRetrofit().getParser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::initializationMenu);

    }


    private boolean testWrongs(String strings){
        if (strings==null){
            return true;
        }
        Pattern pattern=Pattern.compile("�");
        Matcher matcher=pattern.matcher(strings);

        if (strings.length()<12 | matcher.find()){
            return true;
        }
        return false;
    }

    public void listnerAdapterMqnu(){
        adapterHome.setListener((position,url) -> {

            Intent intent= Activity_Web.newIntentActivityWeb(getContext(),url);
            startActivity(intent);


        });
    }
}
