package iosco.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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


public class EveningEventsFragment extends Fragment {

    private static EveningEventsFragment instance;

    private ArrayList<Row> listRows;
    private ViewGroup contenedor;
    private ProgressBar progressBar;

    private int indexChildShow = -1;

    private static ArrayList<CalendarEntity> staticList;

    private EveningEventsFragment() {

    }

    public static EveningEventsFragment getInstance(){
        if(instance == null){
            instance = new EveningEventsFragment();
        }
        return instance;
    }

    public static ArrayList<CalendarEntity> getList(){
        return staticList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_evening_events, container, false);
        progressBar = (ProgressBar)rootView.findViewById(R.id.progressBarFragmentEvening);
        contenedor = (ViewGroup)rootView.findViewById(R.id.eveningContenedor);

        if(indexChildShow == -1) {


            listRows = new ArrayList<>();

            try {
                loadRows();
            }catch (Exception e){}

        }else{
            progressBar.setVisibility(View.GONE);
            contenedor.setVisibility(View.VISIBLE);
            try {
                loadViews();
            }catch (Exception e){
                e.printStackTrace();
            }
        }




        return rootView;
    }

    private void loadRows(){
        final ArrayList<CalendarEntity> listCalendars = new ArrayList<>();
        staticList = new ArrayList<>();
        ApiImplementation.getService().getEveningEvents(Global.getUserToken(getActivity())).enqueue(new Callback<ArrayList<CalendarEntity>>() {
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

    private void loadViews(){
        ViewGroup subContenedor = null;
        for(final Row r : listRows){
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            if(r instanceof Head){
                LinearLayout viewGroup = (LinearLayout) inflater.inflate(R.layout.model_event_hour, null, false);
                TextView txtDay = (TextView)viewGroup.findViewById(R.id.txtEventHour);
                txtDay.setText(((Head) r).getName());
                Helpers.changeFont(getActivity(), txtDay, "HelveticaNeueLTStd-Bd.otf");
                viewGroup.setTag("head");
                contenedor.addView(viewGroup);

                viewGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // evaluo si el último head tiene un child
                        // evaluo si el siguiente child es un subContenedor
                        if (contenedor.getChildCount() > contenedor.indexOfChild(view) + 1 && contenedor.getChildAt(contenedor.indexOfChild(view) + 1).getTag().equals("contenedor")) {
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

            }else{;
                if(subContenedor == null){
                    subContenedor = (ViewGroup) inflater.inflate(R.layout.model_linear, null, false);
                    subContenedor.setTag("contenedor");
                    contenedor.addView(subContenedor);
                }
                RelativeLayout viewGroup = (RelativeLayout) inflater.inflate(R.layout.model_event_name_back, null, false);
                TextView txtNameEvent = (TextView)viewGroup.findViewById(R.id.txtEventName);
                txtNameEvent.setText(((CalendarEntity) r).getTitle());
                Helpers.changeFont(getActivity(), txtNameEvent, "HelveticaNeueLTStd-Lt.otf");
                subContenedor.addView(viewGroup);
                viewGroup.setTag(listRows.indexOf(r));

                viewGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivityNew)getActivity()).showEveningDetail((CalendarEntity)listRows.get((int)view.getTag()));
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
