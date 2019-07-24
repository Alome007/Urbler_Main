package com.urbler;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import moe.feng.common.stepperview.VerticalStepperItemView;
public class register extends AppCompatActivity {
FirebaseUser firebaseUser;
String UserId;
EditText fN,sN,doB,country,state,city,cAdd;
AwesomeValidation awesomeValidation;
String c,st,ct,cA;
String f,s,d;
DatabaseReference dbRef;
loader loader = new loader();
private VerticalStepperItemView[] mSteppers = new VerticalStepperItemView[3];
private Button mNextBtn0, mNextBtn1, mPrevBtn1, mNextBtn2, mPrevBtn2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkReg()) {
            Log.i("TAG", "onCreate: Registered");
            Toast.makeText(register.this,"Hello",Toast.LENGTH_LONG).show();
            modalAppReg fragment = new modalAppReg();
            fragment.show(getSupportFragmentManager(), "Active: modalAppReg");
            setContentView(R.layout.empty);

        } else {

        }
        {
            setContentView(R.layout.register);
            initViews();
            //todo id..

            dbRef= FirebaseDatabase.getInstance().getReference().child("Riders").child(UserId).child("profile");
            mNextBtn0.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                                    f= fN.getText().toString();
                                    s= sN.getText().toString();
                                    d= doB.getText().toString();
                    if (!f.isEmpty()&&!s.isEmpty()&&!d.isEmpty()) {
                        mSteppers[0].setErrorText(null);
                    //    Toast.makeText(register.this, "Am not empty", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        mSteppers[0].setErrorText("All Fields are Required.");
                    }
                    mSteppers[0].nextStep();
                }

            });
            mPrevBtn1.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {

                    mSteppers[1].prevStep();

                }

            });
            mNextBtn1.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {
                    c=country.getText().toString();
                                    st=state.getText().toString();
                                    ct=city.getText().toString();
                                    cA=cAdd.getText().toString();
                    if (!c.isEmpty()&&!st.isEmpty()&&!ct.isEmpty()&&!cA.isEmpty()) {
                                        mSteppers[1].setErrorText(null);
                                    } else {
//                                            mSteppers[0].setErrorText(null);
                                        mSteppers[1].setErrorText("All Fields are Required.");
                    }
                    mSteppers[1].nextStep();

                }

            });
            mPrevBtn2.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {
                    mSteppers[2].prevStep();

                }

            });
            mNextBtn2.setOnClickListener(new View.OnClickListener() {

                @Override

                public void onClick(View view) {
                          addValidationToViews();
                                if (awesomeValidation.validate()) {
                                    startRegProcess();
                                    loader.show(getSupportFragmentManager(), "Loader: Active");
                                }
                                else
                                {
                                    fN.setError(null);
                                    sN.setError(null);
                                    doB.setError(null);
                                    country.setError(null);
                                    state.setError(null);
                                    city.setError(null);
                                    cAdd.setError(null);
                                    Snackbar.make(view, "Please fill Appropriately.", Snackbar.LENGTH_LONG).show();
                               }
                }

            });
        }
    }
    private void startRegProcess() {
regPojo regPojo=new regPojo(ct,c,d,f,s,cA,"",st);
dbRef.setValue(regPojo).addOnCompleteListener(new OnCompleteListener<Void>() {
    @Override
    public void onComplete(@NonNull Task<Void> task) {
        SharedPreferences register=getSharedPreferences("isReg",MODE_PRIVATE);
        SharedPreferences.Editor editor=register.edit();
        //editor.putBoolean("reg",true);
        editor.putBoolean("code",false);
        editor.putString("val","lol");
       editor.apply();
        modalAppReg fragment = new modalAppReg();
       fragment.show(getSupportFragmentManager(), "Active: modalAppReg");
    }
}).addOnFailureListener(new OnFailureListener() {
    @Override
    public void onFailure(@NonNull Exception e) {
        Toast.makeText(register.this,"Failed to register"+e,Toast.LENGTH_LONG).show();
    }
});
    }
    private void initViews() {
        mSteppers[0] = findViewById(R.id.stepper_0);
        mSteppers[1] =findViewById(R.id.stepper_1);
        mSteppers[2] =findViewById(R.id.stepper_2);
        mNextBtn0=findViewById(R.id.next1);
        VerticalStepperItemView.bindSteppers(mSteppers);
        mPrevBtn1=findViewById(R.id.button_prev_1);
        mNextBtn1=findViewById(R.id.button_next_1);
        mPrevBtn2 = findViewById(R.id.button_prev_2);
        mNextBtn2 =findViewById(R.id.button_next_2);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            UserId = firebaseUser.getUid();
        }
        Log.i("FIREBASE", "initViews: ID"+UserId);
        fN=findViewById(R.id.fn);
        sN=findViewById(R.id.sn);
        doB=findViewById(R.id.dB);
        country=findViewById(R.id.country);
        state=findViewById(R.id.state);
        city=findViewById(R.id.city);
        cAdd=findViewById(R.id.contactAddress);
    }
    public boolean checkReg(){
        boolean isReg=false;
        SharedPreferences register=getSharedPreferences("isReg",MODE_PRIVATE);
        boolean code=register.getBoolean("code",false);
        boolean reg=register.getBoolean("reg",false);
        //todo put true. in modalAppREg after code verifi..
        if (code&&reg){
            isReg=true;
        }
        return isReg;
    }
    private void addValidationToViews() {
        awesomeValidation=new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this, R.id.fn, RegexTemplate.NOT_EMPTY, R.string.required);
        awesomeValidation.addValidation(this, R.id.sn, RegexTemplate.NOT_EMPTY, R.string.required);
        awesomeValidation.addValidation(this, R.id.dB, RegexTemplate.NOT_EMPTY, R.string.required);
        awesomeValidation.addValidation(this, R.id.country, RegexTemplate.NOT_EMPTY, R.string.required);
        awesomeValidation.addValidation(this, R.id.state, RegexTemplate.NOT_EMPTY, R.string.required);
        awesomeValidation.addValidation(this, R.id.city, RegexTemplate.NOT_EMPTY, R.string.required);
        awesomeValidation.addValidation(this, R.id.contactAddress, RegexTemplate.NOT_EMPTY, R.string.required);
    }
}
