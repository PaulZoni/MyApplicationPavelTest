package s.hfad.com.myapplicationpaveltest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivityList extends Activity {

    private RecyclerView mRecyclerView;
    private ListAdapter mListAdapter;
    private static List<String>list=new ArrayList<>();

    public static void setList(List<String> list) {
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


    public void updateList(){


        mListAdapter=new ListAdapter(list,this);
        mRecyclerView.setAdapter(mListAdapter);
    }



    private class ListHolder extends RecyclerView.ViewHolder{

        TextView mTextView_mainList;

        public ListHolder(View itemView) {
            super(itemView);
            mTextView_mainList=(TextView)itemView;
        }
    }

    private class ListAdapter extends RecyclerView.Adapter<ListHolder>{


        private List<String>mStringList;
        private Context mContext;

        public ListAdapter(List<String> stringList,Context context) {
            mStringList = stringList;
            this.mContext=context;
        }



        @Override
        public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            View view = layoutInflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ListHolder(view);
        }

        @Override
        public void onBindViewHolder(ListHolder holder, int position) {
            String string=mStringList.get(position);
            holder.mTextView_mainList.setText(string);
        }

        @Override
        public int getItemCount() {
            return mStringList.size();
        }
    }
}









