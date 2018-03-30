package s.hfad.com.myapplicationpaveltest.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import s.hfad.com.myapplicationpaveltest.MainActivityTest;
import s.hfad.com.myapplicationpaveltest.MonetaryAssets;
import s.hfad.com.myapplicationpaveltest.R;
import s.hfad.com.myapplicationpaveltest.fragment.BlankFragmentHome;


public class HomePage extends FragmentActivity  {

    private BottomNavigationView mBottomNavigationView;



    public interface OnBackPressedListener {
        public void onBackPressed();
    }

    @Override
    public void onBackPressed() {

        FragmentManager fm = getSupportFragmentManager();
        OnBackPressedListener backPressedListener = null;
        for (Fragment fragment: fm.getFragments()) {
            if (fragment instanceof  OnBackPressedListener) {
                backPressedListener = (OnBackPressedListener) fragment;
                super.onBackPressed();
                break;
            }
        }

        if (backPressedListener != null) {
            backPressedListener.onBackPressed();
        } else {
            super.onBackPressed();
        }


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        loadingFragment();
        buttonNavigationLisner();

    }

    public void loadingFragment(){
        android.support.v4.app.FragmentManager manager=getSupportFragmentManager();
        Fragment fragment=manager.findFragmentById(R.id.homeContainer);

        fragment = new BlankFragmentHome();
        manager.beginTransaction()
                .add(R.id.homeContainer, fragment)
                .commit();


    }





    public void buttonNavigationLisner(){
        mBottomNavigationView=(BottomNavigationView)findViewById(R.id.bottom_navigation);
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            FragmentManager manager=getSupportFragmentManager();
            Fragment fragment=manager.findFragmentById(R.id.homeContainer);

            Intent intent;
            switch (item.getItemId()){

                case R.id.action_converter:

                    fragment = new MainActivityTest();
                    manager.beginTransaction()
                            .replace(R.id.homeContainer, fragment)
                            .commit();

                    break;
                case R.id.action_assets:
                    fragment = new MonetaryAssets();
                    manager.beginTransaction()
                            .replace(R.id.homeContainer, fragment)
                            .commit();


                    break;
                case R.id.action_expenses:
                    intent=new Intent(HomePage.this,Expenses.class);
                    startActivity(intent);
                    break;
            }
            return true;
        });

    }
}











