package s.hfad.com.myapplicationpaveltest;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;


public class HomePage extends AppCompatActivity {

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
                    fragment = new Expenses();
                    manager.beginTransaction()
                            .replace(R.id.homeContainer, fragment)
                            .commit();

                    break;
            }
            return true;
        });

    }
}











