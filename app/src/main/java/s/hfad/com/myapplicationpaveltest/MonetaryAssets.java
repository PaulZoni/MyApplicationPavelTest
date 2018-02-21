package s.hfad.com.myapplicationpaveltest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


public class MonetaryAssets extends Activity implements IViewPurse,View.OnClickListener {

    private MainPresenterPurse presenter;

    private Button buttonOk;
    private Button buttonAll;
    protected EditText editTextNumber;
    protected EditText editTextTx;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monetary_assets);

        if (presenter==null){
            presenter=new MainPresenterPurse(this);
        }
        buttonOk=(Button)findViewById(R.id.buttonOkAssets);
        buttonOk.setOnClickListener(this);
        buttonAll=(Button)findViewById(R.id.buttonAllTrAssets);
        buttonAll.setOnClickListener(this);

        graphV();

    }


    public void graphV(){
        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)
        });

        graph.addSeries(series);
    }


    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_purse,menu);

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();
        switch (id){
            case R.id.action_settings:
                //???
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

}










