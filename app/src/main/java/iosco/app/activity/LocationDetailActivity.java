package iosco.app.activity;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import iosco.app.R;
import iosco.app.model.entity.AccommodationEntity;
import iosco.app.model.entity.CalendarEntity;
import iosco.app.service.ApiImplementation;
import iosco.app.utils.Global;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LocationDetailActivity extends AppCompatActivity {

    private int id;
    private String url;
    private GoogleMap map;
    private boolean posicionInicial = false;

    private ArrayList<CalendarEntity> arrayEvents;
    private ArrayList<AccommodationEntity> arrayHotels;
    private ArrayList<String> arraySketchers;

    private ImageView imgLocationMap;
    private ProgressBar progressBar;
    private LinearLayout layoutLocationMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_detail);

        Bundle b = getIntent().getExtras();
        id = b.getInt("id");
        url = b.getString("url");

        imgLocationMap = (ImageView)findViewById(R.id.imgLocationMap);
        layoutLocationMap = (LinearLayout)findViewById(R.id.layoutLocationMapDetail);
        progressBar = (ProgressBar)findViewById(R.id.progressBarLocation);

        controllerShow(1);

        ApiImplementation.configImageLoader(this);

        if(id == 1){
            ImageLoader.getInstance().displayImage(url, imgLocationMap, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String s, View view) {}
                @Override
                public void onLoadingFailed(String s, View view, FailReason failReason) {}
                @Override
                public void onLoadingComplete(String s, View view, Bitmap bitmap) {controllerShow(2);}
                @Override
                public void onLoadingCancelled(String s, View view) {}
            });
        }else if(id == 2){
            loadEvents();
        }else if(id == 3){
            loadHotels();
        }
    }

    private void loadEvents(){
        if(arrayEvents == null) {
            ApiImplementation.getService().getEveningEvents(Global.getUserToken(this)).enqueue(new Callback<ArrayList<CalendarEntity>>() {
                @Override
                public void onResponse(Call<ArrayList<CalendarEntity>> call, Response<ArrayList<CalendarEntity>> response) {
                    arrayEvents = new ArrayList();
                    arrayEvents.addAll(response.body());
                    posicionInicial = false;
                    configurarMapa(1);
                }

                @Override
                public void onFailure(Call<ArrayList<CalendarEntity>> call, Throwable t) {

                }
            });
        }else{
            configurarMapa(1);
        }
    }

    private void loadHotels(){
        if(arrayHotels == null) {
            ApiImplementation.getService().getHotels(Global.getUserToken(this)).enqueue(new Callback<ArrayList<AccommodationEntity>>() {
                @Override
                public void onResponse(Call<ArrayList<AccommodationEntity>> call, Response<ArrayList<AccommodationEntity>> response) {
                    arrayHotels = new ArrayList();
                    arrayHotels.addAll(response.body());
                    configurarMapa(2);
                }

                @Override
                public void onFailure(Call<ArrayList<AccommodationEntity>> call, Throwable t) {

                }
            });
        }else{
            configurarMapa(2);
        }
    }

    private void configurarMapa(int flag){
        if (map == null) {
            map = ((MapFragment)getFragmentManager().findFragmentById(R.id.fragmentLocationtMapDetail)).getMap();
            map.setMyLocationEnabled(true);

            if (map != null) {
                map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location arg0) {
                        if(!posicionInicial){
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arg0.getLatitude(), arg0.getLongitude()), 17));
                            map.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
                            posicionInicial = true;
                        }
                    }
                });
                map.clear();
                if (flag == 1) {
                    for(CalendarEntity c : arrayEvents){
                        crearMarcador(c.getEventLocation().getLatitude(), c.getEventLocation().getLongitude(),c.getTitle());
                    }
                }else if(flag == 2){
                    for(AccommodationEntity c : arrayHotels){
                        crearMarcador(c.getLatitude(), c.getLongitude(),c.getName());
                    }
                }
                controllerShow(3);
            }
        }else{
            map.clear();
            if (flag == 1) {
                for(CalendarEntity c : arrayEvents){
                    crearMarcador(c.getEventLocation().getLatitude(), c.getEventLocation().getLongitude(),c.getTitle());
                }
            }else if(flag == 2){
                for(AccommodationEntity c : arrayHotels){
                    crearMarcador(c.getLatitude(), c.getLongitude(),c.getName());
                }
            }
            controllerShow(3);
        }
    }

    private Marker crearMarcador(double x, double y, String name){
        Marker m = map.addMarker((new MarkerOptions().position(new LatLng(x, y)).title(name)));
        return m;
    }

    private void controllerShow(int i){
        switch (i){
            case 1:
                progressBar.setVisibility(View.VISIBLE);
                layoutLocationMap.setVisibility(View.GONE);
                imgLocationMap.setVisibility(View.GONE);
                break;
            case 2:
                progressBar.setVisibility(View.GONE);
                layoutLocationMap.setVisibility(View.GONE);
                imgLocationMap.setVisibility(View.VISIBLE);
                break;
            case 3:
                progressBar.setVisibility(View.GONE);
                layoutLocationMap.setVisibility(View.VISIBLE);
                imgLocationMap.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        map = null;
    }
}
