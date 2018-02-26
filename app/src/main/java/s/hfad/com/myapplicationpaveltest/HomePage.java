package s.hfad.com.myapplicationpaveltest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;



public class HomePage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                if (position==0){

                    Intent intent=new Intent(HomePage.this,MainActivityTest.class);
                    startActivity(intent);

                }else if (position==1){

                    Intent intent=new Intent(HomePage.this,MonetaryAssets.class);
                    startActivity(intent);
                }
            }
        };

        ListView listView=(ListView)findViewById(R.id.LIst_homePage);
        listView.setOnItemClickListener(itemClickListener);
    }



}
