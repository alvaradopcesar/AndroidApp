package iosco.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import iosco.app.R;
import iosco.app.model.entity.CalendarEntity;
import iosco.app.model.entity.Event;
import iosco.app.model.entity.Exhibitor;
import iosco.app.model.entity.Seccion;
import iosco.app.fragment.AboutFragment;
import iosco.app.fragment.AssistantsFragment;
import iosco.app.fragment.CalendarFragment;
import iosco.app.fragment.CalenderFragment_;
import iosco.app.fragment.CalendarDetailFragment;
import iosco.app.fragment.EveningEventDetailFragment;
import iosco.app.fragment.EveningEventsFragment;
import iosco.app.fragment.ExhibitorDetailFragment;
import iosco.app.fragment.ExhibitorsFragment;
import iosco.app.fragment.ProfileFragment;

public class MainActivity extends AppCompatActivity {

    private Objects dumbObject;

    private DrawerLayout drawerLayout;
    private NavigationView navView;

    private Toolbar appbar;


    private ArrayList<Seccion> listMenu;
    private List<Fragment> listFragments;
    private int fragmentActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appbar = (Toolbar) findViewById(R.id.appbar); int i=1;
        setSupportActionBar(appbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.navview);

        setUpNavigationDrawer();

        listFragments = new ArrayList<>();
        listFragments.add(CalenderFragment_.getInstance());
        listFragments.add(ExhibitorsFragment.getInstance());
        listFragments.add(AssistantsFragment.getInstance());
        listFragments.add(EveningEventsFragment.getInstance());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, CalendarFragment.getInstance())
                .commit();

        getSupportActionBar().setTitle("Calender");


        setUpMenu();



        /*ApiService api = new ApiService() {
            @Override
            public void getToken(@Path("grant_type") String grant_type, @Path("username") String username, @Path("password") String password, Callback<TokenResponseEntity> token) {

            }

            @Override
            public Callback<TokenResponseEntity> getToken2(@Path("grant_type") String grant_type, @Path("username") String username, @Path("password") String password) {
                return null;
            }
        }
        Callback<TokenResponseEntity> mc = ApiService.getToken2("password", "enrique.tito@live.com", "123ABCa$");
        mc.enqueue(new Callback<TokenResponseEntity>() {
            @Override
            public void onResponse(Response<TokenResponseEntity> response) {

                Log.d("CallBack", " response is " + response);
            }

            @Override
            public void onFailure(Throwable t) {

                Log.d("CallBack", " Throwable is " +t);
            }
        });

        ApiImplementation.service.getToken("password", "enrique.tito@live.com", "123ABCa$", new Callback<TokenResponseEntity>() {
            @Override
            public void onResponse(Call<TokenResponseEntity> call, Response<TokenResponseEntity> response) {
                TokenResponseEntity token = response.body();

                Log.i("respuesta", token.getAccess_token());
                Log.i("respuesta", token.getError_description());
            }

            @Override
            public void onFailure(Call<TokenResponseEntity> call, Throwable t) {

            }
        });*/
