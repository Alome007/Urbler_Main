package com.urbler;
 /*Created by Alome on 2/16/2019.
 WeMet
 */
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.gildaswise.horizontalcounter.HorizontalCounter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
public class loader extends BottomSheetDialogFragment {
    View view;
   // HorizontalCounter horizontalCounter;
    @SuppressLint({"RestrictedApi", "InflateParams"})
    public void setupDialog(final Dialog dialog, final int style) {
        super.setupDialog(dialog, style);
        view= LayoutInflater.from(getContext()).inflate(R.layout.loader, null);
        dialog.setContentView(view);
        initViews();
     //   horizontalCounter.setDisplayingInteger(true);
        setCancelable(true);
        ((View) view.getParent()).getLayoutParams();
        BottomSheetBehavior.from(dialog.findViewById(R.id.design_bottom_sheet)).setPeekHeight(10000);
        ((View) view.getParent()).setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));

    }

    private void initViews() {
       // horizontalCounter=view.findViewById(R.id.horizontal_counter);

    }
}

