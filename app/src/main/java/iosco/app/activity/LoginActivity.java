package iosco.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.io.IOException;

import iosco.app.R;
import iosco.app.model.entity.ErrorEntity;
import iosco.app.model.entity.TokenResponseEntity;
import iosco.app.service.ApiImplementation;
import iosco.app.utils.AVLoadingIndicatorView;
import iosco.app.utils.Helpers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtPass;
    private Button btnLogin,btnForgot;
    private RelativeLayout layoutShadow;

    private AVLoadingIndicatorView loginLoadingIndicator;

    private boolean loginTouchable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); int i=1;

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPass = (EditText)findViewById(R.id.txtPass);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnForgot= (Button)findViewById(R.id.btnForgot);
        loginLoadingIndicator = (AVLoadingIndicatorView)findViewById(R.id.loginLoadingIndicator);
        layoutShadow = (RelativeLayout)findViewById(R.id.layoutShadow);

        txtEmail.requestFocus();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (loginTouchable)
                    login();
            }
        });

        Helpers.changeFont(this, txtEmail, "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(this, txtPass, "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(this, btnLogin, "HelveticaNeueLTStd-Bd.otf");

        Helpers.changeFont(this, (Button)findViewById(R.id.btnForgot), "HelveticaNeueLTStd-Roman.otf");


        btnForgot.setPaintFlags(btnForgot.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void login(){
        if(!txtEmail.getText().toString().trim().equals("") && !txtPass.getText().toString().trim().equals("")) {

            loginTouchable = false;
            layoutShadow.setVisibility(View.VISIBLE);
            loginLoadingIndicator.setVisibility(View.VISIBLE);
            ApiImplementation.getService().getToken("password", txtEmail.getText().toString(), txtPass.getText().toString()).enqueue(new Callback<TokenResponseEntity>() {
                @Override
                public void onResponse(Call<TokenResponseEntity> call, Response<TokenResponseEntity> response) {
                    if (response.isSuccess()) {
                        // request successful (status code 200, 201)
                        TokenResponseEntity result = response.body();
                        Log.i("respuesta", result.getAccess_token());


                        saveToken(response.body());
                        startActivity(new Intent(getApplicationContext(), MainActivityNew.class));
                        finish();
                    } else {
                        //request not successful (like 400,401,403 etc)
                        //Handle errors


                        try {
                            Gson gson = new Gson();
                            ErrorEntity error = gson.fromJson(response.errorBody().string(), ErrorEntity.class);
                            Toast.makeText(getApplicationContext(), error.getError_description(), Toast.LENGTH_SHORT).show();

                        } catch (IOException ex) {
                            ex.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Email or password is incorrect", Toast.LENGTH_SHORT).show();

                        }
                    }
                    loginTouchable = true;
                    layoutShadow.setVisibility(View.GONE);
                    loginLoadingIndicator.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<TokenResponseEntity> call, Throwable t) {
                    Log.i("respuesta", "onFailure");
                    t.printStackTrace();
                    loginTouchable = true;
                    layoutShadow.setVisibility(View.GONE);
                    loginLoadingIndicator.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void saveToken(TokenResponseEntity token){
        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=saved_values.edit();
        editor.putString("access_token", token.getAccess_token());
        editor.putString("token_type", token.getToken_type());
        editor.putInt("expires_in", token.getExpires_in());
        editor.putString("userName", token.getUserName());
        editor.commit();
    }

    public void forgotPassword(View v){
        startActivity(new Intent(this,ForgetActivity.class));
        this.overridePendingTransition(R.anim.slide_in_top, R.anim.slide_stay);
    }


}
