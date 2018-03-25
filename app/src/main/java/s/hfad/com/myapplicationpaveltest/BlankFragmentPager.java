package s.hfad.com.myapplicationpaveltest;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



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
                return 2;
            }
        });

        if (position==0){
            mViewPager.setCurrentItem(0);
        }else if (position==1){
            mViewPager.setCurrentItem(1);
        }
        return view;
    }

}
