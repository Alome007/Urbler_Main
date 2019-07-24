package com.urbler;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity implements BaseMovieAdapter.OnItemClickListener {
    private List<appPojo> mMovieList;
    private Comparator<appPojo> movieComparator;
    private RecyclerView recyclerView;
    private BaseMovieAdapter mSectionedRecyclerAdapter;
    FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
    String userId0;
    CollapsingToolbarLayout collapsingToolbarLayout;
    ImageView profile;
    String mTitle="";
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkReg())
        {
            Log.i("TAG", "onCreate: Registered");
        }
        else
        {
            startActivity(new Intent(MainActivity.this,
                    register.class));
            finish();
        }
        setContentView(R.layout.activity_main);
        initView();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        //     Resources resources = getResources();
        mMovieList = new ArrayList<>();
        setAdapterByDecade();
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        populateInfo(collapsingToolbarLayout,profile);
        makeCollapsingToolbarLayoutLooksGood(collapsingToolbarLayout);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modalNew  fragment = new modalNew();
                fragment.show(getSupportFragmentManager(), "Active: modalNew");
            }
        });
//        for(int i = 0; i < 20; i++) {
//            Movie movie = new Movie(names[i], years[i], genres[i]);
//            mMovieList.add(movie);
//        }
        populate();
        mSectionedRecyclerAdapter.collapseAllSections();
        mSectionedRecyclerAdapter.setOnItemClickListener(this);
    }

    private void setAdapterByDecade() {
        this.movieComparator = (o1, o2) -> o1.getPosition() + o2.getPosition();
        Collections.sort(mMovieList, movieComparator);
        mSectionedRecyclerAdapter = new MovieAdapterByDecade(mMovieList);
    }
    private void initView() {
        recyclerView=findViewById(R.id.recyclerView);
        collapsingToolbarLayout=findViewById(R.id.main_collapsing_toolbar_layout);
        profile=findViewById(R.id.imageUser);
        fab=findViewById(R.id.addNew);
        FirebaseMessaging.getInstance().subscribeToTopic("userId").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                //    Toast.makeText(getApplicationContext(),"Subscribed", Toast.LENGTH_LONG).show();
            }
        });
    }

    public boolean checkReg(){
        boolean isReg=false;
        SharedPreferences register=getSharedPreferences("isReg",MODE_PRIVATE);
        boolean reg=register.getBoolean("reg",false);
        if (reg){
            isReg=true;
        }
        return isReg;
    }
    public void populateInfo(CollapsingToolbarLayout collapsingToolbarLayout, ImageView pic){
        userId0=user.getUid();
        DatabaseReference you= FirebaseDatabase.getInstance().getReference().child("Riders").child(userId0).child("profile");
        you.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                regPojo weMeetPojo=dataSnapshot.getValue(regPojo.class);
                String yourName= null;
                if (weMeetPojo != null) {
                    String imageUrl=weMeetPojo.getAvatarUrl();
//                    Glide.with(MainActivity.this)
//                            .load(imageUrl)
//                            .into(pic);

                }
                else{
                    //  pic.setImageResource(R.drawable.ic_account_circle_black_24dp);
                    //todo   pic.setVisibility(View.INVISIBLE);
                }
                if (weMeetPojo != null) {

                    yourName = weMeetPojo.getSurName()+" "+" "+ weMeetPojo.getFirstName();
                    // pro.setVisibility(View.GONE);
                }
                collapsingToolbarLayout.setTitle(yourName+" "+mMovieList.size());
//
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Alomeeeee", "loadPost:onCancelled", databaseError.toException());
                // ...
            }

        });
    }

    @Override
    public void onItemClicked(appPojo appPojo) {
        final int index = mMovieList.indexOf(appPojo);
        mMovieList.remove(appPojo);
        mSectionedRecyclerAdapter.notifyItemRemovedAtPosition(index);
        String name=mMovieList.get(index).getCity();
        Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubheaderClicked(int position) {

        if (mSectionedRecyclerAdapter.isSectionExpanded(mSectionedRecyclerAdapter.getSectionIndex(position))) {
            mSectionedRecyclerAdapter.collapseSection(mSectionedRecyclerAdapter.getSectionIndex(position));
        } else {
            mSectionedRecyclerAdapter.expandSection(mSectionedRecyclerAdapter.getSectionIndex(position));
        }
    }
    @Override
    public void onMovieCreated(appPojo appPojo) {
        for (int i = 0; i < mMovieList.size(); i++) {
            if (movieComparator.compare(mMovieList.get(i), appPojo) >= 0) {
                mMovieList.add(i, appPojo);
                mSectionedRecyclerAdapter.notifyItemInsertedAtPosition(i);
                return;
            }
        }
        mMovieList.add(mMovieList.size(), appPojo);
        mSectionedRecyclerAdapter.notifyItemInsertedAtPosition(mMovieList.size() - 1);
    }
    private void populate() {
        final DatabaseReference me= FirebaseDatabase.getInstance().getReference().child("Riders").child(userId0).child("Packages");
        me.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    mMovieList.clear();
                    for(DataSnapshot ds0:ds.getChildren()){
                        appPojo value = ds0.getValue(appPojo.class);
                        String city = value.getDay();
                        String city0=value.getCity();
                        int position=value.getPosition();
                        String type = value.getType();
                        String date= value.getPhoneNumber();
                        appPojo meetPojo = new appPojo();
                        meetPojo.setDay(city);
                        meetPojo.setCity(city0);
                        meetPojo.setType(type);
                        meetPojo.setPhoneNumber(date);
                        meetPojo.setPosition(position);
                        mMovieList.add(meetPojo);
                        int size=mMovieList.size();
                      //  Toast.makeText(MainActivity.this, size, Toast.LENGTH_SHORT).show();
                        recyclerView.setAdapter(mSectionedRecyclerAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Errrror",Toast.LENGTH_LONG).show();
            }
        });
    };
    private void makeCollapsingToolbarLayoutLooksGood(CollapsingToolbarLayout collapsingToolbarLayout) {
        try {
            final Field field = collapsingToolbarLayout.getClass().getDeclaredField("mCollapsingTextHelper");
            field.setAccessible(true);

            final Object object = field.get(collapsingToolbarLayout);
            final Field tpf = object.getClass().getDeclaredField("mTextPaint");
            tpf.setAccessible(true);

            ((TextPaint) tpf.get(object)).setTypeface(Typeface.createFromAsset(getAssets(), "product.ttf"));
            ((TextPaint) tpf.get(object)).setColor(getResources().getColor(R.color.white));
        } catch (Exception ignored) {
        }
    }
}
