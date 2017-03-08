package com.example.falmi001.madlibs;

/** Created by Mike Fallon.
 * The purpose of EnterWords is to allow the user to enter words. It will store the words in Firebase.
 * Credits -
 * Firebase Tutorial - http://www.androidhive.info/2016/10/android-working-with-firebase-realtime-database/
 * Firebase Tutorial - https://www.simplifiedcoding.net/firebase-realtime-database-example-android-application/
 * Device Shaking Detection - http://jasonmcreynolds.com/?p=388
 */

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.falmi001.madlibs.R.id.word1;
import static com.example.falmi001.madlibs.R.id.word2;
import static com.example.falmi001.madlibs.R.id.word3;
import static com.example.falmi001.madlibs.R.id.word4;
import static com.example.falmi001.madlibs.R.id.word5;
import static com.example.falmi001.madlibs.R.id.word6;
import static com.example.falmi001.madlibs.R.id.word7;


public class EnterWords extends AppCompatActivity {

    private static final String TAG = EnterWords.class.getSimpleName();
    private Button mashIt_btn;
        private Button clearIt_btn;
        private EditText inWord1, inWord2, inWord3, inWord4, inWord5, inWord6, inWord7;
        private TextView txtDetails;
        private DatabaseReference myStory;
        private FirebaseDatabase myFBInstance;
        private String storyId;
        private String togglebtn;
        private String userName;


