package com.urbler;
 /*Created by Alome on 2/16/2019.
 WeMet
 */


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static android.content.Context.MODE_PRIVATE;

public class modalAppReg extends BottomSheetDialogFragment {
    View view;
    Button con;
    ProgressBar prog;
    //  HorizontalCounter horizontalCounter;
    @SuppressLint({"RestrictedApi", "InflateParams"})
    public void setupDialog(final Dialog dialog, final int style) {
        super.setupDialog(dialog, style);
        view = LayoutInflater.from(getContext()).inflate(R.layout.reg_modal, null);
        dialog.setContentView(view);
        initViews();
        checkVal(500);
        con.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mStartActivity = new Intent(getContext(), MainActivity.class);
                int mPendingIntentId = 123456;
                PendingIntent mPendingIntent = PendingIntent.getActivity(getContext(), mPendingIntentId, mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                AlarmManager mgr = (AlarmManager)getContext().getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                System.exit(0);
            }
        });
        //    horizontalCounter.setDisplayingInteger(true);
        setCancelable(false);
        ((View) view.getParent()).getLayoutParams();
        BottomSheetBehavior.from(dialog.findViewById(R.id.design_bottom_sheet)).setPeekHeight(10000);
        ((View) view.getParent()).setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));


    }
    private void initViews() {
        con=view.findViewById(R.id.con);
        prog=view.findViewById(R.id.prog);
    }
    public void checkVal(int delay){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @SuppressLint("DefaultLocale")
            public void run()
            {
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("isReg", MODE_PRIVATE);
                String code = sharedPreferences.getString("val", null);
                SharedPreferences register=getContext().getSharedPreferences("isReg",MODE_PRIVATE);
                SharedPreferences.Editor editor=register.edit();
                if (code != null) {
                    if (!code.isEmpty()){
                        if (code.equalsIgnoreCase("Verified")) {
                            editor.putBoolean("reg",true);
                            editor.apply();
                            con.setVisibility(View.VISIBLE);
                            con.setEnabled(true);
                            prog.setVisibility(View.GONE);
                            //verified
                            // (getContext(),"Hipppppppppppppppy",Toast.LENGTH_LONG).show();

                        }
                    }
                    else{
                        Log.d("URBLER HANDLER ", "Currently: Waiting for Verifcation Code !!!!!!");
                    }
                }
                handler.postDelayed(this, delay);
                // Toast.makeText(MainActivity.this,"The string is "+" "+formatted,Toast.LENGTH_LONG).show();

            }
        }, delay);
    }
}

