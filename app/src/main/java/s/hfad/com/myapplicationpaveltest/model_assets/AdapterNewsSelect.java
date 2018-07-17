package s.hfad.com.myapplicationpaveltest.model_assets;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import java.util.List;
import s.hfad.com.myapplicationpaveltest.R;

public class AdapterNewsSelect extends RecyclerView.Adapter<AdapterNewsSelect.NewsButtonSelectHolder>{

    public interface listenerAdapterNewsSelect{
        public void onClick(int position);
    }
    private List<String>listNewsString;
    private View mView;
    private listenerAdapterNewsSelect mListenerAdapterNewsSelect;


    public void setListenerAdapterNewsSelect(listenerAdapterNewsSelect mListenerAdapterNewsSelect) {
        this.mListenerAdapterNewsSelect = mListenerAdapterNewsSelect;
    }


    public AdapterNewsSelect(List<String> listNewsString) {
        this.listNewsString = listNewsString;
    }

    @NonNull
    @Override
    public NewsButtonSelectHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        mView= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_button_select_news,parent,false);
        return new NewsButtonSelectHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsButtonSelectHolder holder, int position) {
        holder.mButton.setText(listNewsString.get(position));
        holder.mButton.setOnClickListener(v -> {

            if (mListenerAdapterNewsSelect != null){
                mListenerAdapterNewsSelect.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listNewsString.size();
    }

    public static class NewsButtonSelectHolder extends RecyclerView.ViewHolder{

        private Button mButton;

        public NewsButtonSelectHolder(View itemView) {
            super(itemView);
            mButton=itemView.findViewById(R.id.button_selectNews);
        }
    }
}

