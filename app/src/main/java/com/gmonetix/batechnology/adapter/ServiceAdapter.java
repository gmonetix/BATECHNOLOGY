package com.gmonetix.batechnology.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.gmonetix.batechnology.R;
import com.gmonetix.batechnology.SendQuoteActivity;
import com.gmonetix.batechnology.helper.Const;
import com.gmonetix.batechnology.helper.Utils;
import com.gmonetix.batechnology.model.Service;
import com.gmonetix.batechnology.service.ServiceClickedActivity;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder>{

    private Context mContext;
    private List<Service> serviceList;
    private int pos;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, count;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);

            title = (TextView) view.findViewById(R.id.service_title);
            count = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.service_thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    Toast.makeText(mContext,serviceList.get(pos).getName(),Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    public ServiceAdapter(Context mContext, List<Service> serviceList) {
        this.mContext = mContext;
        this.serviceList = serviceList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Service service = serviceList.get(position);
        holder.title.setText(service.getName());
        holder.count.setText(service.getSummary());

        Utils.setFont(mContext,holder.title);
        Utils.setFont(mContext,holder.count);
        // loading album cover using Glide library
        Glide.with(mContext).load(service.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
                pos = position;
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ServiceClickedActivity.class);
                intent.putExtra(Const.INTENT_SERVICE_TITLE,serviceList.get(position).getName());
                mContext.startActivity(intent);
            }
        });

    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_service, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.service_menu_rqst_a_quote:
                    Intent intent = new Intent(mContext, SendQuoteActivity.class);
                    intent.putExtra(Const.INTENT_SERVICE_TITLE,serviceList.get(pos).getName());
                    mContext.startActivity(intent);
                    return true;
                case R.id.service_menu_open_in_browser:
                    Utils.openInBrowser(mContext,serviceList.get(pos).getLink());
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

}
