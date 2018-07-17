package s.hfad.com.myapplicationpaveltest.model_assets;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Picasso;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import s.hfad.com.myapplicationpaveltest.R;


public class AdapterHome extends RecyclerView.Adapter<MenuViewHolder>  {

    private List<Menu>mMenus;
    private NewsHandler<MenuViewHolder> mNewsHandler;
    private Listener listener;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private View view;
    private MenuViewHolder menuViewHolder;
    private final String ERROR_NULL = "ERROR_NULL";

    public static interface Listener {
        public void onClick(int position,String url);
    }

    public AdapterHome(Context context,List<Menu> menuList) {
        mMenus=menuList;
        mLayoutInflater=LayoutInflater.from(context);
        mContext=context;
        Handler handler=new Handler();
        mNewsHandler=new NewsHandler("w",handler);
        mNewsHandler.start();
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card_view_home,parent,false);
        menuViewHolder=new MenuViewHolder(view);

        return menuViewHolder;
    }

    class NewsHandler<T> extends HandlerThread{
        private Handler mHandler;
        private Handler uiHandler;
        private static final int MESSAGE_DOWNLOAD = 0;
        private ConcurrentMap<T,Integer> mRequestMap = new ConcurrentHashMap<>();

        NewsHandler(String name, Handler uiHandler) {
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

        void ui(T target){
            uiHandler.post(() -> {
                MenuViewHolder holder= (MenuViewHolder) target;
                int position= mRequestMap.get(target);
                holder.textHomePage.setText(mMenus.get(position).getText());

                Picasso.with(mContext).load(mMenus.get(position).getPhotoId())
                        .placeholder(R.drawable.home_page_photo)
                        .error(R.drawable.home_page_photo)
                        .into(holder.imageHomePage);

            });
        }

        void quay(T holder, int position){
            mRequestMap.put(holder,position);
            try {
                mHandler.obtainMessage(MESSAGE_DOWNLOAD,holder).sendToTarget();
            }catch (NullPointerException e){
                Log.e(ERROR_NULL, String.valueOf(e));
            }

        }
    }

    @SuppressLint("HandlerLeak")
    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu menu=mMenus.get(position);
        holder.setUrl(menu.getUrl());

        CardView cardView=holder.cardView;
        mNewsHandler.quay(holder,position);

        cardView.setOnClickListener(view -> {
            if (listener!=null){
                listener.onClick(position,holder.getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMenus.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void setListener(Listener listener){
        this.listener = listener;
    }
}

