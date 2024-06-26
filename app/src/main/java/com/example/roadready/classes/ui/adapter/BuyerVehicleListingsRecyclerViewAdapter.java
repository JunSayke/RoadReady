package com.example.roadready.classes.ui.adapter;

import static com.example.roadready.classes.util.LocationTool.haversineDistance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadready.R;
import com.example.roadready.classes.model.gson.data.VehicleGson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BuyerVehicleListingsRecyclerViewAdapter extends RecyclerView.Adapter<BuyerVehicleListingsRecyclerViewAdapter.ViewHolder> {
    private final Context context;
    private final List<VehicleGson> vehicleGsonList;
    private final OnItemClickListener onItemClickListener;
    private final Location currentLocation;

    public BuyerVehicleListingsRecyclerViewAdapter(Context context, Location currentLocation, List<VehicleGson> vehicleGsonList, OnItemClickListener listener) {
        this.context = context;
        this.currentLocation = currentLocation;
        this.vehicleGsonList = vehicleGsonList;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listings_buyer_vehicle, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        VehicleGson model = vehicleGsonList.get(position);
        Picasso.get().load(model.getImageUrl()).into(holder.getVehicleImage());
        holder.getVehicleName().setText(model.getModelAndName());
//        float[] results = new float[1];
//        Location.distanceBetween(10.303841817089438, 123.88096691447505, model.getDealershipGson().getLatitude(), model.getDealershipGson().getLongitude(), results);
//        float distanceInMeters = results[0];
//        float distanceInKm = distanceInMeters / 1000;

        String description = "";
        if (currentLocation != null) {
            double distanceInKm = haversineDistance(currentLocation.getLatitude(), currentLocation.getLongitude(), model.getDealershipGson().getLatitude(), model.getDealershipGson().getLongitude());
            description = model.getDealershipGson().getName() + " (" + String.format("%.2f", distanceInKm) + " km away)";
        } else {
            description = model.getDealershipGson().getName()  + " (location not available)";
        }
        holder.getVehicleDesc().setText(description);
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
        private final TextView vehicleDesc;
        private final Button btnSelect;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vehicleImage = itemView.findViewById(R.id.bhVehicleImage);
            vehicleName = itemView.findViewById(R.id.bhVehicleName);
            vehicleDesc = itemView.findViewById(R.id.bhVehicleDesc);
            btnSelect = itemView.findViewById(R.id.bhBtnSelect);
            btnSelect.setOnClickListener(v -> {
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

        public TextView getVehicleDesc() {
            return vehicleDesc;
        }

        public Button getBtnSelect() {
            return btnSelect;
        }
    }
}
