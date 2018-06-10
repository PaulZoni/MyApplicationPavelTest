package s.hfad.com.myapplicationpaveltest;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import s.hfad.com.myapplicationpaveltest.MyEnum.languageEnum;
import s.hfad.com.myapplicationpaveltest.fragment.BlankFragmentHome;
import s.hfad.com.myapplicationpaveltest.fragment.Expenses;
import s.hfad.com.myapplicationpaveltest.fragment.MonetaryAssets;
import s.hfad.com.myapplicationpaveltest.modelAsets.Sound;


@SuppressLint("ParcelCreator")
public class HomePage extends AppCompatActivity implements BlankFragmentHome.DialogSelectLanguage,
        Parcelable {

    private static final int PERMISSION_REQUEST_CODE = 123;
    private BottomNavigationView mBottomNavigationView;
    private static final int MY_PERMISSION_ACCESS_COARSE_LOCATION = 11;
    private int STAT_CHECK_ITEM=0;
    private final String KEY_ITEM = "key item";
    public final static String KEY_BUNDLE_HOME = "key bundle home";
    public static String STAT_LANGUAGE;
    private SharedPreferences mPreferences;
    private Bundle bundle;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

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
        bundle = new Bundle();
        loadState();
        Thread soundThread=new Thread(()-> new Sound(this));
        soundThread.start();
        loadingFragmentHome();
        buttonNavigationListener();
    }

    @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
   }


    @Override
    protected void onStart() {
        super.onStart();
        checkPermission();
    }


    private void checkPermission(){
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions( this,
                    new String[] { android.Manifest.permission.ACCESS_FINE_LOCATION  },
                    MY_PERMISSION_ACCESS_COARSE_LOCATION );

        }else if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ){

            ActivityCompat.requestPermissions( this,
                    new String[] { android.Manifest.permission.ACCESS_COARSE_LOCATION  },
                    MY_PERMISSION_ACCESS_COARSE_LOCATION );
        }
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS);
        if (permissionStatus != PackageManager.PERMISSION_GRANTED)
         ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_SMS},PERMISSION_REQUEST_CODE);

        int permissionStatusBackground = ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_BOOT_COMPLETED);
        if (permissionStatusBackground != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECEIVE_BOOT_COMPLETED},PERMISSION_REQUEST_CODE);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Sound.release();
    }


    public void loadingFragmentHome(){
        android.support.v4.app.FragmentManager manager=getSupportFragmentManager();
        Fragment fragment=manager.findFragmentById(R.id.homeContainer);
        fragment = new BlankFragmentHome();
        bundle.putParcelable(KEY_BUNDLE_HOME,this);
        fragment.setArguments(bundle);
        manager.beginTransaction()
                .add(R.id.homeContainer, fragment)
                .commit();
    }


    public void buttonNavigationListener(){
        mBottomNavigationView = findViewById(R.id.bottom_navigation);
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
                case R.id.action_news:
                    fragment = new BlankFragmentHome();
                    bundle.putParcelable(KEY_BUNDLE_HOME,this);
                    fragment.setArguments(bundle);
                    manager.beginTransaction()
                            .replace(R.id.homeContainer, fragment)
                            .commit();
                    break;
            }
            return true;
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        return new AlertDialog.Builder(this)
                .setTitle(R.string.choiceLanguage)
                .setCancelable(false)
                .setSingleChoiceItems(R.array.targetLanguageArray, STAT_CHECK_ITEM,(dialog, which) -> {
                    STAT_CHECK_ITEM = which;
                    saveState();
                    switch (which){
                        case 0:
                            STAT_LANGUAGE = languageEnum.RUS.getDescription();

                            break;
                        case 1:
                            STAT_LANGUAGE = languageEnum.USA.getDescription();
                            break;
                        case 2:
                            STAT_LANGUAGE = languageEnum.FRA.getDescription();
                            break;
                    }
                    dialog.cancel();
                }).create();
    }

    @Override
    public void createDialogSelectLanguage() {
        showDialog(0);
    }

    @Override
    public String getStatLanguage() {
        return STAT_LANGUAGE;
    }

    private void saveState(){
        mPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putInt(KEY_ITEM,STAT_CHECK_ITEM);
        editor.apply();
    }

    private void loadState(){
        mPreferences = getPreferences(MODE_PRIVATE);
        STAT_CHECK_ITEM = mPreferences.getInt(KEY_ITEM,0);
    }
}



















