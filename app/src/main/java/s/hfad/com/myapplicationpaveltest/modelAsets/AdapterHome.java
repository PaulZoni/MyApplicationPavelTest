package s.hfad.com.myapplicationpaveltest.modelAsets;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import s.hfad.com.myapplicationpaveltest.R;
import s.hfad.com.myapplicationpaveltest.fragment.BlankFragmentHome;


public class AdapterHome extends RecyclerView.Adapter<MenuViewHolder>  {

    private List<Menu>mMenus;
    private NewsHandler<MenuViewHolder> mNewsHandler;
    private Listener listener;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private View view;
    private MenuViewHolder menuViewHolder;

    public static interface Listener {
        public void onClick(int position);
    }

    public AdapterHome(Context context,List<Menu> menuList) {
        mMenus=menuList;
        mLayoutInflater=LayoutInflater.from(context);
        mContext=context;
        Handler handler=new Handler();
        mNewsHandler=new NewsHandler("w",handler);
        mNewsHandler.start();
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_view_home,parent,false);
        menuViewHolder=new MenuViewHolder(view);

        return menuViewHolder;
    }

    class NewsHandler<T> extends HandlerThread{
        private Handler mHandler;
        private Handler uiHandler;
        private static final int MESSAGE_DOWNLOAD = 0;
        private ConcurrentMap<T,Integer> mRequestMap = new ConcurrentHashMap<>();

        public NewsHandler(String name,Handler uiHandler) {
            super(name);
            this.uiHandler=uiHandler;
        }

        @Override
        protected void onLooperPrepared() {
            super.onLooperPrepared();
            mHandler=new Handler(getLooper()){


                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);

                    if (msg.what==MESSAGE_DOWNLOAD){
                        T target = (T) msg.obj;
                        ui(target);
                    }
                }
            };
        }

        public void ui(T target){

            uiHandler.post(() -> {
                MenuViewHolder holder= (MenuViewHolder) target;
                int position= mRequestMap.get(target);
                holder.textHomePage.setText(mMenus.get(position).text);
                Picasso.with(mContext).load(mMenus.get(position).photoId)
                        .placeholder(R.drawable.home_page_photo)
                        .error(R.drawable.home_page_photo)
                        .into(holder.imageHomePage);
            });
        }

        public void quay(T holder, int position){
            mRequestMap.put(holder,position);
            mHandler.obtainMessage(MESSAGE_DOWNLOAD,holder).sendToTarget();
        }

    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {

        CardView cardView=holder.cardView;
        mNewsHandler.quay(holder,position);

        cardView.setOnClickListener(view -> {

            if (listener!=null){
                listener.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMenus.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }
}










