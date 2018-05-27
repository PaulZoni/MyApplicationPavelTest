package s.hfad.com.myapplicationpaveltest;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.HashMap;
import s.hfad.com.myapplicationpaveltest.fragment.BlankFragmentInformation;
import s.hfad.com.myapplicationpaveltest.modelAsets.LoadingGraf;


public class BlankFragmentPager extends Fragment {

    private ArrayList<Double> listX;
    private HashMap<String, ArrayList<Double>> valuta;
    private  int maxPageCount = 15;
    private int positions;
    private View view;
    public BlankFragmentPager() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_blank_fragment_pager, container, false);
        timeValueLoading();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true);
    }

    private ArrayList<Double> checkValue(int stats){

        if (stats==0){
            listX=valuta.get("USD");
            return listX;
        }else if (stats==1){
            listX=valuta.get("EUR");

        }else if (stats==2) {
            listX = valuta.get("CHF");

        }else if (stats==3) {
            listX = valuta.get("AUD");

        }else if (stats==4) {
            listX = valuta.get("AZN");

        }else if (stats==5) {
            listX = valuta.get("GBP");

        }else if (stats==6) {
            listX = valuta.get("AMD");

        }else if (stats==7) {
            listX = valuta.get("BYN");

        }else if (stats==8) {
            listX = valuta.get("BGN");

        }else if (stats==9) {
            listX = valuta.get("BRL");

        }else if (stats==10) {
            listX = valuta.get("HUF");

        }else if (stats==11) {
            listX = valuta.get("HKD");

        }else if (stats==12) {
            listX = valuta.get("DKK");

        }else if (stats==13) {
            listX = valuta.get("INR");

        }else if (stats==14) {
            listX = valuta.get("KZT");

        }
        return listX;
    }

    public void timeValueLoading() {
            valuta = LoadingGraf.getValuta();
            positions=getArguments().getInt(MainActivityTest.TAG_1);
            ViewPager mViewPager = view.findViewById(R.id.view_pager);
            FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
            mViewPager.setOffscreenPageLimit(1);

            mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
                @Override
                public Fragment getItem(int position) {
                    listX = checkValue(position);
                    return BlankFragmentInformation.newInstance(listX);

                }

                @Override
                public int getCount() {
                    return maxPageCount;
                }

            });

            mViewPager.setCurrentItem(positions,true);
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
































