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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import s.hfad.com.myapplicationpaveltest.Parser.ParsingNewsRetrofit;
import s.hfad.com.myapplicationpaveltest.R;
import s.hfad.com.myapplicationpaveltest.modelAsets.AdapterHome;
import s.hfad.com.myapplicationpaveltest.modelAsets.Menu;

public class BlankFragmentHome extends Fragment {


    private AdapterHome adapterHome;

    private View view;

    public BlankFragmentHome() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_blank_fragment_home, container, false);


        initializationMenu();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    private void initializationMenu(){

        new ParsingNewsRetrofit().getParser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(parser->{
                    List<Menu> mMenus=new ArrayList<>();

                    for (int i = 0; i <parser.size() ; i++) {
                        mMenus.add(new Menu(parser.get(i).getDescription(),parser.get(i).getUrlToImage()));
                    }

                    RecyclerView rv = (RecyclerView)view.findViewById(R.id.LIst_homePage);
                    rv.setHasFixedSize(true);
                    StaggeredGridLayoutManager llm = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
                    rv.setLayoutManager(llm);
                    adapterHome=new AdapterHome(getActivity(),mMenus);
                    rv.setAdapter(adapterHome);
                });

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
