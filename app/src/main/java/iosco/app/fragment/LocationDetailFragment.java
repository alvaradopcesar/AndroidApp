package iosco.app.fragment;


import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.HashMap;

import iosco.app.R;
import iosco.app.helpers.AppConstants;
import iosco.app.model.entity.AccommodationEntity;
import iosco.app.model.entity.CalendarEntity;
import iosco.app.model.entity.EventLocationEntity;
import iosco.app.model.entity.FlightEntity;
import iosco.app.service.ApiImplementation;
import iosco.app.utils.Global;
import iosco.app.utils.TouchImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationDetailFragment extends Fragment {

    private int id;
    private String url;
    private GoogleMap map;
    private boolean posicionInicial = false;

    private ArrayList<CalendarEntity> arrayEvents;
    private ArrayList<AccommodationEntity> arrayHotels;
    private ArrayList<String> arraySketchers;

    HashMap<Marker,Object> hashMapMarkers = new HashMap<>();

    private TouchImageView imgLocationMap;
    private ProgressBar progressBar;
    private LinearLayout layoutLocationMap;

    private MapFragment fragmentMap;

    private int mapSelected;

    public LocationDetailFragment() {}

    public static LocationDetailFragment newInstance(int id, String url) {
        LocationDetailFragment fragment = new LocationDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("id", id);
        bundle.putSerializable("url",url);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_location_detail, container, false);

        posicionInicial = false;

        fragmentMap = ((MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.fragmentLocationtMapDetail));
        map = fragmentMap.getMap();

        id = (int) getArguments().getSerializable("id");
        url = (String) getArguments().getSerializable("url");

        imgLocationMap = (TouchImageView)rootView.findViewById(R.id.imgLocationMap);
        layoutLocationMap = (LinearLayout)rootView.findViewById(R.id.layoutLocationMapDetail);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBarLocation);

        controllerShow(1);

        ApiImplementation.configImageLoader(getActivity());

        if(id == 1){
            loadImage();
        }else if(id == 2){
            loadEvents();
        }else if(id == 3){
            loadHotels();
        }

        imgLocationMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*imgLocationMap = null;
                imgLocationMap = (TouchImag eView) getView().findViewById(R.id.imgLocationMap);
                loadImage();*/
                //imgLocationMap.reset();
                //imgLocationMap.resetStatic();

            }
        });


        return rootView;
    }

    private void loadImage(){
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
    }

    private void loadEvents(){
        if(arrayEvents == null) {
            ApiImplementation.getService().getEveningEvents(Global.getUserToken(getActivity())).enqueue(new Callback<ArrayList<CalendarEntity>>() {
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
            ApiImplementation.getService().getHotels(Global.getUserToken(getActivity())).enqueue(new Callback<ArrayList<AccommodationEntity>>() {
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
        hashMapMarkers.clear();
        if (map == null) {

            fragmentMap = ((MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.fragmentLocationtMapDetail));
            map = fragmentMap.getMap();

            if (map != null) {
                map.setMyLocationEnabled(true);
                map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location arg0) {
                        if(!posicionInicial){
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arg0.getLatitude(), arg0.getLongitude()), AppConstants.K_MAPS_ZOOM_LEVEL));
                            map.animateCamera(CameraUpdateFactory.zoomTo(AppConstants.K_MAPS_ZOOM_LEVEL), 2000, null);
                            posicionInicial = true;
                        }
                    }
                });
                map.clear();
                if (flag == 1) {
                    for(CalendarEntity c : arrayEvents){
                        hashMapMarkers.put(crearMarcador(c.getEventLocation().getLatitude(), c.getEventLocation().getLongitude(), c.getTitle()), c);
                    }
                    addDefaultMarker(1);
                }else if(flag == 2){
                    for(AccommodationEntity c : arrayHotels){
                        hashMapMarkers.put(crearMarcador(c.getLatitude(), c.getLongitude(),c.getName()),c);
                    }
                    addDefaultMarker(2);
                }
                controllerShow(3);
            }
        }else{
            map.setMyLocationEnabled(true);
            map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                @Override
                public void onMyLocationChange(Location arg0) {
                    if (!posicionInicial) {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arg0.getLatitude(), arg0.getLongitude()), AppConstants.K_MAPS_ZOOM_LEVEL));
                        map.animateCamera(CameraUpdateFactory.zoomTo(AppConstants.K_MAPS_ZOOM_LEVEL), 2000, null);
                        posicionInicial = true;
                    }
                }
            });
            map.clear();
            if (flag == 1) {
                //map.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);
                for(CalendarEntity c : arrayEvents){
                    hashMapMarkers.put(crearMarcador(c.getEventLocation().getLatitude(), c.getEventLocation().getLongitude(), c.getTitle()), c);
                }
                addDefaultMarker(1);
            }else if(flag == 2){
                for(AccommodationEntity c : arrayHotels){
                    hashMapMarkers.put(crearMarcador(c.getLatitude(), c.getLongitude(),c.getName()),c);
                }
                addDefaultMarker(2);
            }

            map.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
            mapSelected = flag;
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

    private void addDefaultMarker(int type){
        if(type == 1) {
            CalendarEntity x = new CalendarEntity();
            x.setName("Lima Convention Center");
            x.setEventLocation(new EventLocationEntity());
            x.getEventLocation().setDescription("Av Javier Prado Este 2479");
            x.getEventLocation().setLatitude(-12.086420);
            x.getEventLocation().setLongitude(-77.000834);
            arrayEvents.add(x);
            Marker m = map.addMarker(new MarkerOptions().position(
                    new LatLng(x.getEventLocation().getLatitude(), x.getEventLocation().getLongitude()))
                    .title(x.getName()));
            m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            hashMapMarkers.put(m, x);
        }else{
            AccommodationEntity x = new AccommodationEntity();
            x.setName("Lima Convention Center");
            x.setAddress("Av Javier Prado Este 2479");
            x.setPhoneNumber("");
            x.setLatitude(-12.086420);
            x.setLongitude(-77.000834);
            arrayHotels.add(x);
            Marker m = map.addMarker(new MarkerOptions().position(
                    new LatLng(x.getLatitude(), x.getLongitude()))
                    .title(x.getName()));
            m.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            hashMapMarkers.put(m, x);
        }

    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        if(fragmentMap != null)
        getActivity().getFragmentManager().beginTransaction().remove(fragmentMap).commit();
    }

    public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter
    {
        public MarkerInfoWindowAdapter(){}

        @Override
        public View getInfoWindow(Marker marker){return null;}

        @Override
        public View getInfoContents(Marker marker)
        {
            View v = getActivity().getLayoutInflater().inflate(R.layout.model_map_detail, null);
            TextView txtMapDetail1 = (TextView)v.findViewById(R.id.txtMapDetail1);
            TextView txtMapDetail2 = (TextView)v.findViewById(R.id.txtMapDetail2);
            TextView txtMapDetail3 = (TextView)v.findViewById(R.id.txtMapDetail3);

            if(mapSelected == 1){
                final CalendarEntity b = (CalendarEntity)hashMapMarkers.get(marker);
                txtMapDetail1.setText(b.getName());
                txtMapDetail2.setText(b.getEventLocation().getDescription());
            }else{
                final AccommodationEntity b = (AccommodationEntity)hashMapMarkers.get(marker);
                txtMapDetail1.setText(b.getName());
                txtMapDetail2.setText(b.getAddress());
                txtMapDetail3.setText(b.getPhoneNumber());
            }
            return v;
        }
    }
}
