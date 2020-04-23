package com.example.weatherappwithnav;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {
public EditText userName,password;
    public SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName=(EditText) findViewById(R.id.username);
        password=(EditText) findViewById(R.id.password);

    }
    public  void validate(View view){
        Intent intent=new Intent();
        intent.putExtra("status","valid");
        if(userName.getText().toString().equals("admin")&&password.getText().toString().equals("admin"))
            {

                sharedPref =this.getPreferences(MODE_PRIVATE);
                String storedPassword=sharedPref.getString("storedUserName",null);
                Toast.makeText(this.getApplicationContext(),"password is:"+storedPassword,Toast.LENGTH_LONG).show();
                if(storedPassword==""){
                    sharedPref = this.getPreferences(MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("storedUserName",password.getText().toString());
                    editor.commit();


                }
                Toast.makeText(this.getApplicationContext(),"password is"+sharedPref.getString("storedUserName",null),Toast.LENGTH_LONG).show();
//                Toast.makeText(this.getApplicationContext(),"Login Failed",Toast.LENGTH_LONG).show();
                intent.putExtra("status","valid");
                setResult(1,intent);
                finish();

            }
        else {
            intent.putExtra("status", "invalid");
            Toast.makeText(this.getApplicationContext(),"Incorrect credentilas...TRY AGAIN....",Toast.LENGTH_LONG).show();
        }

    }
}