        // The following are used for the shake detection
        private SensorManager mSensorManager;
        private Sensor mAccelerometer;
        private ShakeDetector mShakeDetector;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.wordlist);

            txtDetails = (TextView) findViewById(R.id.textView);
            inWord1 = (EditText) findViewById(word1);
            inWord2 = (EditText) findViewById(word2);
            inWord3 = (EditText) findViewById(word3);
            inWord4 = (EditText) findViewById(word4);
            inWord5 = (EditText) findViewById(word5);
            inWord6 = (EditText) findViewById(word6);
            inWord7 = (EditText) findViewById(word7);
            mashIt_btn = (Button) findViewById(R.id.mashIt);
            clearIt_btn = (Button) findViewById(R.id.clearIt);

            // Get the user's name and the story selected from StoryPicker.java
            Intent intent = getIntent();

            userName = intent.getStringExtra("UserName");
            storyId = intent.getStringExtra("StoryID");
            txtDetails.setText("Welcome " + userName + "! Enter the words below:");

            myFBInstance = FirebaseDatabase.getInstance();

            // Get reference to the user & the story
            myStory = myFBInstance.getReference(userName).child(storyId);

            // Writes the username to the Firebase Database
            //myFBInstance.getReference(userName).setValue(userName);
            Log.e(TAG, "myFBInstance is set to " + userName + " and child (" + storyId + ").");

            // Store the Words in Firebase and passes the words via intent to display the story
            mashIt_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String word1 = inWord1.getText().toString();
                    String word2 = inWord2.getText().toString();
                    String word3 = inWord3.getText().toString();
                    String word4 = inWord4.getText().toString();
                    String word5 = inWord5.getText().toString();
                    String word6 = inWord6.getText().toString();
                    String word7 = inWord7.getText().toString();

                    // If no words have been added to a story, create a list of new words
                    // If any words have been added to the story, then update the list of words.
                    // BUT this says if storyID is empty then create a list else update a list.
                    // StoryID will not ever be empty at this point as it's been set by the user
                    // in the StoryPicker.java class.
                    // SO - this still works but might want to rewrite or eliminate this.
                    if (TextUtils.isEmpty(togglebtn)) {
                        createList(word1, word2, word3, word4, word5, word6, word7);
                        Log.e(TAG, "There were no words in " + storyId + ", so created a new list.");
                    } else {
                        updateList(word1, word2, word3, word4, word5, word6, word7);
                        Log.e(TAG, "Updated the word list for " + storyId + ".");
                    }

                    // Pass the words as strings via Intent to the DisplayStory_activity
                    Intent intent = new Intent(EnterWords.this, DisplayStory_activity.class);

                    intent.putExtra("Word1", word1);
                    intent.putExtra("Word2", word2);
                    intent.putExtra("Word3", word3);
                    intent.putExtra("Word4", word4);
                    intent.putExtra("Word5", word5);
                    intent.putExtra("Word6", word6);
                    intent.putExtra("Word7", word7);
                    intent.putExtra("StoryID", storyId);
                    startActivity(intent);
                }

            });

            // Clear the list of words
            clearIt_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inWord1.setText("");
                    inWord2.setText("");
                    inWord3.setText("");
                    inWord4.setText("");
                    inWord5.setText("");
                    inWord6.setText("");
                    inWord7.setText("");
                    togglebtn = "";
                    Log.e(TAG, "clearIt_btn was used to reset word values to null.");
                }
            });

            // The user can also shake the device to clear the list of words
            // With help from http://jasonmcreynolds.com/?p=388
            mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mAccelerometer = mSensorManager
                    .getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            mShakeDetector = new ShakeDetector();
            mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {
                @Override
                public void onShake(int count) {
                    inWord1.setText("");
                    inWord2.setText("");
                    inWord3.setText("");
                    inWord4.setText("");
                    inWord5.setText("");
                    inWord6.setText("");
                    inWord7.setText("");
                    togglebtn = "";
                    Log.e(TAG, "ShakeListener detected movement so words were set to null.");
                }
            });

           toggleButton();

        }

        // Changing button text
        private void toggleButton() {
            if (TextUtils.isEmpty(togglebtn)) {
                mashIt_btn.setText("See My Crazy Story!");
            } else {
                mashIt_btn.setText("Update My Crazy Story!");
            }
        }

        // Creates a new list of words in the Firebase Database
        private void createList(String word1, String word2, String word3, String word4, String word5, String word6, String word7) {

            Words words = new Words(word1, word2, word3, word4, word5, word6, word7);
            togglebtn = "On";

            myStory.setValue(words);

            addWordChangeListener();
        }

    // Updates the list of words in the Firebase Database
    private void updateList(String word1, String word2, String word3, String word4, String word5, String word6, String word7) {

            if (!TextUtils.isEmpty(word1))
                myStory.child("word1").setValue(word1);
            if (!TextUtils.isEmpty(word2))
                myStory.child("word2").setValue(word2);
            if (!TextUtils.isEmpty(word3))
                myStory.child("word3").setValue(word3);
            if (!TextUtils.isEmpty(word4))
                myStory.child("word4").setValue(word4);
            if (!TextUtils.isEmpty(word5))
                myStory.child("word5").setValue(word5);
            if (!TextUtils.isEmpty(word6))
                myStory.child("word6").setValue(word6);
            if (!TextUtils.isEmpty(word7))
                myStory.child("word7").setValue(word7);

        }

        private void addWordChangeListener() {
            // User data change listener
            myStory.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Words words = dataSnapshot.getValue(Words.class);

                    // Check for null
                    if (words == null) {
                        Log.e(TAG, "There are no words yet found.");
                        togglebtn = "";
                        return;
                    } else {

                        Log.e(TAG, "Words have been changed!");

                        // Change the title
                        txtDetails.setText("Change Your Words:");

                        toggleButton();
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                    // Failed to read value
                    Log.e(TAG, "Database error. See onCancelled", error.toException());
                }
            });
        }


        // Used for ShakerListener
        @Override
        public void onResume() {
            super.onResume();
            // Add the following line to register the Session Manager Listener onResume
            mSensorManager.registerListener(mShakeDetector, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
        }

    // Used for ShakerListener
    @Override
        public void onPause() {
            // Add the following line to unregister the Sensor Manager onPause
            mSensorManager.unregisterListener(mShakeDetector);
            super.onPause();
        }
}
