package com.example.weatherappwithnav;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    public Intent intent;
    public boolean logginStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent=new Intent(this,Login.class);
        startActivityForResult(intent,1);




    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1)
        {
            if(data.getStringExtra("status").equals("valid")) {
                logginStatus = true;
                Toast.makeText(this.getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();

            }
            else {
                logginStatus = false;

            }
            if(logginStatus) {
                BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
                bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
//                if (savedInstanceState == null) {
//                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                            new Home()).commit();
//                }
            }
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.home:
                            selectedFragment = new Home();
                            break;
                        case R.id.search:
                            selectedFragment = new Search();
                            break;
                        case R.id.explore:
                            selectedFragment = new Explore();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}
