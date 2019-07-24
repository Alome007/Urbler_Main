package com.urbler;
 /*Created by Alome on 2/16/2019.
 WeMet
 */
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.gildaswise.horizontalcounter.HorizontalCounter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;

public class modalNew extends BottomSheetDialogFragment {
    View view;
    TextView day;
    HorizontalCounter horizontalCounter;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"RestrictedApi", "InflateParams", "SetTextI18n"})
    public void setupDialog(final Dialog dialog, final int style) {
        super.setupDialog(dialog, style);
        view= LayoutInflater.from(getContext()).inflate(R.layout.modal_app, null);
        dialog.setContentView(view);
        initViews();
        horizontalCounter.setDisplayingInteger(true);
        setCancelable(true);
        ((View) view.getParent()).getLayoutParams();
        BottomSheetBehavior.from(dialog.findViewById(R.id.design_bottom_sheet)).setPeekHeight(10000);
        ((View) view.getParent()).setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);
        Calendar calander = Calendar.getInstance();
        int cDay = calander.get(Calendar.DAY_OF_MONTH);
        int cYear=calander.get(Calendar.YEAR);
        day.setText(getDayOfMonthSuffix(cDay)+" "+dayOfTheWeek+""+" , "+cYear);
        // Toast.makeText(getContext(),dayOfTheWeek+ "  "+getDayOfMonthSuffix(cDay), Toast.LENGTH_SHORT).show();

    }

    private void initViews() {
        horizontalCounter=view.findViewById(R.id.horizontal_counter);
        day=view.findViewById(R.id.date);
        day.setSingleLine(false);
    }
    String getDayOfMonthSuffix(final int n) {
        checkArgument(n >= 1 && n <= 31, "illegal day of month: " + n);
        if (n >= 11 && n <= 13) {
            return n+ "th";
        }
        switch (n % 10) {
            case 1:  return n +"st";
            case 2:  return n+ "nd";
            case 3:  return n+"rd";
            default: return n+ "th";
        }
    }
}

