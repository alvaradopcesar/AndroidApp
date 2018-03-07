package iosco.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import iosco.app.R;
import iosco.app.model.entity.CalendarEntity;
import iosco.app.utils.Helpers;

/**
 * A simple {@link Fragment} subclass.
 */
public class EveningEventDetailFragment extends Fragment {

    private CalendarEntity calendar;
    private GoogleMap map;
    private boolean posicionInicial = false;

    private MapFragment fragmentMap;

    public EveningEventDetailFragment() {
    }

    public static EveningEventDetailFragment newInstance(CalendarEntity calendar) {
        EveningEventDetailFragment fragment = new EveningEventDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("calendar", calendar);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_evening_event_detail, container, false);

        fragmentMap = ((MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.fragmentEveningEventMap));
        map = fragmentMap.getMap();

        calendar = (CalendarEntity) getArguments().getSerializable("calendar");

        ((TextView)rootView.findViewById(R.id.txtTitle)).setText(calendar.getTitle());
        ((TextView)rootView.findViewById(R.id.txtDuration)).setText(calendar.getDuration());
        ((TextView)rootView.findViewById(R.id.txtDate)).setText(calendar.getDate());
        ((TextView)rootView.findViewById(R.id.txtLocation)).setText(calendar.getEventLocation().getDescription());
        ((TextView)rootView.findViewById(R.id.txtSketchDescription)).setText(calendar.getSketchDescription());

        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtTitle)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView)rootView.findViewById(R.id.txtDuration)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView)rootView.findViewById(R.id.txtDate)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView)rootView.findViewById(R.id.txtLocation)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.lblLocation)), "HelveticaNeueLTStd-Bd.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtSketchDescription)), "HelveticaNeueLTStd-Roman.otf");

        configurarMapa();

        return rootView;
    }

    private void configurarMapa(){
        if (map == null) {
            map = ((MapFragment)getActivity().getFragmentManager().findFragmentById(R.id.fragmentEveningEventMap)).getMap();
            if (map != null) {
                map.setMyLocationEnabled(true);
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(calendar.getEventLocation().getLatitude(), calendar.getEventLocation().getLongitude()), 17));
                map.addMarker((new MarkerOptions().position(
                        new LatLng(calendar.getEventLocation().getLatitude(), calendar.getEventLocation().getLongitude()))));
            }
        }
        if (map != null) {
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(calendar.getEventLocation().getLatitude(), calendar.getEventLocation().getLongitude()), 17));
            map.addMarker((new MarkerOptions().position(
                    new LatLng(calendar.getEventLocation().getLatitude(), calendar.getEventLocation().getLongitude()))));
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        if(fragmentMap != null)
            getActivity().getFragmentManager().beginTransaction().remove(fragmentMap).commit();
    }

}
