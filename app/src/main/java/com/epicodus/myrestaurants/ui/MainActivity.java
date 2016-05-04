package com.epicodus.myrestaurants.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.epicodus.myrestaurants.Constants;
import com.epicodus.myrestaurants.R;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Firebase mSearchedLocationRef;
    private ValueEventListener mSearchedLocationRefListener;

    @Bind(R.id.locationEditText) EditText mLocationEditText;
    @Bind(R.id.findRestaurantsButton) Button mFindRestaurantsButton ;
    @Bind(R.id.savedRestaurantsButton) Button mSavedRestaurantsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mFindRestaurantsButton.setOnClickListener(this);
        mSavedRestaurantsButton.setOnClickListener(this);
        mSearchedLocationRef = new Firebase(Constants.FIREBASE_URL_SEARCHED_LOCATION);

        mSearchedLocationRefListener = mSearchedLocationRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String locations = dataSnapshot.getValue().toString();
                Log.d("Location updated", locations);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSearchedLocationRef.removeEventListener(mSearchedLocationRefListener);
    }
            @Override
            public void onClick(View v) {
                if (v == mFindRestaurantsButton) {
                    String location = mLocationEditText.getText().toString();
                    saveLocationToFirebase(location);
                    Intent intent = new Intent(MainActivity.this, RestaurantListActivity.class);
                    intent.putExtra("location", location);
                    startActivity(intent);
                }
                if (v == mSavedRestaurantsButton) {
                    Intent intent = new Intent(MainActivity.this, SavedRestaurantListActivity.class);
                    startActivity(intent);
                }
            }

        public void saveLocationToFirebase(String location) {
            Firebase searchedLocationRef = new Firebase(Constants.FIREBASE_URL_SEARCHED_LOCATION);
            searchedLocationRef.push().setValue(location);
        }
}
