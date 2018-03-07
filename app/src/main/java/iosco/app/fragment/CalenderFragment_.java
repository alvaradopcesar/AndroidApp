package iosco.app.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import iosco.app.R;
import iosco.app.adapters.CalenderAdapter;
import iosco.app.model.entity.Day;
import iosco.app.model.entity.File;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalenderFragment_.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalenderFragment_#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalenderFragment_ extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalenderFragment.
     */
    // TODO: Rename and change types and number of parameter

    private static CalenderFragment_ instance = null;

    private static ArrayList<Day> listDays;
    private ArrayList<File> listFiles;
    private ArrayList<File> listFilesBackup;

    private ListView lstCalender;
    private CalenderAdapter calenderAdapter;
    private int dayActual;
    private int monthActual;

    private File fileActual;
    private ArrayList<File> eventsActual;

    public static CalenderFragment_ getInstance() {
        if(instance == null) {
            instance = new CalenderFragment_();
        }

        return instance;
    }

    /*public static CalenderFragment newInstance(String param1, String param2) {
        CalenderFragment fragment = new CalenderFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    private CalenderFragment_() {
        // Required empty public constructor
    }

    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        dayActual = 8;
        monthActual = 5;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_calender_, container, false);

        lstCalender = (ListView)rootView.findViewById(R.id.lstCalender);

        initComponents();

        return rootView;
    }

    private void initComponents(){
        listFiles = new ArrayList<>();
        listFilesBackup = new ArrayList<>();
        eventsActual = new ArrayList<>();



        calenderAdapter = new CalenderAdapter(getActivity(),listFiles);
        lstCalender.setAdapter(calenderAdapter);

        calenderAdapter.notifyDataSetChanged();

        lstCalender.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (listFiles.get(position).isEvent()) {
                    // show detail event
                    showEventDetail(listFiles.get(position));
                } else {
                    // collapse or show events by day
                    showEvents(position);
                }
            }
        });

        if(listFilesBackup.size() == 0)
            loadData();
    }

    private void showEventDetail(File event){
        //((MainActivity)getActivity()).showCalendarDetail(event);
    }

    private void showEvents(int position){
        // si no hay dia seleccionado, muestro el nuevo
        if(dayActual == 0 && monthActual == 0){
            if(getEventsByDay(listFiles.get(position)).size() > 0) {
                dayActual = listFiles.get(position).getIdDay();
                monthActual = listFiles.get(position).getIdMonth();
                eventsActual.clear();
                eventsActual.addAll(getEventsByDay(listFiles.get(position)));
                listFiles.addAll(position + 1, eventsActual);
                listFiles.get(position).setIsShow(true);
            }
        }
        // si selecciono el dia que está mostrado, lo oculto
        else if(dayActual == listFiles.get(position).getIdDay() && monthActual == listFiles.get(position).getIdMonth()){
            listFiles.get(position).setIsShow(false);
            listFiles.removeAll(eventsActual);
            eventsActual.clear();
            dayActual = 0; monthActual =0;
        }
        // si selecciono un dia, pero ya habian cosas mostradas
        else if(dayActual != listFiles.get(position).getIdDay() || monthActual != listFiles.get(position).getIdMonth()){
            if(getEventsByDay(listFiles.get(position)).size() > 0){
                File daySelected = new File();
                daySelected = listFiles.get(position);
                desactivateDays();
                daySelected.setIsShow(true);
                listFiles.removeAll(eventsActual);
                eventsActual.clear();
                eventsActual.addAll(getEventsByDay(daySelected));
                listFiles.addAll(listFiles.indexOf(daySelected)+1,eventsActual);

                dayActual = daySelected.getIdDay();
                monthActual = daySelected.getIdMonth();
            }
        }

        calenderAdapter.notifyDataSetChanged();
    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
    }

    private ArrayList<File> getEventsByDay(File day){
        ArrayList<File> flag = new ArrayList<>();

        for(File o : listFilesBackup) {
            if (o.isEvent() && (o.getIdMonth() == day.getIdMonth() && o.getIdDay() == day.getIdDay())) {
                flag.add(o);
            }
        }

        return flag;
    }

    private void desactivateDays(){
        for(File o : listFilesBackup) {
            if (!o.isEvent()) {
                o.setIsShow(false);
            }
        }
    }

    private void loadData(){
        /*Day d1 = new Day();
        d1.setIdMonth(5);
        d1.setIdDay(8);

        Event e1 = new Event();
        e1.setName("Capacity Building Resources Committee\n" +
                "Meeting (CBRC)");
        e1.setDuration("09:00 - 10:00 AM");

        Event e2 = new Event();
        e1.setName("Af\u001Fliate Members Consultative Committee\n" +
                "Meeting (AMCC)");
        e1.setDuration("09:30 - 13:00 PM");

        Event e3 = new Event();
        e3.setName("Lunch");
        e3.setDuration("12:30 - 14:00 PM");

        d1.getList().add(e1);
        d1.getList().add(e2);
        d1.getList().add(e3);

        Day d2 = new Day();
        d2.setIdMonth(5);
        d2.setIdDay(9);

        Day d3 = new Day();
        d2.setIdMonth(5);
        d2.setIdDay(10);

        Day d4 = new Day();
        d2.setIdMonth(5);
        d2.setIdDay(11);

        Day d5 = new Day();
        d2.setIdMonth(5);
        d2.setIdDay(12);

        listDays.add(d1);
        listDays.add(d2);
        listDays.add(d3);
        listDays.add(d4);
        listDays.add(d5);*/

        File f1 = new File();
        f1.setIdMonth(5);
        f1.setIdDay(8);

        File f2 = new File();
        f2.setName("Capacity Building Resources Committee Meeting (CBRC)");
        f2.setDuration("09:00 - 10:00 AM");
        f2.setIsEvent(true);
        f2.setIdMonth(5);
        f2.setIdDay(8);

        File f3 = new File();
        f3.setName("Af\u001Fliate Members Consultative Committee Meeting (AMCC)");
        f3.setDuration("09:30 - 13:00 PM");
        f3.setIsEvent(true);
        f3.setIdMonth(5);
        f3.setIdDay(8);

        File f4 = new File();
        f4.setName("Lunch");
        f4.setDuration("12:30 - 14:00 PM");
        f4.setIsEvent(true);
        f4.setIdMonth(5);
        f4.setIdDay(8);

        File f5 = new File();
        f5.setIdMonth(5);
        f5.setIdDay(9);

        File f51 = new File();
        f51.setName("Lunch");
        f51.setDuration("12:30 - 14:00 PM");
        f51.setIsEvent(true);
        f51.setIdMonth(5);
        f51.setIdDay(9);

        File f52 = new File();
        f52.setName("Lunch");
        f52.setDuration("12:30 - 14:00 PM");
        f52.setIsEvent(true);
        f52.setIdMonth(5);
        f52.setIdDay(9);

        File f6 = new File();
        f6.setIdMonth(5);
        f6.setIdDay(10);

        File f7 = new File();
        f7.setIdMonth(5);
        f7.setIdDay(11);

        File f8 = new File();
        f8.setIdMonth(5);
        f8.setIdDay(12);

        listFilesBackup.add(f1);
        listFilesBackup.add(f2);
        listFilesBackup.add(f3);
        listFilesBackup.add(f4);
        listFilesBackup.add(f5);
        listFilesBackup.add(f51);
        listFilesBackup.add(f52);
        listFilesBackup.add(f6);
        listFilesBackup.add(f7);
        listFilesBackup.add(f8);

        for(File o : listFilesBackup){
            if(!o.isEvent()){
                listFiles.add(o);
            }
        }

        dayActual = 0;
        monthActual = 0;

        //int count = 1;
        int index = 0;
        for(File o : listFiles){
            if(!o.isEvent() && (o.getIdDay() == 8 && o.getIdMonth() == 5)){
                index = listFiles.indexOf(o);
            }
        }

        if(index != -1) {
            Log.i("respuesta","index "+index);
            showEvents(index);

        }


        /*for(int i=0 ; i<listFiles.size() ; i++){
            if(listFiles.get(i).getIdMonth() == monthActual && listFiles.get(i).getIdDay() == dayActual){
                for(File o : listFilesBackup){
                    if(o.isEvent() && (o.getIdMonth() == monthActual && o.getIdDay() == dayActual)){
                        listFiles.add(i+count,o); count++;
                    }
                }
                if(count > 1){
                    listFiles.get(i).setIsShow(true);
                }
                break;
            }
        }*/
    }

    protected void removeListItem(View rowView, final int lista) {
        final Animation animation = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        rowView.startAnimation(animation);
        Handler handle = new Handler();
        handle.postDelayed(new Runnable() {

            public void run() {
                listFiles.remove(lista);
                calenderAdapter.notifyDataSetChanged();
            }
        }, 1000);
    }
}
