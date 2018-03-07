package iosco.app.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.HashMap;

import iosco.app.R;
import iosco.app.activity.MainActivityNew;
import iosco.app.helpers.AppConstants;
import iosco.app.model.entity.AccommodationEntity;
import iosco.app.model.entity.CalendarEntity;
import iosco.app.model.entity.EventLocationEntity;
import iosco.app.service.ApiImplementation;
import iosco.app.utils.Global;
import iosco.app.utils.Helpers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LocationFragment extends Fragment {

    private LinearLayout layoutLocationEvents;
    private LinearLayout layoutLocationHotels;

    private LinearLayout layoutLocationFloors;
    private LinearLayout layoutLocationFloorsContent;

    private View viewLocationFloor1;
    private View viewLocationFloor2;
    private View viewLocationFloor3;
    private View viewLocationFloor4;
    private View viewLocationFloor5;

    private ArrayList<CalendarEntity> arrayEvents;
    private ArrayList<AccommodationEntity> arrayHotels;
    private ArrayList<String> arraySketchers;

    HashMap<Marker,Object> hashMapMarkers = new HashMap<>();

    private GoogleMap map;
    private boolean posicionInicial = false;
    private boolean floorsVisible = true;
    private int imagenSelected;
    private int mapSelected;

    private ImageView imgLocationMap;
    private ProgressBar progressBar;
    private LinearLayout layoutLocationMap;

    private View lastViewSelected;

    private MapFragment fragmentMap;

    private static LocationFragment instance;

    public LocationFragment() {
    }

    public static LocationFragment getInstance(){
        if(instance == null){
            instance = new LocationFragment();
        }
        return instance;
    }

    /*public static LocationFragment getInstance(){
        if(instance == null){
            instance = new LocationFragment();
        }
        return instance;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        map = null;

        View rootView = inflater.inflate(R.layout.fragment_location, container, false);

        fragmentMap = ((MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.fragmentLocationtMap));
        map = fragmentMap.getMap();

        layoutLocationEvents = (LinearLayout)rootView.findViewById(R.id.layoutLocationEvents);
        layoutLocationHotels = (LinearLayout)rootView.findViewById(R.id.layoutLocationHotels);

        layoutLocationFloors = (LinearLayout)rootView.findViewById(R.id.layoutLocationFloors);
        layoutLocationFloorsContent = (LinearLayout)rootView.findViewById(R.id.layoutLocationFloorsContent); int i=1;

        viewLocationFloor1 = rootView.findViewById(R.id.viewLocationFloor1);
        viewLocationFloor2 = rootView.findViewById(R.id.viewLocationFloor2);
        viewLocationFloor3 = rootView.findViewById(R.id.viewLocationFloor3);
        viewLocationFloor4 = rootView.findViewById(R.id.viewLocationFloor4);
        viewLocationFloor5 = rootView.findViewById(R.id.viewLocationFloor5);

        imgLocationMap = (ImageView)rootView.findViewById(R.id.imgLocationMap);
        layoutLocationMap = (LinearLayout)rootView.findViewById(R.id.layoutLocationMap);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBarLocation);

        ApiImplementation.configImageLoader(getActivity());

        /*scaleGestureDetector = new ScaleGestureDetector(getActivity(),new ScaleListener());

        imgLocationMap.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                scaleGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });*/

        imgLocationMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivityNew)getActivity()).showLocationDetail(1, arraySketchers.get(imagenSelected),null);
            }
        });

        layoutLocationEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controllerShow(1);
                controllerBackground(view);
                loadEvents();
            }
        });

        layoutLocationHotels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controllerShow(1);
                controllerBackground(view);
                loadHotels();
            }
        });

        layoutLocationFloors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(floorsVisible)
                    Helpers.collapse(layoutLocationFloorsContent);
                else
                    Helpers.expand(layoutLocationFloorsContent);

                floorsVisible = !floorsVisible;
            }
        });
        viewLocationFloor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFloor(1);
                controllerBackground(view);
            }
        });
        viewLocationFloor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFloor(2);
                controllerBackground(view);
            }
        });
        viewLocationFloor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFloor(3);
                controllerBackground(view);
            }
        });
        viewLocationFloor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFloor(4);
                controllerBackground(view);
            }
        });
        viewLocationFloor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFloor(5);
                controllerBackground(view);
            }
        });

        arraySketchers = new ArrayList<>();
        ApiImplementation.getService().getSketchers().enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                arraySketchers.addAll(response.body());
                for(String x : arraySketchers){
                    Log.i("respuesta13",x);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {

            }
        });

        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtLocationText1)), "HelveticaNeueLTStd-Md.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtLocationText2)), "HelveticaNeueLTStd-Md.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtLocationText3)), "HelveticaNeueLTStd-Md.otf");
        Helpers.changeFont(getActivity(), (TextView) viewLocationFloor1, "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), (TextView) viewLocationFloor2, "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), (TextView) viewLocationFloor3, "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), (TextView) viewLocationFloor4, "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), (TextView) viewLocationFloor5, "HelveticaNeueLTStd-Roman.otf");

        return rootView;
    }

    private void loadFloor(int id){
        controllerShow(2);
        imagenSelected = id-1;
        try {
            switch (id) {
                case 1:
                    ImageLoader.getInstance().displayImage(arraySketchers.get(0), imgLocationMap);
                    break;
                case 2:
                    ImageLoader.getInstance().displayImage(arraySketchers.get(1), imgLocationMap);
                    break;
                case 3:
                    ImageLoader.getInstance().displayImage(arraySketchers.get(2), imgLocationMap);
                    break;
                case 4:
                    ImageLoader.getInstance().displayImage(arraySketchers.get(3), imgLocationMap);
                    break;
                case 5:
                    ImageLoader.getInstance().displayImage(arraySketchers.get(4), imgLocationMap);
                    break;
            }
        }catch (Exception e){}
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

    private void configurarMapa(final int flag){
        hashMapMarkers.clear();
        if (map == null) {
            fragmentMap = ((MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.fragmentLocationtMap));
            map = fragmentMap.getMap();

            if (map != null) {
                map.setMyLocationEnabled(true);
                map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
                    @Override
                    public void onMyLocationChange(Location arg0) {
                     //   LatLng latLng = new LatLng(arg0.getLatitude(), arg0.getLongitude());
                        if (!posicionInicial) {
                         //   map.addMarker(new MarkerOptions().position(latLng).title("You are here"));

                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(arg0.getLatitude(), arg0.getLongitude()), AppConstants.K_MAPS_ZOOM_LEVEL));
                            map.animateCamera(CameraUpdateFactory.zoomTo(AppConstants.K_MAPS_ZOOM_LEVEL), 2000, null);
                            posicionInicial = true;
                        }
                    }
                });
                map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        ((MainActivityNew)getActivity()).showLocationDetail(mapSelected+1, "",null);
                    }
                });
                map.clear();
                if (flag == 1) {
                    for(CalendarEntity c : arrayEvents){
                        crearMarcador(c.getEventLocation().getLatitude(), c.getEventLocation().getLongitude(),c.getTitle());
                    }
                    addDefaultMarker(1);
                }else if(flag == 2){
                    for(AccommodationEntity c : arrayHotels){
                        crearMarcador(c.getLatitude(), c.getLongitude(),c.getName());
                    }
                    addDefaultMarker(2);
                }
                map.setInfoWindowAdapter(new MarkerInfoWindowAdapter());
                mapSelected = flag;
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
            map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    ((MainActivityNew) getActivity()).showLocationDetail(mapSelected + 1, "",null);
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
                    hashMapMarkers.put(crearMarcador(c.getLatitude(), c.getLongitude(), c.getName()), c);
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

    private void controllerBackground(View view){
        if(lastViewSelected != null){
            lastViewSelected.setBackgroundColor(Color.parseColor("#00ffffff"));
        }
        view.setBackgroundColor(Color.parseColor("#ffffff"));
        lastViewSelected = view;
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
