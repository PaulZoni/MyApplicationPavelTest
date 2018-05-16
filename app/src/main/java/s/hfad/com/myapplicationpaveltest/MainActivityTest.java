package s.hfad.com.myapplicationpaveltest;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import s.hfad.com.myapplicationpaveltest.Parser.ParserValute;
import s.hfad.com.myapplicationpaveltest.fragment.BlankFragmentInformation;
import s.hfad.com.myapplicationpaveltest.fragment.BlankFragmentValute;
import s.hfad.com.myapplicationpaveltest.modelAsets.KeyWord;
import static android.content.Context.MODE_PRIVATE;


public class MainActivityTest extends Fragment implements IView,View.OnClickListener,HomePage.OnBackPressedListener {

    protected  static   HashMap<String,Double> v=new HashMap<>();
    private static final String START_KEY_TIME="START_KEY";
    static boolean STAT_TIME=false;
    private SharedPreferences preferences;
    private MainPresenter presenter;
    private RVAdapter adapter;
    private FloatingActionButton mFloatingActionButton;
    private KeyWord mKeyWord;
    private EditText faindEditText;
    static final String TAG_1="ID_Pager";
    protected TextView textView;
    protected EditText editText;
    protected Spinner spinnerLeft;
    protected Spinner spinnerRight;
    private CollapsingToolbarLayout collapsing;
    private NestedScrollView  scrollView;
    private LinearLayout linearLayout1All;
    private Button buttonSum;
    private List<ValutaModel> persons;

