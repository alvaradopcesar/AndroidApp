package iosco.app.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import iosco.app.R;
import iosco.app.service.ApiImplementation;
import iosco.app.utils.Helpers;
import iosco.app.utils.TextViewPro;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgetActivity extends AppCompatActivity {

    private EditText txtPass;
    private Button btnLogin;
    private boolean sendState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  int i=5, g = 5;
        setContentView(R.layout.activity_forget);

        txtPass = (EditText)findViewById(R.id.txtPass);
        btnLogin = (Button)findViewById(R.id.btnLogin);

        Helpers.changeFont(this, txtPass, "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(this, btnLogin, "HelveticaNeueLTStd-Bd.otf");

        sendState = false;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!sendState)
                    sendEmail();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forget, menu);
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

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.slide_stay, R.anim.slide_out_top);
    }

    public void closeActivity(View v){
        finish();
    }

    private void sendEmail(){
        sendState = true;
        ApiImplementation.getService().forgotPassword(txtPass.getText().toString(), "en").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                sendState = false;
                Toast.makeText(getApplicationContext(), Helpers.responseToString(response), Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                sendState = false;
                Log.i("respuesta18","sendEmail() onFailure()");
                t.printStackTrace();
            }
        });
    }
}
