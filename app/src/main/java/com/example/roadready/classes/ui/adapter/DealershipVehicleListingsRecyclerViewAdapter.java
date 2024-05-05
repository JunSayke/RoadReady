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
import com.example.roadready.classes.model.gson.data.VehicleGson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DealershipVehicleListingsRecyclerViewAdapter extends RecyclerView.Adapter<DealershipVehicleListingsRecyclerViewAdapter.ViewHolder> {
    private final Context context;
    private final List<VehicleGson> vehicleGsonList;
    private final DealershipVehicleListingsRecyclerViewAdapter.OnItemClickListener onItemClickListener;

    public DealershipVehicleListingsRecyclerViewAdapter(Context context, List<VehicleGson> vehicleGsonList, DealershipVehicleListingsRecyclerViewAdapter.OnItemClickListener listener) {
        this.context = context;
        this.vehicleGsonList = vehicleGsonList;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public DealershipVehicleListingsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listings_dealership_vehicle, parent, false);
        return new DealershipVehicleListingsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DealershipVehicleListingsRecyclerViewAdapter.ViewHolder holder, int position) {
        VehicleGson model = vehicleGsonList.get(position);
        Picasso.get().load(model.getImageUrl()).into(holder.getVehicleImage());
        holder.getVehicleName().setText(model.getModelAndName());
    }

    @Override
    public int getItemCount() {
        return vehicleGsonList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String itemId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView vehicleImage;
        private final TextView vehicleName;
        private final ImageButton btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vehicleImage = itemView.findViewById(R.id.bhVehicleImage);
            vehicleName = itemView.findViewById(R.id.bhVehicleName);
            btnDelete = itemView.findViewById(R.id.bhBtnDelete);
            btnDelete.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(vehicleGsonList.get(position).getId());
                }
            });
        }

        public ImageView getVehicleImage() {
            return vehicleImage;
        }

        public TextView getVehicleName() {
            return vehicleName;
        }

        public ImageButton getBtnDelete() {
            return btnDelete;
        }
    }
}
