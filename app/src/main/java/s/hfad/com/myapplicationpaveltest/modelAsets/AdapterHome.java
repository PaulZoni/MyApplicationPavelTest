package s.hfad.com.myapplicationpaveltest.modelAsets;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import s.hfad.com.myapplicationpaveltest.R;


public class AdapterHome extends RecyclerView.Adapter<AdapterHome.MenuViewHolder>  {

    private List<Menu>mMenus;

    private Listener listener;
    LayoutInflater mLayoutInflater;

    public static interface Listener {
        public void onClick(int position);
    }

    public AdapterHome(Context context,List<Menu> menuList) {
        mMenus=menuList;
        mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_view_home,parent,false);
        MenuViewHolder menuViewHolder=new MenuViewHolder(view);
        return menuViewHolder;
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {

        CardView cardView=holder.cardView;
        holder.textHomePage.setText(mMenus.get(position).text);
        holder.imageHomePage.setImageResource(mMenus.get(position).photoId);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (listener!=null){
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMenus.size();
    }


    public static class MenuViewHolder extends RecyclerView.ViewHolder{

        private CardView cardView;
        private ImageView imageHomePage;
        private TextView textHomePage;

        public MenuViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.card_view);
            imageHomePage=(ImageView)itemView.findViewById(R.id.imageHome_page);
            textHomePage=(TextView)itemView.findViewById(R.id.textHome_page);

        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }
}










