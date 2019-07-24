package com.urbler;
 /*Created by Alome on 2/16/2019.
 WeMet
 */
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Build;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

import com.gildaswise.horizontalcounter.HorizontalCounter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.google.common.base.Preconditions.checkArgument;

public class modalNew extends BottomSheetDialogFragment {
    View view;
    TextView day;
    String type="";
    RadioGroup radio;
    RadioButton postpaid,prepaid;
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
        day.setText(getDayOfMonthSuffix(cDay)+" "+dayOfTheWeek+""+" , "+cYear+"\n"+getCurrentTimeUsingCalendar());
        // Toast.makeText(getContext(),dayOfTheWeek+ "  "+getDayOfMonthSuffix(cDay), Toast.LENGTH_SHORT).show();
        getType();
        horizontalCounter.setOnReleaseListener(() -> {
            Toast.makeText(getContext(), "Value updated to: " + horizontalCounter.getCurrentValue(), Toast.LENGTH_SHORT).show();
        });

    }

    private void initViews() {
        horizontalCounter=view.findViewById(R.id.horizontal_counter);
        day=view.findViewById(R.id.date);
        day.setSingleLine(false);
        radio=view.findViewById(R.id.type);
        postpaid=view.findViewById(R.id.postpaid);
        prepaid=view.findViewById(R.id.prepaid);
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
    public String getCurrentTimeUsingCalendar() {
        Calendar cal = Calendar.getInstance();
        Date date=cal.getTime();
        @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        String formattedDate=dateFormat.format(date);
      //  System.out.println("Current time of the day using Calendar - 24 hour format: "+ formattedDate);
        return formattedDate;
    }
    public void getType(){
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.type);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.postpaid:
                        type="PostPaid";
                        // do operations specific to this selection
                        Toast.makeText(getContext(),type,Toast.LENGTH_LONG).show();
                        break;
                    case R.id.prepaid:
                        type="Prepaid";
                        Toast.makeText(getContext(),type,Toast.LENGTH_LONG).show();
                        break;

                }
            }
        });

    }
    public  void sendSms(String phone) {
        SmsManager smsManager = SmsManager.getDefault();
        String sms = "Your Order is on the way, click on this link to download the Urbler Order tracker android app\n" +
                "https://play.google.com/store/apps/details?id=com.urbler.recepient" +"" +
                "To track your Order. \n" +
                "Urbler Team.";
        smsManager.sendTextMessage(phone, null, sms, null, null);
    }
    public void sendPush(){
    }
    public void allowTrack(int phone){
int p;
    }
}

