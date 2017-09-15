package com.example.hayk.gametime.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hayk.gametime.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hayk on 13.06.2017.
 */

public class AdapterForRecycler extends RecyclerView.Adapter<AdapterForRecycler.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView icon,info;
        TextView name;
        AdapterForRecycler.ViewHolder.onViewHolderItemClickListener viewHolderItemClickListener;

        ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView)itemView.findViewById(R.id.game_item);
            info = (ImageView)itemView.findViewById(R.id.game_info);
            name = (TextView)itemView.findViewById(R.id.game_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(viewHolderItemClickListener != null){
                        viewHolderItemClickListener.onItemClicked(getAdapterPosition());
                    }
                }
            });
        }

        interface onViewHolderItemClickListener{
            void onItemClicked(int position);
        }

        void setOnViewHolderListener(AdapterForRecycler.ViewHolder.onViewHolderItemClickListener viewHolderListener){
            this.viewHolderItemClickListener = viewHolderListener;
        }
    }

    private List<GameItem> list;
    private Context context;
    private AdapterForRecycler.onAdapterItemClickListener adapterItemClickListener;

    public AdapterForRecycler(Context context){
        list = new ArrayList<>();
        this.context = context;
        GameItem gameItem1 = new GameItem(R.mipmap.bomb_icon,context.getString(R.string.bomb_game));
        GameItem gameItem2 = new GameItem(R.mipmap.pantomima_icon,context.getString(R.string.pantomima_game));
        GameItem gameItem3 = new GameItem(R.mipmap.sing_icon,context.getString(R.string.sing_game));
        GameItem gameItem4 = new GameItem(R.mipmap.film_icon,context.getString(R.string.film_game));
        GameItem gameItem5 = new GameItem(R.mipmap.chain_icon,context.getString(R.string.chain_game));
        list.add(gameItem1);
        list.add(gameItem2);
        list.add(gameItem3);
        list.add(gameItem4);
        list.add(gameItem5);
    }

    @Override
    public AdapterForRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_card,parent,false);
        return new AdapterForRecycler.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterForRecycler.ViewHolder holder, int position) {
        holder.setOnViewHolderListener(new AdapterForRecycler.ViewHolder.onViewHolderItemClickListener() {
            @Override
            public void onItemClicked(int position) {
                if(adapterItemClickListener != null){
                    adapterItemClickListener.onItemClicked(list.get(position));
                }
            }
        });
        Picasso.with(context).load(list.get(position).getmIcon()).into(holder.icon);
        holder.name.setText(list.get(position).getmName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface onAdapterItemClickListener{
        void onItemClicked(GameItem gameItem);
    }

    public void setOnAdapterListener(AdapterForRecycler.onAdapterItemClickListener adapterListener){
        this.adapterItemClickListener = adapterListener;
    }
}
