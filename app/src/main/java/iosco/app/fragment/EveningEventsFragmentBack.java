package iosco.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import iosco.app.R;
import iosco.app.adapters.EveningEventAdapter;
import iosco.app.model.entity.Event;
import iosco.app.model.entity.Head;
import iosco.app.model.entity.Row;

/**
 * A simple {@link Fragment} subclass.
 */
public class EveningEventsFragmentBack extends Fragment {

    private ListView lstEvents;

    private ArrayList<Row> listRows;
    private ArrayList<Event> listEvents;
    private EveningEventAdapter eveningEventAdapter;
    private static EveningEventsFragmentBack instance;

    public static EveningEventsFragmentBack getInstance(){
        if(instance == null){
            instance = new EveningEventsFragmentBack();
        }
        return instance;
    }

    public EveningEventsFragmentBack() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_evening_events, container, false);

        listEvents = new ArrayList<>();
        listRows = new ArrayList<>();

        //lstEvents = (ListView)rootView.findViewById(R.id.lstEvents);

        eveningEventAdapter = new EveningEventAdapter(getActivity(),listRows);
        lstEvents.setAdapter(eveningEventAdapter);

        lstEvents.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(listRows.get(i) instanceof Event){
                    //((MainActivityNew)getActivity()).showEventDetail((Event)listRows.get(i));
                }
            }
        });

        loadRows();

        return rootView;
    }

    private void loadEvents(){

    }

    private void loadRows(){
        for(int i=0; i < 4 ; i++){
            Head h = new Head();
            h.setName("09:00 - 10:00 AM");

            listRows.add(h);

            for(int j=0; j < 2 ; j++){
                Event e = new Event();
                e.setName("Amon Allardice");
                listRows.add(e);
            }
        }
        eveningEventAdapter.notifyDataSetChanged();
    }


}
