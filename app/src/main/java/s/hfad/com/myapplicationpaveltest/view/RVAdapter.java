package s.hfad.com.myapplicationpaveltest.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;
import s.hfad.com.myapplicationpaveltest.R;
import s.hfad.com.myapplicationpaveltest.model_assets.ValutaModel;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{

    private LayoutInflater inflater;
    private List<ValutaModel> persons;

    private Listener listener;


    public static interface Listener {
        public void onClick(int position);
    }


    public RVAdapter(Context context, List<ValutaModel> persons){
        this.persons = persons;
        this.inflater = LayoutInflater.from(context);
    }


    @NonNull
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_card_view, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, int position) {
        CardView cardView=personViewHolder.cv;
        personViewHolder.personName.setText(persons.get(position).getName());
        personViewHolder.personAge.setText(persons.get(position).getValue());
        personViewHolder.personPhoto.setImageResource(persons.get(position).getPhotoId());
        cardView.setOnClickListener(view -> {

            if (listener!=null){
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return persons.size();
    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personAge;
        ImageView personPhoto;
        PersonViewHolder(View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cv);
            personName = itemView.findViewById(R.id.person_name);
            personAge = itemView.findViewById(R.id.person_age);
            personPhoto = itemView.findViewById(R.id.person_photo);
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }
}