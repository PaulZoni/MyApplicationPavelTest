package s.hfad.com.myapplicationpaveltest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;


import java.util.ArrayList;
import java.util.List;

import s.hfad.com.myapplicationpaveltest.Activity.Expenses;
import s.hfad.com.myapplicationpaveltest.modelAsets.AdapterHome;
import s.hfad.com.myapplicationpaveltest.modelAsets.Menu;


public class HomePage extends Activity {


    private List<Menu>mMenus;
    private AdapterHome adapterHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        initializableMenu();

        RecyclerView rv = (RecyclerView)findViewById(R.id.LIst_homePage);
        rv.setHasFixedSize(true);
        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        adapterHome=new AdapterHome(this,mMenus);
        rv.setAdapter(adapterHome);

        listnerAdapterMqnu();

    }


    private void initializableMenu(){
        mMenus=new ArrayList<>();
        mMenus.add(new Menu("Конвертер валют",R.drawable.home_page_photo));
        mMenus.add(new Menu("Рссходы",R.drawable.money_from_the_wallet));
        mMenus.add(new Menu("Доходы",R.drawable.purse));
    }

    public void listnerAdapterMqnu(){
        adapterHome.setListener(new AdapterHome.Listener() {
            @Override
            public void onClick(int position) {

                if (position==0){
                    Intent intent=new Intent(HomePage.this,MainActivityTest.class);
                    startActivity(intent);

                }else if (position==1){

                    Intent intent=new Intent(HomePage.this,MonetaryAssets.class);
                    startActivity(intent);

                }else if (position==2){

                    Intent intent=new Intent(HomePage.this,Expenses.class);
                    startActivity(intent);
                }
            }
        });
    }
}














