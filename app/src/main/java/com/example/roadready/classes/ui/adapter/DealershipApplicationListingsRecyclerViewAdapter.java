package com.example.roadready.classes.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roadready.R;
import com.example.roadready.classes.model.gson.data.ApplicationGson;

import java.util.List;

public class DealershipApplicationListingsRecyclerViewAdapter extends RecyclerView.Adapter<DealershipApplicationListingsRecyclerViewAdapter.ViewHolder> {
    private final Context context;
    private final List<ApplicationGson> applicationsGsonList;
    private final OnItemClickListener onItemClickListener;

    public DealershipApplicationListingsRecyclerViewAdapter(Context context, List<ApplicationGson> applicationsGsonList, OnItemClickListener listener) {
        this.context = context;
        this.applicationsGsonList = applicationsGsonList;
        this.onItemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listings_dealership_application, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ApplicationGson model = applicationsGsonList.get(position);
        String name = model.getFirstName() + " " + model.getLastName();

        holder.getName().setText(name);
        holder.getApplicationId().setText(String.valueOf(position + 1));
        holder.getModeOfPayment().setText(getFormatModeOfPayment(model.getApplicationType()));
    }

    String getFormatModeOfPayment(String mop){
        if(mop == null) return "Null";
        else if(mop.equals("cash")) return "Cash";
        else if(mop.equals("inhouseFinance")) return "In-House Finance";
        else if(mop.equals("bankLoan(dealershipBankChoice)")) return "Bank Loan (Dealership's Bank Choice)";
        else if(mop.equals("bankLoan(buyerBankChoice)")) return "Bank Loan (Your Bank Choice)";
        else return "Undefined";
    }

    @Override
    public int getItemCount() {
        return applicationsGsonList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String itemId);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView applicationId;
        private final TextView modeOfPayment;
        private final Button btnSelect;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.dealershipName);
            applicationId = itemView.findViewById(R.id.dealershipApplicationId);
            modeOfPayment = itemView.findViewById(R.id.dealershipModeOfPayment);
            btnSelect = itemView.findViewById(R.id.dealershipBtnSelect);
            btnSelect.setOnClickListener(v -> {
                int position = getBindingAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onItemClickListener != null) {
                    onItemClickListener.onItemClick(applicationsGsonList.get(position).getId());
                }
            });
        }

        public TextView getName() {
            return name;
        }
        public TextView getApplicationId() {
            return applicationId;
        }

        public TextView getModeOfPayment() {
            return modeOfPayment;
        }

        public Button getBtnSelect() {
            return btnSelect;
        }
    }
}

