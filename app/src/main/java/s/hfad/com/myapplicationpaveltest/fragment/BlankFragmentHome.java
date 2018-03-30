package s.hfad.com.myapplicationpaveltest.fragment;



import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import s.hfad.com.myapplicationpaveltest.R;
import s.hfad.com.myapplicationpaveltest.modelAsets.AdapterHome;
import s.hfad.com.myapplicationpaveltest.modelAsets.Menu;


public class BlankFragmentHome extends Fragment {

    private List<Menu> mMenus;
    private AdapterHome adapterHome;
    public BlankFragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_blank_fragment_home, container, false);

        initializableMenu();
        RecyclerView rv = (RecyclerView)view.findViewById(R.id.LIst_homePage);
        rv.setHasFixedSize(true);
        StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        rv.setLayoutManager(llm);
        adapterHome=new AdapterHome(getActivity(),mMenus);
        rv.setAdapter(adapterHome);

        listnerAdapterMqnu();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void initializableMenu(){
        mMenus=new ArrayList<>();
        mMenus.add(new Menu("Конвертер валют",R.drawable.home_page_photo));
        mMenus.add(new Menu("Рссходы",R.drawable.money_from_the_wallet));
        mMenus.add(new Menu("Доходы",R.drawable.purse));
    }

    public void listnerAdapterMqnu(){
        adapterHome.setListener(position -> {

            if (position==0){
               //?

            }else if (position==1){

                //?

            }else if (position==2){
                //?

            }
        });
    }
}
