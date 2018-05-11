package s.hfad.com.myapplicationpaveltest.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import s.hfad.com.myapplicationpaveltest.Activity.Activity_Web;
import s.hfad.com.myapplicationpaveltest.modelAsets.AdapterNewsSelect;
import s.hfad.com.myapplicationpaveltest.modelAsets.ListNewsStrings;
import s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing.Article;
import s.hfad.com.myapplicationpaveltest.modelAsets.NewsParsing.ParsingNewsRetrofit;
import s.hfad.com.myapplicationpaveltest.R;
import s.hfad.com.myapplicationpaveltest.modelAsets.AdapterHome;
import s.hfad.com.myapplicationpaveltest.modelAsets.Menu;

public class BlankFragmentHome extends Fragment {

    private final String KEY_PREFERENCES = "keyPreferences";
    private final String KEY_POSITION = "keyPosition";
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

    public BlankFragmentHome() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_blank_fragment_home, container, false);
        initializationComponent();
        mListNewsStrings = new ListNewsStrings(getContext());
        loadPosition();
        if (isNetWorkAvailable()){
            parsingNews(indicatePosition);
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
        parsingNews(position);
    }


    private void loadPosition(){
        mSharedPreferences = view.getContext().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        try {
            indicatePosition = mSharedPreferences.getInt(KEY_POSITION,0);

        }catch (Exception e){
            Log.e("load Position","Load position error");
        }
    }


    private void savePosition(){
        mSharedPreferences = view.getContext().getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(KEY_POSITION,indicatePosition);
        editor.apply();

    }

    private void initializationMenu(ArrayList<Article> parser){
        List<Menu> mMenus=new ArrayList<>();
        initializationNewsName(parser.get(0).getSource().getName());
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
        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        adapterHome=new AdapterHome(getActivity(),mMenus);
        rv.setAdapter(adapterHome);
        listenerAdapterMenu();
    }

    private void initializationNewsName(String name) {
        mTextViewNewsName.setText(name);
    }


    private void parsingNews(int position){

        new ParsingNewsRetrofit(position).getParser()
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
            createDialog();
            return true;
        });
    }

    private void createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setTitle(getString(R.string.are_you_sure));
        builder.setPositiveButton(getString(R.string.Ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                savePosition();
                Toast .makeText(view.getContext(),getText(R.string.positionSave),Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton(getString(R.string.No), (dialog, which) -> dialog.cancel());

        AlertDialog alert = builder.create();
        alert.show();
    }
}















