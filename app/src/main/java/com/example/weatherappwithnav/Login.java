package com.example.weatherappwithnav;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

public class Login extends AppCompatActivity {
public EditText userName,passWord;
public TextView signUp;
public ImageView weatherImage;
Gson gson;
DbHelper dbHelper;
    public SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    PasswordProtect  passwordProtect=new PasswordProtect();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName=(EditText) findViewById(R.id.username);
        passWord=(EditText) findViewById(R.id.password);
        signUp=(TextView)findViewById(R.id.signup);
        sharedPreferences=getSharedPreferences("userDetails",MODE_PRIVATE);
        editor=sharedPreferences.edit();
        gson=new Gson();
        dbHelper=new DbHelper(this);
        weatherImage=(ImageView)findViewById(R.id.weather_image);
        fadeAnimation();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public  void validate(View view){
        Intent intent=new Intent();
        Boolean status=false;
        String decryptedPassword="";
//        intent.putExtra("status","valid");
        int size=sharedPreferences.getInt("userId",0);
        String username=userName.getText().toString();
        String password=passWord.getText().toString();

        //checking the credentials via shared preference
//        for(int i=1;i<size+1;i++) {
//            User user=new User();
//
//            if(sharedPreferences.contains(String.valueOf(i))){     //to check if the user id exists..
//
//                    String userData=sharedPreferences.getString(String.valueOf(i),null);
//                    user=gson.fromJson(userData,User.class);
//                    decryptedPassword=passwordProtect.decrypt(user.getPassword(),"secretkey");
//                    if(user.getUserName().equalsIgnoreCase(username)&&decryptedPassword.equalsIgnoreCase(String.valueOf(password))){
//                        intent.putExtra("status","valid");
//                        intent.putExtra("color",user.getColorPreference());
//                        status=true;
//
//                        break;
//                    }
//                }
//       }

        //checking the credentials via SQLite
        Cursor c=dbHelper.get(username);
        Toast.makeText(this.getApplicationContext(),"cursorcount."+String.valueOf(c.getCount()),Toast.LENGTH_LONG).show();
        if(c.moveToFirst()) {
            status = true;
            intent.putExtra("status","valid");
            intent.putExtra("color",c.getString(3));
        }
        if(status)
        {
            setResult(1,intent);

            finish();
        }
       else{
            Toast.makeText(this.getApplicationContext(),"Incorrect credentilas...TRY AGAIN....",Toast.LENGTH_LONG).show();

        }


    }
    public void goToSignUp(View view){
            Intent intent=new Intent(this,Signup.class);
            startActivity(intent);
    }
    public void fadeAnimation(){
        Animator fadeAnimator= AnimatorInflater.loadAnimator(this,R.animator.alpha);
        fadeAnimator.setTarget(weatherImage);
        fadeAnimator.start();
    }
}
