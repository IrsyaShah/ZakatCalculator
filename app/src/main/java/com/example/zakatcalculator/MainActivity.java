package com.example.zakatcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Locale;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // declare variables
    Toolbar mainToolbar;
    TextView txtUruf, txtPayable, txtTotalZakat;
    EditText etGoldWeight, etGoldPrice;
    Spinner spType;
    Button btnCalculate, btnClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // setup toolbar
        mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setSubtitle("Check Easily");
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setLogo(R.drawable.logo);
        }

        // setup spinner
        spType = findViewById(R.id.spType);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.type_gold, R.layout.spinner_item_main);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(adapter);

        // initialize views
        etGoldWeight = findViewById(R.id.etGoldWeight);
        etGoldPrice = findViewById(R.id.etGoldPrice);
        txtUruf = findViewById(R.id.txtUruf);
        txtPayable = findViewById(R.id.txtPayable);
        txtTotalZakat = findViewById(R.id.txtTotalZakat);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnClear = findViewById(R.id.btnClear);

        // button click listener
        btnCalculate.setOnClickListener(this);
        btnClear.setOnClickListener(this);
    }

    // create option menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
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

        // menu about page
        } else if (item.getItemId() == R.id.item_about) {

            // open about page
            Intent aboutIntent = new Intent(this, AboutActivity.class);
            startActivity(aboutIntent);
        }
        return false;
    }

    // handle button click
    @Override
    public void onClick(View view) {
        // calculate button
        if (view == btnCalculate) {

            // declare variables
            String type;
            double x, weight, goldprice, uruf, payAmount, total;

            // get selected type
            type = spType.getSelectedItem().toString().trim();

            // check empty input
            if (etGoldWeight.getText().toString().isEmpty() || etGoldPrice.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // check spinner selection
            if (spType.getSelectedItem().toString().equals("Select Type")) {
                Toast.makeText(this, "Please select type", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                // convert input to number
                weight = Double.parseDouble(etGoldWeight.getText().toString());
                goldprice = Double.parseDouble(etGoldPrice.getText().toString());

                // set nisab or uruf value
                if (type.equalsIgnoreCase("keep")) {
                    x = 85;
                } else {
                    x = 200;
                }

                // calculate eligible weight
                uruf = weight - x;

                // calculate payable amount
                if (uruf <= 0) {
                    payAmount = 0;
                } else {
                    payAmount = uruf * goldprice;
                }

                // calculate zakat (2.5%)
                total = payAmount * 0.025;

                // show result
                txtUruf.setText(String.format(Locale.US, "RM %.2f", uruf));
                txtPayable.setText(String.format(Locale.US, "RM %.2f", payAmount));
                txtTotalZakat.setText(String.format(Locale.US, "RM %.2f", total));
            }
            catch (NumberFormatException e) {
                // error input
                Toast.makeText(this, "Please enter valid number", Toast.LENGTH_SHORT).show();
            }

        // clear button
        } else if (view == btnClear) {
            etGoldWeight.setText("");
            etGoldPrice.setText("");
            spType.setSelection(0);
            txtUruf.setText("");
            txtPayable.setText("");
            txtTotalZakat.setText("");
        }
    }
}