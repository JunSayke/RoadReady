//package com.example.roadready.classes.ui.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Button;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.roadready.R;
//import com.example.roadready.classes.model.gson.data.DealershipGson;
//
//import java.util.List;
//
//public class BuyerDealershipListRecyclerViewAdapter extends RecyclerView.Adapter<BuyerDealershipListRecyclerViewAdapter.ViewHolder> {
//    private final Context context;
//    private final List<DealershipGson> dealershipList;
//    private final OnItemClickListener onItemClickListener;
//
//    public BuyerDealershipListRecyclerViewAdapter(Context context, List<DealershipGson> dealershipList, OnItemClickListener listener) {
//        this.context = context;
//        this.dealershipList = dealershipList;
//        this.onItemClickListener = listener;
//    }
//
//    @NonNull
//    @Override
//    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listings_buyer_dealership, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        DealershipGson dealership = dealershipList.get(position);
//        // Set data to views here
//    }
//
//    @Override
//    public int getItemCount() {
//        return dealershipList.size();
//    }
//
//    public interface OnItemClickListener {
//        void onItemClick(String dealershipId);
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        private final ImageView dealershipImage;
//        private final TextView dealershipName;
//        private final Button selectButton;
//
//        public ViewHolder(@NonNull View itemView) {
//            super(itemView);
//            dealershipImage = itemView.findViewById(R.id.dealershipImage);
//            dealershipName = itemView.findViewById(R.id.dealershipName);
//            selectButton = itemView.findViewById(R.id.selectButton);
//            selectButton.setOnClickListener(v -> {
//                int position = getBindingAdapterPosition();
//                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
//                    onItemClickListener.onItemClick(dealershipList.get(position).getId());
//                }
//            });
//        }
//
//        public ImageView getDealershipImage() {
//            return dealershipImage;
//        }
//
//        public TextView getDealershipName() {
//            return dealershipName;
//        }
//
//        public Button getSelectButton() {
//            return selectButton;
//        }
//    }
//}