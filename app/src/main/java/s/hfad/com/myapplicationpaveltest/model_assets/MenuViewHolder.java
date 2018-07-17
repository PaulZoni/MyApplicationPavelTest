package s.hfad.com.myapplicationpaveltest.model_assets;


import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import s.hfad.com.myapplicationpaveltest.R;

public class MenuViewHolder extends RecyclerView.ViewHolder {

      CardView cardView;
      ImageView imageHomePage;
      TextView textHomePage;
     private String url;


    public void setUrl(String url){
         this.url=url;
     }

    public String getUrl(){
         return url;
    }

    public MenuViewHolder(View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.card_view);
        imageHomePage = itemView.findViewById(R.id.imageHome_page);
        textHomePage = itemView.findViewById(R.id.textHome_page);
    }

}
