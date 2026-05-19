package com.example.zakatcalculator;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {

    // declare variables
    Toolbar aboutToolbar;
    TextView txtURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about);

        // setup toolbar
        aboutToolbar = findViewById(R.id.about_toolbar);
        setSupportActionBar(aboutToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("About");
            getSupportActionBar().setSubtitle("Developer Info");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setLogo(R.drawable.logo);
        }

        // initialize views
        txtURL = findViewById(R.id.txtURL);

        // textview click listener
        txtURL.setOnClickListener(this);
    }

    // create option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_about, menu);
        return true;
    }

    // handle menu item click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // menu share button
        if (item.getItemId() == R.id.item_share) {

            // share app link
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, "https://github.com/IrsyaShah/ZakatCalculator");
            shareIntent.putExtra(Intent.EXTRA_TITLE, "Check out this Zakat Calculator App!");
            startActivity(Intent.createChooser(shareIntent, null));
            return true;

        // menu home page
        } else if (item.getItemId() == android.R.id.home) {
            getOnBackPressedDispatcher().onBackPressed();
            return true;
        }
        return false;
    }

    // handle clickable URL
    @Override
    public void onClick(View view) {

        // open github website
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://github.com/IrsyaShah/ZakatCalculator"));
        startActivity(intent);
    }
}