package s.hfad.com.myapplicationpaveltest.fragment;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.getbase.floatingactionbutton.FloatingActionButton;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import s.hfad.com.myapplicationpaveltest.Activity.Activity_Web;
import s.hfad.com.myapplicationpaveltest.HomePage;
import s.hfad.com.myapplicationpaveltest.Interface.MVPView;
import s.hfad.com.myapplicationpaveltest.Interface.PresenterNewsInterface;
import s.hfad.com.myapplicationpaveltest.Presenter.PresenterNews;
import s.hfad.com.myapplicationpaveltest.modelAsets.AdapterNewsSelect;
import s.hfad.com.myapplicationpaveltest.modelAsets.ListNewsStrings;
import s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing.Article;
import s.hfad.com.myapplicationpaveltest.R;
import s.hfad.com.myapplicationpaveltest.modelAsets.AdapterHome;
import s.hfad.com.myapplicationpaveltest.modelAsets.Menu;

public class BlankFragmentHome extends Fragment implements MVPView {

    private final String KEY_PREFERENCES = "keyPreferences";
    private final String KEY_POSITION = "keyPosition";
    private final String SAVED_LANGUAGE = "saved_language";
    private int indicatePosition;
    private AdapterHome adapterHome;
    private View view;
    private NestedScrollView mScrollView;
    private FloatingActionButton mFloatingActionButton;
    private RecyclerView recyclerViewNewsSelect;
    private AdapterNewsSelect adapterNewsSelect;
    private ListNewsStrings mListNewsStrings;
    private LinearLayoutManager manager;
    private TextView mTextViewNewsName;
    private SharedPreferences mSharedPreferences;
    private PresenterNewsInterface<MVPView> mNewsPresenter;
    private String STAT_LANGUAGE;



    private DialogSelectLanguage mDialogSelectLanguage;
    public interface DialogSelectLanguage extends Serializable {
        void createDialogSelectLanguage();
        String getStatLanguage();
    }

    public void setDialogSelectLanguage() {

        if (getArguments().getParcelable(HomePage.KEY_BUNDLE_HOME)!=null) {
            mDialogSelectLanguage = getArguments().getParcelable(HomePage.KEY_BUNDLE_HOME);
        }
    }


    public BlankFragmentHome() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_blank_fragment_home, container, false);
        mNewsPresenter = new PresenterNews();
        mNewsPresenter.attachView(this);
        setDialogSelectLanguage();
        initializationComponent();
        mListNewsStrings = new ListNewsStrings(getContext());
        loadPosition();
        if (isNetWorkAvailable()){

            mNewsPresenter.loadingNews(indicatePosition);

        }else {
            initializationMenu(null);
        }

        initializationSelectNewsRecyclerView();
        scrollFunction();
        createToolbarMenu();
        return view;
    }


    private void initializationComponent(){
        mFloatingActionButton = view.findViewById(R.id.scrollViewButtonHome);
        mScrollView = view.findViewById(R.id.nestedScrollView);
        recyclerViewNewsSelect = view.findViewById(R.id.recyclerView_selectNews);
        mTextViewNewsName = view.findViewById(R.id.textView_newsName);
    }

    private void scrollFunction(){
        mFloatingActionButton.setOnClickListener
                (v -> mScrollView.scrollTo(0,mScrollView.getMaxScrollAmount()));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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

    private void initializationSelectNewsRecyclerView(){

        manager=new LinearLayoutManager(view.getContext(),LinearLayoutManager.HORIZONTAL,true);
        recyclerViewNewsSelect.setLayoutManager(manager);

        recyclerViewNewsSelect.setHasFixedSize(true);
        recyclerViewNewsSelect.setNestedScrollingEnabled(false);

        adapterNewsSelect=new AdapterNewsSelect(mListNewsStrings.getStringList());
        recyclerViewNewsSelect.setAdapter(adapterNewsSelect);
        adapterNewsSelect.setListenerAdapterNewsSelect(this::loadingPosition);
    }


    private void loadingPosition(int position){
        indicatePosition = position;
        mNewsPresenter.loadingNews(position);
    }


    private void loadPosition(){
        mSharedPreferences = view.getContext().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        try {
            indicatePosition = mSharedPreferences.getInt(KEY_POSITION,0);
            STAT_LANGUAGE = mSharedPreferences.getString(SAVED_LANGUAGE,"");
        }catch (Exception e){
            Log.e("load Position","Load position error");
        }
    }


    private void savePosition(){
        mSharedPreferences = view.getContext().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(KEY_POSITION,indicatePosition);
        editor.apply();
        editor.commit();
    }

    private void saveLanguage(){
        mSharedPreferences = view.getContext().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(SAVED_LANGUAGE,STAT_LANGUAGE);
        editor.apply();

    }

    @Override
    public void initializationMenu(List<Article> parser){
        List<Menu> mMenus=new ArrayList<>();
        if (parser!=null){
            initializationNewsName(parser.get(0).getSource().getName());
        }
        if (parser!=null){
            for (int i = 0; i <parser.size() ; i++) {
                String title;
                if (testWrongs(parser.get(i).getDescription())){

                    if (testWrongs(parser.get(i).getTitle())){
                        title= String.valueOf(getString(R.string.noTitle));
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
        StaggeredGridLayoutManager llm = checkOrientation();
        rv.setLayoutManager(llm);
        adapterHome=new AdapterHome(getActivity(),mMenus);
        rv.setAdapter(adapterHome);
        if (isNetWorkAvailable()){
            listenerAdapterMenu();
        }
    }

    private StaggeredGridLayoutManager checkOrientation(){
        int parameter = 1;
        int configuration = view.getResources().getConfiguration().orientation;
        if (configuration == Configuration.ORIENTATION_LANDSCAPE)parameter = 2;
        if (configuration == Configuration.ORIENTATION_PORTRAIT)parameter = 1;
        return  new StaggeredGridLayoutManager(parameter,StaggeredGridLayoutManager.VERTICAL);
    }


    private void initializationNewsName(String name) {
        mTextViewNewsName.setText(name);
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

    public void listenerAdapterMenu(){
        adapterHome.setListener((position,url) -> {
            Intent intent= Activity_Web.newIntentActivityWeb(getContext(),url);
            startActivity(intent);
        });
    }


    private void createToolbarMenu(){
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_news);
        toolbar.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            switch (id){
                case R.id.list_itemNews:
                    createDialogSelectNews();
                    break;
                case R.id.languageTranslation:
                    mDialogSelectLanguage.createDialogSelectLanguage();
                    break;
                case R.id.languageTranslationRun:
                    STAT_LANGUAGE = mDialogSelectLanguage.getStatLanguage();
                    mNewsPresenter.textParsing(STAT_LANGUAGE);
                    saveLanguage();
            }
            return true;
        });
    }


    private void createDialogSelectNews(){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(getString(R.string.are_you_sure));
        builder.setPositiveButton(getString(R.string.Ok), (dialog, which) -> {
            savePosition();
            Toast .makeText(view.getContext(),getText(R.string.positionSave),Toast.LENGTH_SHORT).show();
        });

        builder.setNegativeButton(getString(R.string.No), (dialog, which) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }
}















