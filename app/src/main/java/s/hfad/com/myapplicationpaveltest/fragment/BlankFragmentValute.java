package s.hfad.com.myapplicationpaveltest.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import s.hfad.com.myapplicationpaveltest.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragmentValute extends Fragment {


    public BlankFragmentValute() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank_fragment_valute, container, false);
    }

}
