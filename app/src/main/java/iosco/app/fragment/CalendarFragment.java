package iosco.app.fragment;

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

import java.util.ArrayList;

import iosco.app.R;
import iosco.app.activity.MainActivityNew;
import iosco.app.model.entity.CalendarEntity;
import iosco.app.model.entity.Head;
import iosco.app.model.entity.Row;
import iosco.app.service.ApiImplementation;
import iosco.app.utils.Global;
import iosco.app.utils.Helpers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CalendarFragment extends Fragment {

    private static CalendarFragment instance;

    private ArrayList<Row> listRows;
    private ViewGroup contenedor;
    private ProgressBar progressBar;

    private int indexChildShow = -1;

    private static ArrayList<CalendarEntity> staticList;

    public CalendarFragment() {

    }

    public static CalendarFragment getInstance(){
        if(instance == null){
            instance = new CalendarFragment();
        }
        return instance;
    }

    public static ArrayList<CalendarEntity> getList(){
        return staticList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBarFragmentCalendar);
        contenedor = (ViewGroup)rootView.findViewById(R.id.calendarContenedor);

        if(indexChildShow == -1) {


            listRows = new ArrayList<>();

            try {
                loadRows();
            }catch (Exception e){}
            //loadViews();

            Log.i("resultado", "contenedor.getChildCount() antes " + contenedor.getChildCount());

        }else{
            Log.i("resultado","contenedor.getChildCount() despues "+contenedor.getChildCount());
            //((ViewGroup)rootView.findViewById(R.id.calendarContenedor)).removeView(contenedor);
            //((ViewGroup)rootView.findViewById(R.id.calendarContenedor)).addView(contenedor);
            progressBar.setVisibility(View.GONE);
            contenedor.setVisibility(View.VISIBLE);
            try {
                loadViews();
            }catch (Exception e){}
        }




        return rootView;
    }

    private void loadRows(){
        final ArrayList<CalendarEntity> listCalendars = new ArrayList<>();
        staticList = new ArrayList<>();
        ApiImplementation.getService().getCalendar(Global.getUserToken(getActivity())).enqueue(new Callback<ArrayList<CalendarEntity>>() {
            @Override
            public void onResponse(Call<ArrayList<CalendarEntity>> call, Response<ArrayList<CalendarEntity>> response) {
                if (response.isSuccess()) {
                    listCalendars.addAll(response.body());

                    ArrayList<Head> listHeads = new ArrayList<>();
                    staticList.addAll(response.body());
                    for (CalendarEntity c : listCalendars) {
                        if (!existHead(c.getStartDay(), c.getStartMonth(), listHeads)) {
                            Head h = new Head();
                            h.setName(c.getStartDay() + " " + Helpers.getNameMonth(c.getStartMonth()) + " 2016");
                            h.setVal1(c.getStartDay());
                            h.setVal2(c.getStartMonth());
                            listHeads.add(h);
                            listRows.add(h);
                        }
                        listRows.add(c);
                    }


                    loadViews();
                    progressBar.setVisibility(View.GONE);
                    contenedor.setVisibility(View.VISIBLE);
                } else {
                    Log.i("respuesta3", "getCalendar !response.isSucces() " + response.errorBody() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CalendarEntity>> call, Throwable t) {
                Log.i("respuesta3", "onFailure " + t.getMessage());
            }
        });
    }

    private boolean existHead(int day, int month, ArrayList<Head> lista){
        boolean flag = false;
        for(Head h : lista){
            if(h.getVal1() == day && h.getVal2() == month){
                flag = true;
            }
        }
        return flag;
    }

    private void loadRows2(){
        /*Head f1 = new Head();
        f1.setName("08 " + valMonth(5) + ", 2016");

        CalendarEntity f2 = new CalendarEntity();
        f2.setName("Capacity Building Resources Committee Meeting (CBRC)");
        f2.setDuration("09:00 - 10:00 AM");

        CalendarEntity f3 = new CalendarEntity();
        f3.setName("Af\u001Fliate Members Consultative Committee Meeting (AMCC)");
        f3.setDuration("09:30 - 13:00 PM");

        CalendarEntity f4 = new CalendarEntity();
        f4.setName("Lunch");
        f4.setDuration("12:30 - 14:00 PM");

        Head f5 = new Head();
        f5.setName("9 " + valMonth(8) + ", 2016");

        CalendarEntity f51 = new CalendarEntity();
        f51.setName("Lunch");
        f51.setDuration("12:30 - 14:00 PM");

        CalendarEntity f52 = new CalendarEntity();
        f52.setName("Lunch");
        f52.setDuration("12:30 - 14:00 PM");

        Head f6 = new Head();
        f6.setName("10 " + valMonth(8) + ", 2016");

        CalendarEntity f61 = new CalendarEntity();
        f61.setName("Lunch");
        f61.setDuration("12:30 - 14:00 PM");

        Head f7 = new Head();
        f7.setName("11 " + valMonth(8) + ", 2016");

        CalendarEntity f71 = new CalendarEntity();
        f71.setName("Lunch");
        f71.setDuration("12:30 - 14:00 PM");

        Line l1 = new Line();
        Line l2 = new Line();
        Line l3 = new Line();
        Line l4 = new Line();

        //listRows.add(l1);
        listRows.add(f1);
        listRows.add(f2);
        listRows.add(f3);
        listRows.add(f4);
        //listRows.add(l2);
        listRows.add(f5);
        listRows.add(f51);
        listRows.add(f52);
        //listRows.add(l3);
        listRows.add(f6);
        listRows.add(f61);
        //listRows.add(l4);
        listRows.add(f7);
        listRows.add(f71);*/
    }

    private void loadViews(){
        ViewGroup subContenedor = null;
        for(final Row r : listRows){
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            if(r instanceof Head){
                Log.i("respuesta4",((Head) r).getName());
                LinearLayout viewGroup = (LinearLayout) inflater.inflate(R.layout.model_day, null, false);
                TextView txtDay = (TextView)viewGroup.findViewById(R.id.txtDay);
                txtDay.setText(((Head) r).getName());
                Helpers.changeFont(getActivity(), txtDay, "HelveticaNeueLTStd-Bd.otf");
                viewGroup.setTag("head");
                contenedor.addView(viewGroup);

                viewGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // evaluo si el último head tiene un child
                        // evaluo si el siguiente child es un subContenedor
                        if(contenedor.getChildCount() > contenedor.indexOfChild(view)+1 && contenedor.getChildAt(contenedor.indexOfChild(view) + 1).getTag().equals("contenedor")) {
                            // evaluo si se ha seleccionado antes un head
                            if (indexChildShow != -1) {
                                Helpers.collapse((ViewGroup) contenedor.getChildAt(indexChildShow + 1));
                            }
                            // evaluo si se ha seleccionado el mismo child
                            if (indexChildShow == contenedor.indexOfChild(view)) {
                                Helpers.collapse((ViewGroup) contenedor.getChildAt(indexChildShow + 1));
                                indexChildShow = -1;
                            } else {
                                Helpers.expand((ViewGroup) contenedor.getChildAt(contenedor.indexOfChild(view) + 1));
                                indexChildShow = contenedor.indexOfChild(view);
                            }
                        }
                    }
                });
                subContenedor = null;

            }else{
                Log.i("respuesta4",((CalendarEntity) r).getTitle());
                if(subContenedor == null){
                    subContenedor = (ViewGroup) inflater.inflate(R.layout.model_linear, null, false);
                    subContenedor.setTag("contenedor");
                    contenedor.addView(subContenedor);
                }
                LinearLayout viewGroup = (LinearLayout) inflater.inflate(R.layout.model_day_detail, null, false);
                TextView txtTimeEvent = (TextView)viewGroup.findViewById(R.id.txtTimeEvent);
                TextView txtNameEvent = (TextView)viewGroup.findViewById(R.id.txtNameEvent);
                ImageView imgCheckEvent = (ImageView)viewGroup.findViewById(R.id.imgCheckEvent);
                txtTimeEvent.setText(((CalendarEntity) r).getDuration());
                txtNameEvent.setText(((CalendarEntity) r).getTitle());
                if(((CalendarEntity) r).isRegistered()){
                    imgCheckEvent.setVisibility(View.VISIBLE);
                }else{
                    imgCheckEvent.setVisibility(View.INVISIBLE);
                    imgCheckEvent.setImageResource(0);
                }
                Helpers.changeFont(getActivity(), txtTimeEvent, "HelveticaNeueLTStd-Roman.otf");
                Helpers.changeFont(getActivity(), txtNameEvent, "HelveticaNeueLTStd-Lt.otf");
                subContenedor.addView(viewGroup);
                viewGroup.setTag(listRows.indexOf(r));

                viewGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivityNew)getActivity()).showCalendarDetail((CalendarEntity)listRows.get((int)view.getTag()));
                    }
                });
            }

            for(int i=0; i < contenedor.getChildCount(); i++){
                if(contenedor.getChildAt(i).getTag().equals("contenedor") && indexChildShow+1 != i){
                    contenedor.getChildAt(i).getLayoutParams().height = 0;
                    contenedor.getChildAt(i).setVisibility(View.GONE);
                }
            }

        }

    }




}
