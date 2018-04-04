package s.hfad.com.myapplicationpaveltest;

import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import s.hfad.com.myapplicationpaveltest.modelAsets.Graph;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import s.hfad.com.myapplicationpaveltest.modelAsets.Graph;
import s.hfad.com.myapplicationpaveltest.modelAsets.Sound;

import static android.content.Context.MODE_PRIVATE;


public class MonetaryAssets extends Fragment implements IViewPurse,View.OnClickListener {

    private MainPresenterPurse presenter;

    private FloatingActionButton buttonOk;
    private FloatingActionButton buttonAll;
    protected EditText editTextNumber;
    protected EditText editTextTx;
    private View view;

    private static final String START_KEY="START_KEY";
    SharedPreferences sPref;
    static boolean STAT=false;
    static private final String KEY_ASSETS="assets_key";


    /*private static final int MAX_SOUNDS = 5;
    int soundId;
    SoundPool mSoundPool;
    private AssetManager mAssets;
    private static final String SOUNDS_FOLDER = "sounds";*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         view=inflater.inflate(R.layout.activity_monetary_assets,container,false);

        if (presenter==null){
            presenter=new MainPresenterPurse(this,getContext(),KEY_ASSETS);
        }
        buttonOk=(FloatingActionButton)view.findViewById(R.id.actionButtonAdd);
        buttonOk.setOnClickListener(this);
        buttonAll=(FloatingActionButton)view.findViewById(R.id.actionButtonAll);
        buttonAll.setOnClickListener(this);

        graphV();
        settings();
        setHasOptionsMenu(true);

        /*mAssets=getActivity().getAssets();
        mSoundPool=new SoundPool(MAX_SOUNDS, AudioManager.STREAM_MUSIC, 0);
        mSoundPool.setOnLoadCompleteListener(this);
        try {
            mAssets.list(SOUNDS_FOLDER);
        } catch (IOException e) {

        }
        try {
            soundId = mSoundPool.load(mAssets.openFd("00923.wav"), 1);
        } catch (IOException e) {

        }*/

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public void settings(){
        sPref=getActivity().getPreferences(MODE_PRIVATE);
        STAT=sPref.getBoolean(START_KEY,false );
    }


    public void graphV(){


        new Graph(getContext(),KEY_ASSETS).getGraph()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listY -> {

                    DataPoint[] points=new DataPoint[listY.size()];
                    for (int i = 0; i <points.length ; i++) {
                        points[i]=new DataPoint(i,listY.get(i));
                    }


                    GraphView graph = (GraphView)getActivity().findViewById(R.id.graph);

                    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

                    graph.getViewport().setYAxisBoundsManual(true);
                    graph.getViewport().setMinY(-150);
                    graph.getViewport().setMaxY(150);

                    graph.getViewport().setXAxisBoundsManual(true);
                    graph.getViewport().setMinX(4);
                    graph.getViewport().setMaxX(80);

                    // enable scaling and scrolling
                    graph.getViewport().setScalable(true);
                    graph.getViewport().setScalableY(true);

                    graph.addSeries(series);

                });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_purse,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        switch (id){
            case R.id.action_settings:
                presenter.newTime();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        presenter.buttonOnClick(view);
        //mSoundPool.play(soundId,1.0f, 1.0f, 1, 0, 1.0f);
    }

    @Override
    public EditText getEditTextNumber(){
        return editTextNumber=(EditText)view.findViewById(R.id.editTextAssets_sum);
    }

    @Override
    public EditText getEditTextTx() {
        return editTextTx=(EditText)view.findViewById(R.id.editTextAssets_text);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        sPref=getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed=sPref.edit();
        ed.putBoolean(START_KEY,STAT);
        ed.apply();
    }


}