/*
        ApiImplementation.service.getToken("password","enrique.tito@live.com","123ABCa$").enqueue(new Callback<TokenResponseEntity>() {
            @Override
            public void onResponse(Call<TokenResponseEntity> call, Response<TokenResponseEntity> response) {
                TokenResponseEntity token = response.body();

                Log.i("respuesta", token.getAccess_token());
                Log.i("respuesta", token.getError_description());
            }

            @Override
            public void onFailure(Call<TokenResponseEntity> call, Throwable t) {

            }
        });*/

        /*ApiImplementation.service.getToken("password","enrique.tito@live.com","123ABCa$").enqueue(new Callback<TokenResponseEntity>() {
            @Override
            public void onResponse(Call<TokenResponseEntity> call, Response<TokenResponseEntity> response) {
                TokenResponseEntity token = response.body();

                Log.i("respuesta", token.getAccess_token());
                Log.i("respuesta", token.getError_description());
            }

            @Override
            public void onFailure(Call<TokenResponseEntity> call, Throwable t) {

            }
        });

        ApiImplementation.service.getToken("password","enrique.tito@live.com","123ABCa$", new Callback<TokenResponseEntity>() {
            @Override
            void success(TokenResponseEntity token, Response response) {
                // ...
            }


            @Override
            void failure(RetrofitError error) {
                // ...
            }
        });*/


    }

    private void setUpMenu(){
        listMenu = new ArrayList<>();

        Seccion s1 = new Seccion();
        s1.setContainer((RelativeLayout) findViewById(R.id.seccion_1));
        s1.setIcon((ImageView) findViewById(R.id.imgSeccion1));
        s1.setText((TextView) findViewById(R.id.txtSeccion1));
        s1.setImageA(R.drawable.menu_seccion1);
        s1.setImageB(R.drawable.menu_seccion1b);
        s1.setColorB(getResources().getColor(R.color.color_1));
        s1.setId(1);

        Seccion s2 = new Seccion();
        s2.setContainer((RelativeLayout) findViewById(R.id.seccion_2));
        s2.setIcon((ImageView) findViewById(R.id.imgSeccion2));
        s2.setText((TextView) findViewById(R.id.txtSeccion2));
        s2.setImageA(R.drawable.menu_seccion2);
        s2.setImageB(R.drawable.menu_seccion2b);
        s2.setColorB(getResources().getColor(R.color.color_2));
        s2.setId(2);

        Seccion s3 = new Seccion();
        s3.setContainer((RelativeLayout) findViewById(R.id.seccion_3));
        s3.setIcon((ImageView) findViewById(R.id.imgSeccion3));
        s3.setText((TextView) findViewById(R.id.txtSeccion3));
        s3.setImageA(R.drawable.menu_seccion3);
        s3.setImageB(R.drawable.menu_seccion3b);
        s3.setColorB(getResources().getColor(R.color.color_3));
        s3.setId(3);

        Seccion s4 = new Seccion();
        s4.setContainer((RelativeLayout) findViewById(R.id.seccion_4));
        s4.setIcon((ImageView) findViewById(R.id.imgSeccion4));
        s4.setText((TextView) findViewById(R.id.txtSeccion4));
        s4.setImageA(R.drawable.menu_seccion4);
        s4.setImageB(R.drawable.menu_seccion4b);
        s4.setColorB(getResources().getColor(R.color.color_4));
        s4.setId(4);

        Seccion s5 = new Seccion();
        s5.setContainer((RelativeLayout) findViewById(R.id.seccion_5));
        s5.setIcon((ImageView) findViewById(R.id.imgSeccion5));
        s5.setText((TextView) findViewById(R.id.txtSeccion5));
        s5.setImageA(R.drawable.menu_seccion5);
        s5.setImageB(R.drawable.menu_seccion5b);
        s5.setColorB(getResources().getColor(R.color.color_4));
        s5.setId(5);

        Seccion s6 = new Seccion();
        s6.setContainer((RelativeLayout) findViewById(R.id.seccion_6));
        s6.setIcon((ImageView) findViewById(R.id.imgSeccion6));
        s6.setText((TextView) findViewById(R.id.txtSeccion6));
        s6.setImageA(R.drawable.menu_seccion6);
        s6.setImageB(R.drawable.menu_seccion6b);
        s6.setColorB(getResources().getColor(R.color.color_4));
        s6.setId(6);

        Seccion s7 = new Seccion();
        s7.setContainer((RelativeLayout) findViewById(R.id.seccion_7));
        s7.setIcon((ImageView) findViewById(R.id.imgSeccion7));
        s7.setText((TextView) findViewById(R.id.txtSeccion7));
        s7.setImageA(R.drawable.menu_seccion7);
        s7.setImageB(R.drawable.menu_seccion7b);
        s7.setColorB(getResources().getColor(R.color.color_4));
        s7.setId(7);

        Seccion s8 = new Seccion();
        s8.setContainer((RelativeLayout) findViewById(R.id.seccion_8));
        s8.setIcon((ImageView) findViewById(R.id.imgSeccion7));
        s8.setText((TextView) findViewById(R.id.txtSeccion7));
        s8.setImageA(R.drawable.menu_seccion7);
        s8.setImageB(R.drawable.menu_seccion7b);
        s8.setColorB(getResources().getColor(R.color.color_4));
        s8.setId(8);

        Seccion s9 = new Seccion();
        s9.setContainer((RelativeLayout) findViewById(R.id.seccion_9));
        s9.setIcon((ImageView) findViewById(R.id.imgSeccion7));
        s9.setText((TextView) findViewById(R.id.txtSeccion7));
        s9.setImageA(R.drawable.menu_seccion7);
        s9.setImageB(R.drawable.menu_seccion7b);
        s9.setColorB(getResources().getColor(R.color.color_4));
        s9.setId(9);

        Seccion s10 = new Seccion();
        s10.setContainer((RelativeLayout) findViewById(R.id.seccion_10));
        s10.setIcon((ImageView) findViewById(R.id.imgSeccion7));
        s10.setText((TextView) findViewById(R.id.txtSeccion7));
        s10.setImageA(R.drawable.menu_seccion7);
        s10.setImageB(R.drawable.menu_seccion7b);
        s10.setColorB(getResources().getColor(R.color.color_4));
        s10.setId(10);

        listMenu.add(s1);
        listMenu.add(s2);
        listMenu.add(s3);
        listMenu.add(s4);
        listMenu.add(s5);
        listMenu.add(s6);
        listMenu.add(s7);
        listMenu.add(s8);
        listMenu.add(s9);
        listMenu.add(s10);


        for(Seccion s : listMenu){
            final Seccion d = s;
            s.setColorA(getResources().getColor(R.color.color_5));

            d.getContainer().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for (Seccion g : listMenu) {
                        //g.changeActive(false);
                    }
                    //d.changeActive(true);
                    changeFragment(d.getId());
                }
            });
        }
    }

    public void changeFragment(int posicion){
        boolean fragmentTransaction = true;
        Fragment fragment = null;

        switch (posicion) {
            case 1:
                fragment = CalendarFragment.getInstance();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bar_color_1)));
                break;
            case 2:
                fragment = ExhibitorsFragment.getInstance();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bar_color_2)));
                break;
            case 3:
                fragment = AssistantsFragment.getInstance();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bar_color_3)));
                break;
            case 4:
                fragment = EveningEventsFragment.getInstance();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bar_color_4)));
                break;
            case 5:
                fragmentTransaction = false; //Locations
                break;
            case 6:
                fragment = ProfileFragment.getInstance();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bar_color_4)));
                break;
            case 7:
                fragment = AboutFragment.getInstance();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.bar_color_4)));
                break;
            case 8:
                fragmentTransaction = false; //Survey
                break;
            case 9:
                fragmentTransaction = false; //Notes
                break;
            case 10:
                logOut();
                Intent mainIntent = new Intent().setClass(this, LoginActivity.class);
                startActivity(mainIntent);
            default:
                fragmentTransaction = false;
        }

        if(fragmentTransaction) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if(fragmentActual>1000) {
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            }else{
                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }

            ft.replace(R.id.content_frame, fragment, "fragment");
            ft.commit();

            getSupportActionBar().setTitle(listMenu.get(posicion - 1).getText().getText());
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);

        }
        drawerLayout.closeDrawers();
        fragmentActual = posicion;
    }

    int d = 0;
    Fragment fragment;

    private void setUpNavigationDrawer(){
        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        boolean fragmentTransaction = false;
                        fragment = null;

                        switch (menuItem.getItemId()) {
                            case R.id.menu_seccion_1:
                                fragment = listFragments.get(0);
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_2:
                                fragment = listFragments.get(1);
                                fragmentTransaction = true; d = 2;
                                break;
                            case R.id.menu_seccion_3:
                                fragment = listFragments.get(2);
                                fragmentTransaction = true;
                                break;/*
                            case R.id.menu_opcion_1:
                                Log.i("NavigationView", "Pulsada opción 1");
                                break;
                            case R.id.menu_opcion_2:
                                Log.i("NavigationView", "Pulsada opción 2");
                                break;*/
                        }

                        if(fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch(item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showCalendarDetail(CalendarEntity event){
        Fragment fragment = CalendarDetailFragment.newInstance(event);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        ft.replace(R.id.content_frame, fragment, "fragment");
        ft.commit();


        getSupportActionBar().setTitle("Detail Events");
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_back);
        fragmentActual = 1001;
    }

    public void showExhibitorDetail(Exhibitor exhibitor){
        Fragment fragment = ExhibitorDetailFragment.newInstance(exhibitor);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        ft.replace(R.id.content_frame, fragment, "fragment");
        ft.commit();

        getSupportActionBar().setTitle("Detail Exhibitors");
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_back);
        fragmentActual = 1002;
    }

    public void showEventDetail(Event event){
        Fragment fragment = new EveningEventDetailFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        ft.replace(R.id.content_frame, fragment, "fragment");
        ft.commit();


        getSupportActionBar().setTitle("Detail Exhibitors");
        //getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_back);
        fragmentActual = 1003;
    }

    private void logOut(){
        deleteToken();
        finish();
    }

    private void deleteToken(){
        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=saved_values.edit();
        editor.putString("access_token", "");
        editor.putString("token_type", "");
        editor.putInt("expires_in", 0);
        editor.putString("userName", "");
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        if(fragmentActual == 1001) {
            changeFragment(1);
        }else if(fragmentActual == 1002){
            changeFragment(2);
        }else if(fragmentActual == 1003){
            changeFragment(4);
        }else{
            super.onBackPressed();
        }
    }

}
