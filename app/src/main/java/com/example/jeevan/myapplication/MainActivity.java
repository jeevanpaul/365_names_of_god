package com.example.jeevan.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int current_index;
    ArrayList<String> god_names = new ArrayList<String>(Arrays.asList("I am to be feared above all gods",
            "I am great and worthy to be praised",
            "I am the LORD, worship me in holiness",
            "I am God exalted as head over all ",
            "I am God, greatness, power, glory, victory and majesty belongs to Me",
            "I am God, power and might are in My hand",
            "I am God, riches and honor come from Me"));
    ArrayList<String> corresponding_descriptions = new ArrayList<String>(Arrays.asList("1 Chronicles 16:25New King James Version (NKJV) \n For the Lord is great and greatly to be praised; He is also to be feared above all gods.",
            "1 Chronicles 16:25New King James Version (NKJV) \n For the Lord is great and greatly to be praised; He is also to be feared above all gods.",
            "1 Chronicles 16:29New King James Version (NKJV) \n Give to the Lord the glory due His name; Bring an offering, and come before Him. Oh, worship the Lord in the beauty of holiness!",
            "1 Chronicles 29:11New King James Version (NKJV)\n Yours, O Lord, is the greatness, The power and the glory, The victory and the majesty; For all that is in heaven and in earth is Yours; Yours is the kingdom, O Lord, And You are exalted as head over all.",
            "1 Chronicles 29:11New King James Version (NKJV)\n Yours, O Lord, is the greatness, The power and the glory, The victory and the majesty; For all that is in heaven and in earth is Yours; Yours is the kingdom, O Lord, And You are exalted as head over all.",
            "1 Chronicles 29:12New King James Version (NKJV)\n Both riches and honor come from You, And You reign over all. In Your hand is power and might; In Your hand it is to make great And to give strength to all.",
            "1 Chronicles 29:12New King James Version (NKJV)\n Both riches and honor come from You, And You reign over all. In Your hand is power and might; In Your hand it is to make great And to give strength to all."
            ));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        renderGodNames();
    }

    void renderGodNames() {
        final int total_size = god_names.size();
        SharedPreferences sharedpreferences = getSharedPreferences("god_names", Context.MODE_PRIVATE);
        current_index = sharedpreferences.getInt("current_index", -1);
        if(current_index == -1) {
            Random rand = new Random();
            current_index = rand.nextInt(total_size);
        }
        ((TextView)findViewById(R.id.name_of_god)).setText(god_names.get(current_index));
        ((TextView)findViewById(R.id.verse_and_description)).setText(corresponding_descriptions.get(current_index));
        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int next_index;
                if(current_index == (total_size - 1))
                    next_index = 0;
                else
                    next_index = current_index + 1;
                    changeNameAndDescription(next_index);
            }
        });
        findViewById(R.id.previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int previous_index;
                if(current_index == 0)
                    previous_index = (total_size - 1);
                else
                    previous_index  = current_index - 1;
                changeNameAndDescription(previous_index);
            }
        });

    }

    void changeNameAndDescription(int index) {
        current_index = index;
        ((TextView)findViewById(R.id.name_of_god)).setText(god_names.get(index));
        ((TextView)findViewById(R.id.verse_and_description)).setText(corresponding_descriptions.get(index));
        SharedPreferences sharedpreferences = getSharedPreferences("god_names", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("current_index", current_index);
        editor.commit();
        Log.i("changed_index", String.valueOf(sharedpreferences.getInt("current_index", 0)));
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedpreferences = getSharedPreferences("god_names", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt("current_index", current_index);
        editor.commit();
        Log.i("on_pause_index", String.valueOf(sharedpreferences.getInt("current_index", -1)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedpreferences = getSharedPreferences("god_names", Context.MODE_PRIVATE);
        current_index = sharedpreferences.getInt("current_index", -1);
        Log.i("on_resume_index", current_index+"");
        if(current_index == -1) {
            Random rand = new Random();
            current_index = rand.nextInt(god_names.size());
        }
    }
}
