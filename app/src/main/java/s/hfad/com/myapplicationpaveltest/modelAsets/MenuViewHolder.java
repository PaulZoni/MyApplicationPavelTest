package s.hfad.com.myapplicationpaveltest.modelAsets;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import s.hfad.com.myapplicationpaveltest.R;

public class MenuViewHolder extends RecyclerView.ViewHolder{

     CardView cardView;
     ImageView imageHomePage;
     TextView textHomePage;


    public MenuViewHolder(View itemView) {
        super(itemView);

        cardView=(CardView)itemView.findViewById(R.id.card_view);
        imageHomePage=(ImageView)itemView.findViewById(R.id.imageHome_page);
        textHomePage=(TextView)itemView.findViewById(R.id.textHome_page);
    }
}
