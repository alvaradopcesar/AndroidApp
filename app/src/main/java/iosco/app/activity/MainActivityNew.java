package iosco.app.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.MemoryCacheUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import iosco.app.R;
import iosco.app.model.entity.CalendarEntity;
import iosco.app.model.entity.Event;
import iosco.app.model.entity.Exhibitor;
import iosco.app.model.entity.Seccion;
import iosco.app.model.entity.UserInfoEntity;
import iosco.app.fragment.AboutFragment;
import iosco.app.fragment.AssistantsFragment;
import iosco.app.fragment.AssistantsSearchFragment;
import iosco.app.fragment.BackgroundFragment;
import iosco.app.fragment.CalendarFragment;
import iosco.app.fragment.CalendarSearchFragment;
import iosco.app.fragment.CalenderFragment_;
import iosco.app.fragment.CalendarDetailFragment;
import iosco.app.fragment.EveningEventDetailFragment;
import iosco.app.fragment.EveningEventsFragment;
import iosco.app.fragment.ExhibitorDetailFragment;
import iosco.app.fragment.ExhibitorSearchFragment;
import iosco.app.fragment.ExhibitorsFragment;
import iosco.app.fragment.HomeFragment;
import iosco.app.fragment.LocationDetailFragment;
import iosco.app.fragment.LocationFragment;
import iosco.app.fragment.NotesFragment;
import iosco.app.fragment.ProfileFragment;
import iosco.app.fragment.SurveyFragment;
import iosco.app.service.ApiImplementation;
import iosco.app.utils.Global;
import iosco.app.utils.Helpers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivityNew extends AppCompatActivity {

    private Objects dumbObject;

    private DrawerLayout drawerLayout;
    private NavigationView navView;

    private Toolbar appbar;


    private ArrayList<Seccion> listMenu;
    private List<Fragment> listFragments;
    private int fragmentActual = -1;

    private ImageView ic_menu;
    private ImageView ico_home_search;
    private TextView txtHomeText;
    private LinearLayout layoutSearch;
    private LinearLayout layoutActionBar;
    private LinearLayout layoutHomeLogo;
    private ImageView imgHeaderPhoto;
    private TextView txtHeaderName;
    private ProgressBar progressHeader;

    private UserInfoEntity userInfo;
    private CalendarEntity calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navView = (NavigationView) findViewById(R.id.navview);
        ic_menu = (ImageView) findViewById(R.id.ic_menu);
        txtHomeText = (TextView) findViewById(R.id.txtHomeText);
        layoutSearch = (LinearLayout) findViewById(R.id.layoutSearch);
        layoutActionBar = (LinearLayout) findViewById(R.id.layoutActionBar);
        layoutHomeLogo = (LinearLayout) findViewById(R.id.layoutHomeLogo);
        ico_home_search = (ImageView) findViewById(R.id.ico_home_search);
        txtHeaderName = (TextView) findViewById(R.id.txtHeaderName);
        imgHeaderPhoto = (ImageView) findViewById(R.id.imgHeaderPhoto);
        progressHeader = (ProgressBar)findViewById(R.id.progressHeader);

        listFragments = new ArrayList<>();
        /*listFragments.add(CalenderFragment_.getInstance());
        listFragments.add(ExhibitorsFragment.getInstance());
        listFragments.add(AssistantsFragment.getInstance());
        listFragments.add(EveningEventsFragment.getInstance());*/

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new HomeFragment())
                .commit();

        setUpMenu();

        ic_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentActual > 100)
                    onBackPressed();
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        findViewById(R.id.ic_menu_extends).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentActual > 100)
                    onBackPressed();
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        findViewById(R.id.ic_menu_extends2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentActual > 100)
                    onBackPressed();
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        ico_home_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(fragmentActual + 100);
            }
        });

        changeFragment(0);
        FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
        ft2.replace(R.id.content_background, BackgroundFragment.newInstance(1), "fragment");
        ft2.commit();

        Helpers.changeFont(this, txtHomeText, "HelveticaNeueLTStd-Lt.otf");
        Helpers.changeFont(this, txtHeaderName, "HelveticaNeueLTStd-Bd.otf");

        ApiImplementation.configImageLoader(this);
        Log.i("resultado77", "getUserInfo loading");
        ApiImplementation.getService().getUserInfo(Global.getUserToken(this)).enqueue(new Callback<UserInfoEntity>() {
            @Override
            public void onResponse(Call<UserInfoEntity> call, Response<UserInfoEntity> response) {
                Log.i("resultado77","getUserInfo finish");
                userInfo = response.body();
                Log.i("resultado77","getUserInfo convertBody");
                if(userInfo != null) {
                    Log.i("resultado77", "getUserInfo != null Ok");
                    saveConfigData(userInfo);
                   if(!isSurveyEnabled())
                   {
                       drawerLayout.findViewById(R.id.seccion_8).setVisibility(View.GONE);
                   }

                    txtHeaderName.setText(userInfo.getFirstName() + " " + userInfo.getLastName());

                    if(userInfo.isHasProfilePicture()) {
                        //ImageLoader.getInstance().displayImage(ApiImplementation.getBaseUrl() + "api/Account/ProfilePicture", imgHeaderPhoto);
                        ImageLoader.getInstance().displayImage(ApiImplementation.getBaseUrl() + "api/Account/ProfilePicture", imgHeaderPhoto, new SimpleImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String imageUri, View view) {
                                Log.i("resultado77", "getUserInfo onFailure");
                                ;
                            }

                            @Override
                            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                                Log.i("resultado77", "foto onFailure" + failReason.toString());
                                ;
                            }

                            @Override
                            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                                progressHeader.setVisibility(View.GONE);
                                imgHeaderPhoto.setVisibility(View.VISIBLE);
                            }
                        });
                    }else{
                        progressHeader.setVisibility(View.GONE);
                        imgHeaderPhoto.setVisibility(View.VISIBLE);
                    }
                }
            }
            @Override
            public void onFailure(Call<UserInfoEntity> call, Throwable t) {
                Log.i("resultado77","getUserInfo onFailure");;
                t.printStackTrace();
            }
        });

        if(getResources().getDisplayMetrics().density == DisplayMetrics.DENSITY_MEDIUM){ Log.i("resultado20","DENSITY_MEDIUM");
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                Log.i("resultado20","ORIENTATION_LANDSCAPE");
            }
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.i("resultado20","landscape");
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            Log.i("resultado20", "portrait");
        }
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
            Helpers.changeFont(this, s.getText(), "HelveticaNeueLTStd Lt.otf");

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
        String txt1 = "",txt2 = "";
        boolean search = false;

        switch (posicion) {
            case 0:
                fragment = new HomeFragment(); txt1 = "IOSCO";
                break;
            case 1:
                fragment = CalendarFragment.getInstance();
                txt1 = "Conference"; txt2="Agenda"; search = true;
                break;
            case 2:
                fragment = ExhibitorsFragment.getInstance();
                txt1 = "Speakers"; search = true;
                break;
            case 3:
                fragment = AssistantsFragment.getInstance();
                txt1 = "Attendees"; search = true;
                break;
            case 4:
                fragment = EveningEventsFragment.getInstance();
                txt1 = "Evening"; txt2 = "Events"; search = true;
                break;
            case 5:
                //fragment = LocationFragment.getInstance(); txt1 = "Location";
                fragment = LocationFragment.getInstance(); txt1 = "Locations";
                break;
            case 6:
                fragment = ProfileFragment.newInstance(userInfo);
                txt1 = "Profile";
                break;
            case 7:
                fragment = AboutFragment.getInstance(); txt1 = "About us";
                break;
            case 8:
                fragment = SurveyFragment.getInstance(); txt1 = "Survey"; //Survey
                break;
            case 9:
                fragment = NotesFragment.getInstance(); txt1 = "My Notes";  //Notes
                break;
            case 10:
                logOut();
                Intent mainIntent = new Intent().setClass(this, LoginActivity.class);
                startActivity(mainIntent);
            case 101:
                fragment = CalendarSearchFragment.newInstance(CalendarFragment.getList());
                txt1 = "Search Conference"; txt2="Agenda";
                break;
            case 102:
                fragment = ExhibitorSearchFragment.newInstance(ExhibitorsFragment.getList());
                txt1 = "Search"; txt2="Speakers";
                break;
            case 103:
                fragment = AssistantsSearchFragment.newInstance(AssistantsFragment.getList());
                txt1 = "Search"; txt2="Attendees";
                break;
            default:
                fragmentTransaction = false;
        }

        if(fragmentTransaction) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

            if(fragmentActual != -1)
            if(posicion == 0 || fragmentActual > 100) {
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
            }else{
                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            }

            ft.replace(R.id.content_frame, fragment, "fragment");
            ft.commit();

            if(posicion == 0 && fragmentActual > 0){
                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                ft2.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                ft2.replace(R.id.content_background, BackgroundFragment.newInstance(1), "fragment");
                ft2.commit();
            }else if(fragmentActual == 0 && posicion > 0){
                FragmentTransaction ft2 = getSupportFragmentManager().beginTransaction();
                ft2.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
                ft2.replace(R.id.content_background, BackgroundFragment.newInstance(2), "fragment");
                ft2.commit();
            }

            changeHome(txt1,txt2,search,posicion,false);
        }
        drawerLayout.closeDrawers();
        fragmentActual = posicion;
    }

    int d = 0;
    Fragment fragment;


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
        Fragment fragment = CalendarDetailFragment.getInstance(event);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if(fragmentActual == 1)
            ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        else
            ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

        ft.replace(R.id.content_frame, fragment, "fragment");
        ft.commit();

        changeHome("Conference Agenda", "Details", false, 1,true);

        fragmentActual = 1001;
    }

    public void showExhibitorDetail(Exhibitor exhibitor){
        Fragment fragment = ExhibitorDetailFragment.newInstance(exhibitor);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        ft.replace(R.id.content_frame, fragment, "fragment");
        ft.commit();

        changeHome("Speaker's", "Details", false, 2, true);

        fragmentActual = 1002;
    }

    public void showEveningDetail(CalendarEntity event){
        Fragment fragment = EveningEventDetailFragment.newInstance(event);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        ft.replace(R.id.content_frame, fragment, "fragment");
        ft.commit();

        changeHome("Evening Events", "Details", false, 4, true);

        fragmentActual = 1004;
    }

    public void showLocationDetail(int id, String url, CalendarEntity currentCalendar){
        Fragment fragment = LocationDetailFragment.newInstance(id, url);
    this.calendar = currentCalendar;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        ft.replace(R.id.content_frame, fragment, "fragment");
        ft.commit();

        changeHome("Locations", "", false, 5, true);
        if(currentCalendar!=null){
            fragmentActual = 10001;
        }else {
            fragmentActual = 1005;
        }
    }

    public void showCalendarNote(int idNote, CalendarEntity calendar){
        Fragment fragment = NotesFragment.newInstance(idNote,calendar);
        this.calendar = calendar;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
        ft.replace(R.id.content_frame, fragment, "fragment");
        ft.commit();

        changeHome("My notes", "", false, 9, true);

        fragmentActual = 10001;
    }

    public void manageSubContainerSurvey(View v){
        SurveyFragment.getInstance().manageSubContainer(v);
    }

    public void manageStarsSurvey(View v){
        SurveyFragment.getInstance().manageStars(v);
    }

    private void logOut(){
        deleteToken();
     ImageLoader.getInstance().clearMemoryCache();
        ImageLoader.getInstance().clearDiskCache();
        finish();
    }



    private void saveConfigData(UserInfoEntity userInfo){
        SharedPreferences saved_values = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor=saved_values.edit();
        editor.putLong("surveyDateEnabled", userInfo.getSurveyEnableDate().getTime());
        editor.commit();
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
        // + 1000 = details
        // + 100 = search
        if(fragmentActual == 10001) {
            showCalendarDetail(calendar);
        }else if(fragmentActual == 1001) {
            changeFragment(1);
        }else if(fragmentActual == 1002){
            changeFragment(2);
        }else if(fragmentActual == 1003){
            changeFragment(4);
        }else if(fragmentActual == 1004){
            changeFragment(4);
        }else if(fragmentActual == 1005){
            changeFragment(5);
        }else if(fragmentActual == 101){
            changeFragment(1);
        }else if(fragmentActual == 102){
            changeFragment(2);
        }else if(fragmentActual == 103){
            changeFragment(3);
        }else{
            if(fragmentActual > 0) {
                changeFragment(0);
            }else{
                super.onBackPressed();
            }
        }
    }

    /*

    Agregado por E. Tito


     */

    private boolean isSurveyEnabled(){

        long currentTime = (new Date()).getTime();
        Log.d("currentdate",currentTime+"- date:"+ (new Date(currentTime)).toString());
        long surveyTime = Global.getSurveyEnableDate(this);
        Log.d("surveyTime",surveyTime+"- date:"+(new Date(surveyTime).toString()));

        return currentTime>surveyTime;




    }

    /*


    Fin Agregado
     */

    private void changeHome(String txt1, String txt2, boolean search, int seccion, boolean arrow){
        if(search){
            layoutSearch.setVisibility(View.VISIBLE);
        }else{
            layoutSearch.setVisibility(View.GONE);
        }

        if(seccion == 0) {
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)txtHomeText.getLayoutParams();
            lp.topMargin = 2;
            txtHomeText.setGravity(Gravity.TOP | Gravity.RIGHT);
            txtHomeText.setTextSize(getResources().getDimension(R.dimen.action_bar_text_iosco));
            layoutSearch.setVisibility(View.GONE);
            layoutHomeLogo.setVisibility(View.VISIBLE);
            txtHomeText.setVisibility(View.GONE);
        }else if(txt2.equals("")){
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)txtHomeText.getLayoutParams();
            lp.topMargin = 4;
            txtHomeText.setGravity(Gravity.TOP | Gravity.RIGHT);
            txtHomeText.setText(txt1);
            txtHomeText.setTextSize(getResources().getDimension(R.dimen.action_bar_text_line));
            txtHomeText.setVisibility(View.VISIBLE);
            layoutHomeLogo.setVisibility(View.GONE);
        }else{
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)txtHomeText.getLayoutParams();
            lp.topMargin = 0;
            txtHomeText.setGravity(Gravity.CENTER|Gravity.RIGHT);
            txtHomeText.setText(txt1 + "\n" + txt2);
            txtHomeText.setTextSize(getResources().getDimension(R.dimen.action_bar_text_multiline));
            txtHomeText.setVisibility(View.VISIBLE);
            layoutHomeLogo.setVisibility(View.GONE);
        }

        /*if(seccion == 0)
            findViewById(R.id.layoutContentViewHome).setBackgroundResource(R.drawable.background_home);
        else
            findViewById(R.id.layoutContentViewHome).setBackgroundColor(Color.parseColor("#F0FBFC"));*/

        switch(seccion) {
            case 0:
                layoutActionBar.setBackgroundResource(R.drawable.content_background_0);
                ic_menu.setImageResource(R.drawable.ico_home_0);
                break;
            case 1:
                layoutActionBar.setBackgroundResource(R.drawable.content_background_1);
                if(!arrow)
                    ic_menu.setImageResource(R.drawable.ico_home_1);
                else
                    ic_menu.setImageResource(R.drawable.ico_home_arrow_1);
                break;
            case 2:
                layoutActionBar.setBackgroundResource(R.drawable.content_background_2);
                if(!arrow)
                    ic_menu.setImageResource(R.drawable.ico_home_2);
                else
                    ic_menu.setImageResource(R.drawable.ico_home_arrow_2);
                    break;
            case 3:
                layoutActionBar.setBackgroundResource(R.drawable.content_background_3);
                ic_menu.setImageResource(R.drawable.ico_home_3); break;
            case 4:
                layoutActionBar.setBackgroundResource(R.drawable.content_background_4);
                if(!arrow)
                    ic_menu.setImageResource(R.drawable.ico_home_4);
                else
                    ic_menu.setImageResource(R.drawable.ico_home_arrow_4);
                break;
            case 5:
                layoutActionBar.setBackgroundResource(R.drawable.content_background_5);
                if(!arrow)
                    ic_menu.setImageResource(R.drawable.ico_home_5);
                else
                    ic_menu.setImageResource(R.drawable.ico_home_arrow_5);
                break;
            case 6:
                layoutActionBar.setBackgroundResource(R.drawable.content_background_6);
                ic_menu.setImageResource(R.drawable.ico_home_6); break;
            case 7:
                layoutActionBar.setBackgroundResource(R.drawable.content_background_6);
                ic_menu.setImageResource(R.drawable.ico_home_6); break;
            case 8:
                layoutActionBar.setBackgroundResource(R.drawable.content_background_7);
                ic_menu.setImageResource(R.drawable.ico_home_7); break;
            case 9:
                layoutActionBar.setBackgroundResource(R.drawable.content_background_7);
                if(!arrow)
                    ic_menu.setImageResource(R.drawable.ico_home_7);
                else
                    ic_menu.setImageResource(R.drawable.ico_home_arrow_4);
                break;
            case 101:
                layoutActionBar.setBackgroundResource(R.drawable.content_background_1);
                ic_menu.setImageResource(R.drawable.ico_home_arrow_1);
                break;
            case 102:
                layoutActionBar.setBackgroundResource(R.drawable.content_background_2);
                ic_menu.setImageResource(R.drawable.ico_home_arrow_2);
                break;
            case 103:
                layoutActionBar.setBackgroundResource(R.drawable.content_background_3);
                ic_menu.setImageResource(R.drawable.ico_home_arrow_4);
                break;
        }
    }
}
