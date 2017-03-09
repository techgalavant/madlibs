package com.example.falmi001.madlibs;

/** Created by Mike Fallon.
 * The purpose of this class is to display the words in the story.
 * It currently gets the words passed via intent from MainActivity,
 * but future might be to read directly from the Firebase DB.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.example.falmi001.madlibs.R.id.story;


public class DisplayStory_activity extends AppCompatActivity {

    private Button email_btn;
    private TextView storyName;
    private TextView newStory;
    String storyid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_story);

        email_btn = (Button) findViewById(R.id.email_btn);
        storyName = (TextView) findViewById(R.id.storyName);
        storyName.setText("Your Crazy Story");
        newStory = (TextView) findViewById(story);
        email_btn.setText("Share My Story");

        Intent intent = getIntent();

        String word1 = intent.getStringExtra("Word1");
        String word2 = intent.getStringExtra("Word2");
        String word3 = intent.getStringExtra("Word3");
        String word4 = intent.getStringExtra("Word4");
        String word5 = intent.getStringExtra("Word5");
        String word6 = intent.getStringExtra("Word6");
        String word7 = intent.getStringExtra("Word7");
        storyid = intent.getStringExtra("StoryID");

        // This will switch the words with the corresponding StoryID selected by the user.
        // TO-DO: I don't like this hard coding stuff here...
        switch (storyid){
            case "Story 1":
                newStory.setText("Once upon a time, " + word1 + " went to " + word2 + ". " + word1 + " started " + word3 + ", but first needed to sit down on the " + word4 + " for " + word5 + ". " + word6 + " is a far way to travel, and " + word1 + " needed some time to consider " + word7 + ". THE END");
                break;

            // Story #2 selected -
            case "Story 2":
                newStory.setText(word1 + " once went to the " + word2 + " to do some " + word3 + ". After about " + word5 + ", " + word1+ " had to go " + word7 + " because " + word3 + " was not allowed at " + word6 + ". So, " + word1 + " decided to sit on a " + word4 + " for at least " + word5 + ". THE END");
                break;

            // Story #3 selected -
            case "Story 3":
                newStory.setText("Have you ever seen " + word6 + "? According to " + word1 + ", the only way to see it is by " + word7 + " for about " + word5 + ". " + word1 + " said that you also need to try " + word3 + " in order to get to " + word2 + ". So get off the " + word4 + " and start " + word7 + "! THE END");
                break;

            // Story #4 selected -
            case "Story 4":
                newStory.setText("It took " + word5 + " for " + word1 + " to go to " + word6 + ". That's because " + word1 + " was " + word3 + " on the " + word4 + ". Have you ever heard of that? It's quite common at " + word2 + " in fact, to go " + word7 + " with " + word1 + "'s family and friends. THE END");
                break;

            // Story #5 selected -
            case "Story 5":
                newStory.setText("If " + word1 + " was " + word7 + " on a " + word4 + ", then it would take " + word5 + " for " + word1 + " to get to " + word6 + ". But if " + word1 + " wants to go to " + word2 + ", then " + word1 + " should start " + word3 + " instead of standing on the " + word4 + "! THE END");
                break;
        }

        // Displays the story with the words
        // newStory.setText("Once upon a time, " + word1 + " went to the " + word2 + ". " + word1 + " started " + word3 + ", but first needed to sit down on the " + word4 + " for " + word5 + ". " + word6 + " is a far way to travel, and " + word1 + " needed some time to consider " + word7 + ". THE END");

    // This email_btn will let the user share their story via email by using the device's own sharing options
    email_btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick (View view){

            String story = newStory.getText().toString();
            String intro = "Here's a new story that I created with the Crazy Mad Libs app. ";
            String ending = " Tell me what you think about it!";
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/html");
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "My Crazy Madlibs Story");
            sendIntent.putExtra(Intent.EXTRA_TEXT, intro + story + ending);
            startActivity(Intent.createChooser(sendIntent, "Sharing Options:"));
            //

        }
    });

}

}
