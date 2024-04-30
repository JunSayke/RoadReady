package com.example.roadready.classes.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadready.R;
import com.example.roadready.classes.model.gson.data.NotificationGson;
import com.example.roadready.classes.model.gson.data.VehicleGson;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class CommonNotificationListingsRecyclerViewAdapter extends RecyclerView.Adapter<CommonNotificationListingsRecyclerViewAdapter.ViewHolder> {
    private final Context context;
    private final List<NotificationGson> notificationGsonList;
    private final CommonNotificationListingsRecyclerViewAdapter.OnItemClickListener onItemClickListener;

    public CommonNotificationListingsRecyclerViewAdapter(Context context, List<NotificationGson> notificationGsonList, CommonNotificationListingsRecyclerViewAdapter.OnItemClickListener listener) {
        this.context = context;
        this.notificationGsonList = notificationGsonList;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public CommonNotificationListingsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listings_dealership_vehicle, parent, false);
        return new CommonNotificationListingsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonNotificationListingsRecyclerViewAdapter.ViewHolder holder, int position) {
        NotificationGson model = notificationGsonList.get(position);
    }

    @Override
    public int getItemCount() {
        return notificationGsonList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String itemId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView txtContent;
        private final TextView txtDate;
        private final ImageButton btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtContent = itemView.findViewById(R.id.nTxtContent);
            txtDate = itemView.findViewById(R.id.nTxtDate);
            btnDelete = itemView.findViewById(R.id.nBtnDelete);
            btnDelete.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(notificationGsonList.get(position).getId());
                }
            });
        }

        //GETTERS
        public TextView getTxtContent(){
            return txtContent;
        }
        public TextView getTxtDate(){
            return txtDate;
        }
        public ImageButton getBtnDelete(){
            return btnDelete;
        }
    }
}