    private View view;
    public MainActivityTest() {
        mKeyWord=new KeyWord();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         view=inflater.inflate(R.layout.activity_main_test, container, false);

        loadingFragment();

        if (presenter==null){
            presenter=new MainPresenter(this);
        }
        buttonSum=(Button)view.findViewById(R.id.button_Sum);
        buttonSum.setOnClickListener(this);

        activateToast();
        settings();
        floatingButton();
        initializationComponent();
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void floatingButton(){
        mFloatingActionButton= view.findViewById(R.id.buttonFind);
        mFloatingActionButton.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getLayoutInflater();

            View dialogView=inflater.inflate(R.layout.dialog_signin, null);

            builder.setView(dialogView)
                    .setPositiveButton(R.string.app_name_Ok, (dialogInterface, i) -> {

                        faindEditText= dialogView.findViewById(R.id.editText_find);
                        String string=faindEditText.getText().toString();

                        String answer=mKeyWord.wordAnsver(string);

                        switch (answer) {
                            case "USD":

                                positionChoice(0);

                                break;
                            case "EUR":
                                positionChoice(1);

                                break;

                            case "CHF":
                                positionChoice(2);
                                break;

                            case "AUD":
                                positionChoice(3);
                                break;

                            case "AZN":
                                positionChoice(4);
                                break;

                            case "GBP":
                                positionChoice(5);
                                break;

                            case "AMD":
                                positionChoice(6);
                                break;

                            case "BYN":
                                positionChoice(7);
                                break;

                            case "BGN":
                                positionChoice(8);
                                break;

                            case "BRL":
                                positionChoice(9);
                                break;

                            case "HUF":
                                positionChoice(10);
                                break;
                            case "HKD":
                                positionChoice(11);
                                break;

                            case "DKK":
                                positionChoice(12);
                                break;

                            case "INR":
                                positionChoice(13);
                                break;

                            case "KZT":
                                positionChoice(14);
                                break;

                            default:
                                Toast toast = Toast.makeText(getActivity(), "Вы ввели не правельный результат", Toast.LENGTH_SHORT);
                                toast.show();
                                break;
                        }

                    });

            AlertDialog alert = builder.create();
            alert.show();
        });
    }

    public void loadingFragment(){
        android.support.v4.app.FragmentManager manager=getFragmentManager();
        Fragment fragment=manager.findFragmentById(R.id.fragmentContainer);
        fragment = new BlankFragmentValute();
        manager.beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit();

        parserValute();
    }

    public void loadingPosition(Bundle b){
        android.support.v4.app.FragmentManager manager=getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction=manager.beginTransaction();
        BlankFragmentInformation frag=new BlankFragmentInformation();
        frag.setArguments(b);
        transaction.replace(R.id.fragmentContainer,frag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void lisnerAdapterPerson(){

        adapter.setListener(position -> loadingPager(position));
    }


    private void initializationComponent(){
        collapsing=view.findViewById(R.id.toolbar_collapsing);
        scrollView=view.findViewById(R.id.nestedScrollView_converter);
        linearLayout1All=view.findViewById(R.id.layout_assets_info_all);

    }

    private void fragmentInformationStatusTurnedOn(){
        scrollView.setLayoutParams(new CoordinatorLayout.LayoutParams(0,0));
        AppBarLayout.LayoutParams p = (AppBarLayout.LayoutParams) collapsing.getLayoutParams();
        p.setScrollFlags(0);
        linearLayout1All.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
    }

    public void loadingPager(int position){

        fragmentInformationStatusTurnedOn();

        android.support.v4.app.FragmentManager manager=getFragmentManager();
        android.support.v4.app.FragmentTransaction transaction=manager.beginTransaction();
        BlankFragmentPager fragment3=new BlankFragmentPager();
        Bundle bundle=new Bundle();
        bundle.putSerializable(TAG_1,position);
        fragment3.setArguments(bundle);

        transaction.replace(R.id.layout_assets_info_1,fragment3);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void positionChoice(int position){
        Bundle bundle=new Bundle();
        bundle.putInt("stat",position);
        loadingPosition(bundle);
    }

    public void parserValute(){
        try {

            new ParserValute().getParser()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(strings -> {

                        MainPresenter.setValue(strings);
                        persons = new ArrayList<>();
                        persons.add(new ValutaModel("Доллар США", String.valueOf(strings.get("USD")), R.mipmap.usd));
                        persons.add(new ValutaModel("Валюта еврозоны Евро", String.valueOf(strings.get("EUR")), R.mipmap.euro));
                        persons.add(new ValutaModel("Швейцарский Франк ", String.valueOf(strings.get("CHF")), R.mipmap.chf));
                        persons.add(new ValutaModel("Австралийский доллар", String.valueOf(strings.get("AUD")), R.mipmap.australia_flag));
                        persons.add(new ValutaModel("Азербайджанский манат", String.valueOf(strings.get("AZN")), R.mipmap.azeri_manat_symbol));
                        persons.add(new ValutaModel("Фунт стерлингов Соединенного королевства", String.valueOf(strings.get("GBP")), R.mipmap.pound));
                        persons.add(new ValutaModel("Армянских драмов", String.valueOf(strings.get("AMD")), R.mipmap.armenian_dram_sign));
                        persons.add(new ValutaModel("Белорусский рубль", String.valueOf(strings.get("BYN")), R.mipmap.bel12));
                        persons.add(new ValutaModel("Болгарский лев", String.valueOf(strings.get("BGN")), R.mipmap.bg));
                        persons.add(new ValutaModel("Бразильский реал", String.valueOf(strings.get("BRL")), R.mipmap.mini_brasil));
                        persons.add(new ValutaModel("Венгерских форинтов", String.valueOf(strings.get("HUF")), R.mipmap.detected));
                        persons.add(new ValutaModel("Гонконгских долларов", String.valueOf(strings.get("HKD")), R.mipmap.hongkong));
                        persons.add(new ValutaModel("Датских крон", String.valueOf(strings.get("DKK")), R.mipmap.dannebrog));
                        persons.add(new ValutaModel("Индийских рупий", String.valueOf(strings.get("INR")), R.mipmap.indian_rupee_symbol));
                        persons.add(new ValutaModel("Казахстанских тенге", String.valueOf(strings.get("KZT")), R.mipmap.tenge));


                        RecyclerView rv = (RecyclerView)view.findViewById(R.id.rv);
                        LinearLayoutManager llm = new LinearLayoutManager(getContext());
                        rv.setLayoutManager(llm);
                        adapter = new RVAdapter(getActivity(),persons);
                        rv.setAdapter(adapter);

                        lisnerAdapterPerson();

                    });

        }catch (Exception e){

            persons.add(new ValutaModel("Нет соединения", "Нет соединения", R.mipmap.usd));
        }
    }


    public void activateToast(){
        CharSequence text="Вы активировали конвертер!";
        int duration=Toast.LENGTH_SHORT;
        Toast toastTexst=Toast.makeText(getActivity(),text,duration);
        toastTexst.show();
    }


    @Override
    public void onClick(View view) {
        presenter.onGetButtonClicked();
    }


    @Override
    public TextView getTextView() {
        return textView=(TextView)view.findViewById(R.id.textView);
    }

    @Override
    public EditText getTextEdit() {
        return editText=(EditText)view.findViewById(R.id.edit_number1);
    }

    @Override
    public Spinner getLeftSpiner() {
        return spinnerLeft=(Spinner)view.findViewById(R.id.spinner_left);
    }

    @Override
    public Spinner getRightSpiner() {
        return spinnerRight=(Spinner)view.findViewById(R.id.spinner_right);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        preferences=getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed=preferences.edit();
        ed.putBoolean(START_KEY_TIME,STAT_TIME);
        ed.apply();
    }

    public void settings(){
        preferences=getActivity().getPreferences(MODE_PRIVATE);
        STAT_TIME=preferences.getBoolean(START_KEY_TIME,false);
    }


    @Override
    public void onBackPressed() {

        FragmentManager manager=getFragmentManager();
        Fragment fragment=manager.findFragmentById(R.id.homeContainer);
        fragment = new MainActivityTest();
        manager.beginTransaction()
                .replace(R.id.homeContainer, fragment)
                .commit();
    }

}












