package s.hfad.com.myapplicationpaveltest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.ArrayList;
import java.util.List;

import s.hfad.com.myapplicationpaveltest.modelAsets.Transaction;

public class MainActivityList extends Activity {

    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;
    private static List<Transaction>list=new ArrayList<>();
    private GoogleMap googleMap;

    public static void setList(List<Transaction> list) {
        MainActivityList.list = list;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        mRecyclerView=(RecyclerView)findViewById(R.id.recyclerView_mainList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateList();
    }


    private void createMapView(int position){
        String loc = list.get(position).getLocation();
        String[] words = loc.split("\\s");
        String[] symbol = words[1].split(",");
        String one = symbol[0] + "." + symbol[1];
        String two = symbol[2] + "." + symbol[3];
        double v = Double.parseDouble(one);
        double v1 =Double.parseDouble(two);
        try {
            if (null==googleMap){
                ((MapFragment)getFragmentManager().findFragmentById(R.id.map))
                        .getMapAsync(googleMap -> {
                            LatLng latLng = new LatLng(v,v1);
                            LatLngBounds latLngBounds = new LatLngBounds(latLng,latLng);

                            googleMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title("My location"));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLngBounds.getCenter(),18));
                        });
            }
        }catch (NullPointerException e){
            Log.e("Оштбка Nul", String.valueOf(e));
        }
    }


    public void updateList(){
        mListAdapter=new ListAdapter(list,this);
        mRecyclerView.setAdapter(mListAdapter);
    }


    private class ListHolder extends RecyclerView.ViewHolder{
        TextView mTextView_mainList;
        CardView mCardView;

        public ListHolder(View itemView) {
            super(itemView);
            mTextView_mainList=itemView.findViewById(R.id.textView_ActivityList);
            mCardView = itemView.findViewById(R.id.card_viewActivityList);
        }
    }


    private class ListAdapter extends RecyclerView.Adapter<ListHolder>{
        private List<Transaction>mStringList;
        private Context mContext;

        public ListAdapter(List<Transaction> stringList,Context context) {
            mStringList = stringList;
            this.mContext=context;
        }

        @Override
        public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(R.layout.layout_activity_lisr, parent, false);
            return new ListHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            CardView cardView = holder.mCardView;
            Transaction string=mStringList.get(position);
            holder.mTextView_mainList.setText(string.getSum()+ string.getComment()+string.getData());
            cardView.setOnClickListener(v -> createMapView(position));
        }

        @Override
        public int getItemCount() {
            return mStringList.size();
        }
    }
}









