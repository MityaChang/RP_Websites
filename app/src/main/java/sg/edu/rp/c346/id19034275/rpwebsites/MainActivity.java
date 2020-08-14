package sg.edu.rp.c346.id19034275.rpwebsites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    Spinner spnCategory, spnSubCategory;
    Button btnGo;
    ArrayList<String> alcategory = new ArrayList<>();
    ArrayAdapter<String> aaCategory;

    String[][] urls = {
            {
                    "https://www.rp.edu.sg/",
                    "https://www.rp.edu.sg/student-life",
            },
            {
                    "https://www.rp.edu.sg/soi/full-time-diplomas/details/r47",
                    "https://www.rp.edu.sg/soi/full-time-diplomas/details/r12"
            }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spnCategory = findViewById(R.id.spinner1);
        spnSubCategory = findViewById(R.id.spinner2);
        btnGo = findViewById(R.id.button);

        aaCategory = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, alcategory);
        spnSubCategory.setAdapter(aaCategory);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                alcategory.clear();
                switch (position) {
                    case 0:
                        alcategory.addAll(Arrays.asList(getResources().getStringArray(R.array.sub_category)));
                        aaCategory.notifyDataSetChanged();
                        break;
                    case 1:
                        alcategory.addAll(Arrays.asList(getResources().getStringArray(R.array.sub_categorysoi)));
                        aaCategory.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "";
                int catePos = spnCategory.getSelectedItemPosition();
                int subcatePos = spnSubCategory.getSelectedItemPosition();
                url = urls[catePos][subcatePos];
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("URL", url);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();

        int catPos = spnCategory.getSelectedItemPosition();
        int subPos = spnSubCategory.getSelectedItemPosition();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor prefEdit = prefs.edit();

        prefEdit.putInt("spn1", catPos);
        prefEdit.putInt("spn2", subPos);

        prefEdit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int menuPos = prefs.getInt("menu_position", 0);
        int submenuPos = prefs.getInt("submenu_position", 0);
        spnCategory.setSelection(menuPos);

        alcategory.clear();
        if (menuPos == 0) {
            String[] strSubMenu = getResources().getStringArray(R.array.sub_category);
            alcategory.addAll(Arrays.asList(strSubMenu));
        } else if (menuPos == 1) {
            String[] strSubMenu = getResources().getStringArray(R.array.sub_categorysoi);
            alcategory.addAll(Arrays.asList(strSubMenu));
        }
        spnSubCategory.setSelection(submenuPos);
        aaCategory.notifyDataSetChanged();
    }
}