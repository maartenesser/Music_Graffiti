package com.example.maartenesser.musicgraffitti;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Music Graffitti");


        Button button_MusicGraffittiMap = (Button) findViewById(R.id.button_musicGraffittiMap);
        Button button_AddMusic = (Button) findViewById(R.id.button_addMusic);

        button_MusicGraffittiMap.setOnClickListener(this);
        button_AddMusic.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ImageView imgView = (ImageView) findViewById(R.id.imageView);

        if (SettingsActivity.getRemoveImageBoolean(this)) {
            imgView.setVisibility(View.INVISIBLE);
        } else {
            imgView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.addMarker:
                Intent addMusic = new Intent(MainActivity.this, MusicActivity.class);
                startActivity(addMusic);
                return true;
            case R.id.map:
                Intent maps = new Intent(MainActivity.this, MusicGraffittiMapsActivity.class);
                startActivity(maps);
                return true;
            case R.id.settings:
                Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.button_musicGraffittiMap):
                Intent MusicGraffittiMapActivity = new Intent(MainActivity.this, MusicGraffittiMapsActivity.class);
                startActivity(MusicGraffittiMapActivity);
                break;
            case (R.id.button_addMusic):
                Intent AddMusicActivity = new Intent(MainActivity.this, MusicActivity.class);
                startActivity(AddMusicActivity);
                break;

        }
    }
}
