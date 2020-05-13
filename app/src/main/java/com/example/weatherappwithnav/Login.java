package com.example.weatherappwithnav;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.gson.Gson;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class Login extends AppCompatActivity {
public EditText userName,passWord;
public TextView signUp;
public ImageView weatherImage;
Gson gson;
DbHelper dbHelper;
LoginButton fbLoginButton;
CallbackManager callbackManager;
Intent intent;
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
        fbLoginButton=(LoginButton)findViewById(R.id.facebook_login_button);
        fbLoginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        callbackManager=CallbackManager.Factory.create();
        intent=new Intent();
        //to create key hash
        try{
        PackageInfo info = getPackageManager().getPackageInfo(
                "com.example.weatherappwithnav",
                PackageManager.GET_SIGNATURES);
        for (Signature signature : info.signatures) {
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(signature.toByteArray());
            Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
        }}
        catch (PackageManager.NameNotFoundException e) {
        }
        catch (NoSuchAlgorithmException e) {
        }
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
           // Log.i("gg",LoginResult);
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        showError("logged in babyy");
                        boolean status = true;
                        intent.putExtra("status","valid");
                        intent.putExtra("color","Red");
                        setResult(1,intent);
                        finish();

                    }

                    @Override
                    public void onCancel() {

                    }

                    @Override
                    public void onError(FacebookException error) {
                       showError(error.getMessage());
                    }
                });


                sharedPreferences = getSharedPreferences("userDetails", MODE_PRIVATE);
        editor=sharedPreferences.edit();
        gson=new Gson();
        dbHelper=new DbHelper(this);
        weatherImage=(ImageView)findViewById(R.id.weather_image);
        fadeAnimation();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
//    AccessTokenTracker tokenTradker=new AccessTokenTracker() {
//        @Override
//        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//            if(currentAccessToken==null){
//                showError("logged out");
//            }
//            else{
//                showError("logged in babyy");
//                boolean status = true;
//                intent.putExtra("status","valid");
//                intent.putExtra("color","Red");
//                setResult(1,intent);
//                finish();
//            }
//        }};

    @RequiresApi(api = Build.VERSION_CODES.O)
    public  void validate(View view){

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
    public void showError(String msg){
        Toast.makeText(this.getApplicationContext(),msg,Toast.LENGTH_LONG).show();
    }
}
