package com.example.weatherappwithnav;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;

public class Signup extends AppCompatActivity {
    EditText username,password;
    RadioGroup color;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int i;
    Intent intent;
    Gson gson;
    PasswordProtect  passwordProtect=new PasswordProtect();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        username=(EditText)findViewById(R.id.signup_username);
        password=(EditText)findViewById(R.id.signup_password);
        color=(RadioGroup)findViewById(R.id.color_preference);
        sharedPreferences=getSharedPreferences("userDetails",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        gson=new Gson();



    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void signUp(View view){
        User user=new User();
        int selectedId;
        RadioButton selectedColor;
        String encryptedPassword="";
        selectedId=color.getCheckedRadioButtonId();
        selectedColor=(RadioButton)findViewById(selectedId);

        user.setUid(getLastInsertedUserId());
        user.setUserName(username.getText().toString());
       encryptedPassword= passwordProtect.encrypt(password.getText().toString(),"secretkey");

        user.setPassword(encryptedPassword);
        user.setColorPreference(selectedColor.getText().toString());

        String userData=gson.toJson(user);
        editor.putString(String.valueOf(user.getUid()),userData);
        editor.commit();
        Toast.makeText(this.getApplicationContext(),sharedPreferences.getString(String.valueOf(user.getUid()),null),Toast.LENGTH_LONG).show();

        intent=new Intent(this,Login.class);
        startActivity(intent);

 }
 public int getLastInsertedUserId(){
        int id=sharedPreferences.getInt("userId",0);
        editor.putInt("userId",(id+1));
        return id+1;
    }
}
