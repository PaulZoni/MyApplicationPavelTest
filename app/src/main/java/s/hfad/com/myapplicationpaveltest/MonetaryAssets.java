package s.hfad.com.myapplicationpaveltest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Date;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class MonetaryAssets extends Activity implements IViewPurse,View.OnClickListener {

    private MainPresenterPurse presenter;

    private FloatingActionButton buttonOk;
    private FloatingActionButton buttonAll;
    protected EditText editTextNumber;
    protected EditText editTextTx;

    private static final String START_KEY="START_KEY";
    SharedPreferences sPref;
    static boolean STAT=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monetary_assets);

        if (presenter==null){
            presenter=new MainPresenterPurse(this,this);
        }
        buttonOk=(FloatingActionButton)findViewById(R.id.actionButtonAdd);
        buttonOk.setOnClickListener(this);
        buttonAll=(FloatingActionButton)findViewById(R.id.actionButtonAll);
        buttonAll.setOnClickListener(this);

        graphV();
        settings();

    }


    public void settings(){
        sPref=getPreferences(MODE_PRIVATE);
        STAT=sPref.getBoolean(START_KEY,false );
    }









    public void graphV(){


        new Graph(this).getGraph()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listY -> {

                    DataPoint[] points=new DataPoint[listY.size()];
                    for (int i = 0; i <points.length ; i++) {
                        points[i]=new DataPoint(i,listY.get(i));
                    }


                    GraphView graph = (GraphView) findViewById(R.id.graph);

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


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_purse,menu);

        return super.onCreateOptionsMenu(menu);
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
    }

    @Override
    public EditText getEditTextNumber(){
        return editTextNumber=(EditText)findViewById(R.id.editTextAssets_sum);
    }

    @Override
    public EditText getEditTextTx() {
        return editTextTx=(EditText)findViewById(R.id.editTextAssets_text);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


        sPref=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed=sPref.edit();
        ed.putBoolean(START_KEY,STAT);
        ed.apply();
    }
}










