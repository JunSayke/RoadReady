package com.example.roadready.fragments.buyer.applicationfragments;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roadready.R;
import com.example.roadready.classes.general.MainFacade;
import com.example.roadready.databinding.FragmentBuyerVehicleApplicationProgressBinding;

public class VehicleApplicationProgress_Fragment extends Fragment {

    private enum ProcessState {
        PENDING, SUCCESS;
    }
    private final String TAG = "VehicleApplicationProgress_Fragment";
    private FragmentBuyerVehicleApplicationProgressBinding binding;
    private MainFacade mainFacade;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBuyerVehicleApplicationProgressBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        try {
            mainFacade = MainFacade.getInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        initProgresses();
        return root;
    }

    private void initProgresses() {
        setProcessState(ProcessState.PENDING, binding.apContainerStatus1);
        setProcessState(ProcessState.PENDING, binding.apContainerStatus2);
        setProcessState(ProcessState.PENDING, binding.apContainerStatus3);
        setProcessState(ProcessState.PENDING, binding.apContainerStatus4);
    }

    private void startProcessTimer(long millisInFuture, long countDownInterval, LinearLayout container) {
        new CountDownTimer(millisInFuture, countDownInterval) {

            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                setProcessState(ProcessState.SUCCESS, container);
            }
        }.start();
    }

    private void setProcessState(ProcessState processState, LinearLayout container) {
        switch(processState) {
            case PENDING:
                setProcessPending(container);
                break;
            case SUCCESS:
                setProcessSuccessful(container);
                break;
        }
    }

    private void setProcessPending(LinearLayout container) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child instanceof RelativeLayout) {
                RelativeLayout rl = (RelativeLayout) child;
                rl.removeAllViews();
                ProgressBar pb = new ProgressBar(requireContext());
                pb.setIndeterminateTintList(ColorStateList.valueOf(Color.rgb(248, 31, 20)));
                rl.addView(pb);
            }
            container.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(248, 31, 20)));
        }
    }

    private void setProcessSuccessful(LinearLayout container) {
        for (int i = 0; i < container.getChildCount(); i++) {
            View child = container.getChildAt(i);
            if (child instanceof RelativeLayout) {
                RelativeLayout rl = (RelativeLayout) child;
                rl.removeAllViews();
                ImageView iv = new ImageView(requireContext());
                iv.setImageResource(R.drawable.check_mark);
                iv.setColorFilter(Color.rgb(32,244,14));
                rl.addView(iv);
            }
            container.setBackgroundTintList(ColorStateList.valueOf(Color.rgb(32, 244, 14)));
        }
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        startProcessTimer(1000, 1000, binding.apContainerStatus1);
        startProcessTimer(1500, 1000, binding.apContainerStatus2);
        startProcessTimer(2700, 1000, binding.apContainerStatus3);
        startProcessTimer(3800, 1000, binding.apContainerStatus4);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}