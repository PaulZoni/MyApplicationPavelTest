package s.hfad.com.myapplicationpaveltest;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import s.hfad.com.myapplicationpaveltest.fragment.BlankFragmentInformation;


public class BlankFragmentPager extends Fragment {


    public BlankFragmentPager() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_blank_fragment_pager, container, false);

        int position=getArguments().getInt(MainActivityTest.TAG_1);
        ViewPager mViewPager=(ViewPager)view.findViewById(R.id.view_pager);
        FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                return BlankFragmentInformation.newInstance(position);
            }

            @Override
            public int getCount() {
                return 15;
            }
        });

        mViewPager.setCurrentItem(position);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
































