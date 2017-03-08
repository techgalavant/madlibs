package com.example.falmi001.madlibs;

/**
 * Created by Mike Fallon on 2/15/2017.
 * This should really be an array, but I am just testing this out.
 * This will be used to set the Words to be stored in the database.
 */

public class Words {
    public String word1;
    public String word2;
    public String word3;
    public String word4;
    public String word5;
    public String word6;
    public String word7;


    // This is the default constructor for DataSnapshot.getValue(Words.class)
    public Words(){

        }

    public Words(String word1, String word2, String word3, String word4, String word5, String word6, String word7) {
            this.word1 = word1;
            this.word2 = word2;
            this.word3 = word3;
            this.word4 = word4;
            this.word5 = word5;
            this.word6 = word6;
            this.word7 = word7;
        }
}
