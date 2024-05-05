package com.example.roadready.classes.general;

import android.os.CountDownTimer;
import android.widget.TextView;

public class VerificationTimer {

    private static VerificationTimer instance;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;

    // Private constructor to prevent direct instantiation
    private VerificationTimer() {
        // Initialize your timer here
    }

    // Get the singleton instance
    public static VerificationTimer getInstance() {
        if (instance == null) {
            instance = new VerificationTimer();
        }
        return instance;
    }



    // Start the timer
    public void startTimer(long millisInFuture, long countDownInterval, TextView container) {
        container.setEnabled(false);
        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                String time = "Cannot resend on cooldown " + millisUntilFinished / countDownInterval + " sec";
                container.setText(time);
            }

            @Override
            public void onFinish() {
                isTimerRunning = false;
                container.setClickable(true);
            }
        };
        countDownTimer.start();
        isTimerRunning = true;
    }

    public boolean isTimerRunning() {
        return isTimerRunning;
    }

    // Stop the timer (if needed)
    public void stopTimer() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
