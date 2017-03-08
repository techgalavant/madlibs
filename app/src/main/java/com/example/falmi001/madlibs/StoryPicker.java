package com.example.falmi001.madlibs;

/**
 * Created by Mike Fallon.
 * The purpose of this activity is to let the user pick a Madlib story.
 * This screen should display a list of stories as buttons and also
 * allow the user to enter their name. Upon story selection, it will launch
 * a new screen with 10 Words to enter into the story.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.falmi001.madlibs.R.id.story_list;


public class StoryPicker extends AppCompatActivity {

    private static final String TAG = StoryPicker.class.getSimpleName();
    private Button storybtn_1;
    private Button storybtn_2;
    private Button storybtn_3;
    private Button storybtn_4;
    private Button storybtn_5;
    private TextView storylist;
    private EditText username;
    private String userName;
    private String storyId;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storylist);

        // FUTURE STATE: lookup FB DB to identify the story names
        // FUTURE STATE: lookup FB DB for the incomplete sentences
        // associated with the story name

        storybtn_1 = (Button) findViewById(R.id.story_btn1);
        storybtn_2 = (Button) findViewById(R.id.story_btn2);
        storybtn_3 = (Button) findViewById(R.id.story_btn3);
        storybtn_4 = (Button) findViewById(R.id.story_btn4);
        storybtn_5 = (Button) findViewById(R.id.story_btn5);
        storylist = (TextView) findViewById(story_list);
        username = (EditText) findViewById(R.id.username);

        // set Story 1 to the first button and pass the story name and user name to the MainActivity
        storybtn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storyId = "Story 1";
                userName = username.getText().toString();
                nameCheck();

                // Pass the strings to the DisplayStory_activity
                // FUTURE: lookup the Firebase DB for the sentences in this story
                // and pass those via intent to DisplayStory_activity
                Intent intent = new Intent(StoryPicker.this, EnterWords.class);

                intent.putExtra("UserName", userName);
                intent.putExtra("StoryID", storyId);

                startActivity(intent);
            }

        });


        // set Story 2 to the first button and pass the story name and user name to the MainActivity
        storybtn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storyId = "Story 2";
                userName = username.getText().toString();
                nameCheck();

                // Pass the strings to the MainStory_activity
                Intent intent = new Intent(StoryPicker.this, EnterWords.class);

                intent.putExtra("UserName", userName);
                intent.putExtra("StoryID", storyId);

                startActivity(intent);
            }

        });

        // set Story 3 to the first button and pass the story name and user name to the MainActivity
        storybtn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storyId = "Story 3";
                userName = username.getText().toString();
                nameCheck();

                // Pass the strings to the MainStory_activity
                Intent intent = new Intent(StoryPicker.this, EnterWords.class);

                intent.putExtra("UserName", userName);
                intent.putExtra("StoryID", storyId);

                startActivity(intent);
            }

        });

        // set Story 4 to the first button and pass the story name and user name to the MainActivity
        storybtn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storyId = "Story 4";
                userName = username.getText().toString();
                nameCheck();

                // Pass the strings to the MainStory_activity
                Intent intent = new Intent(StoryPicker.this, EnterWords.class);

                intent.putExtra("UserName", userName);
                intent.putExtra("StoryID", storyId);

                startActivity(intent);
            }

        });

        // set Story 5 to the first button and pass the story name and user name to the MainActivity
        storybtn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storyId = "Story 5";
                userName = username.getText().toString();
                nameCheck();

                // Pass the strings to the MainStory_activity
                Intent intent = new Intent(StoryPicker.this, EnterWords.class);

                intent.putExtra("UserName", userName);
                intent.putExtra("StoryID", storyId);

                startActivity(intent);
            }

        });
    }

    // Check if user has entered their name
    private void nameCheck() {
        if (TextUtils.isEmpty(userName)) {
            userName = "Last User";
            Log.e(TAG, "The username was changed to '" + userName + "' because a username was not entered.");
        } else {
            Log.e(TAG, "The username is " + userName + " per nameCheck operation.");
        }
        Log.e(TAG,storyId + " was selected.");

    }
}