package iosco.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import iosco.app.R;
import iosco.app.adapters.ExhibitorAdapterOld;
import iosco.app.model.entity.Exhibitor;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExhibitorsFragment_ extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ListView lstLetters;
    private ListView lstExhibitors;

    private ArrayAdapter<String> lettersAdapter;
    private ArrayList<String> lettersList;
    private ArrayList<Exhibitor> listExhibitors;
    private ArrayList<Exhibitor> listPojos;

    private LinearLayout layoutLetters;

    private ExhibitorAdapterOld exhibitorAdapterOld;

    public ExhibitorsFragment_() {
        // Required empty public constructor
    }

    private static ExhibitorsFragment_ instance = null;

    public static ExhibitorsFragment_ getInstance() {
        if(instance == null) {
            instance = new ExhibitorsFragment_();
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_exhibitors_, container, false);

        layoutLetters = (LinearLayout)rootView.findViewById(R.id.layoutLetters);
        lstExhibitors = (ListView)rootView.findViewById(R.id.lstExhibitors);
        //lstLetters = (ListView)rootView.findViewById(R.id.lstLetters);

        lettersList = new ArrayList<>();
        listExhibitors = new ArrayList<>();
        listPojos = new ArrayList<>();

        exhibitorAdapterOld = new ExhibitorAdapterOld(getActivity(),listPojos);
        lstExhibitors.setAdapter(exhibitorAdapterOld);

        loadLettes();
        loadExhibitors();
        loadPojos();


        //lettersAdapter  = new ArrayAdapter<String>(getActivity(), R.layout.model_letter, lettersList);



        //lstLetters.setAdapter(lettersAdapter);
        //lettersAdapter.notifyDataSetChanged();

        return rootView;
    }

    private void loadLettes(){

        for (char ch: "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()) {
            //lettersList.add(ch+"");
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            TextView txtL = (TextView) inflater.inflate(R.layout.model_letter, null, false);
            txtL.setText(ch+"");
            layoutLetters.addView(txtL);
        }
    }

    private void loadExhibitors(){

        Exhibitor e1 = new Exhibitor();
        e1.setNombre("Amon");
        e1.setApellido("Allardice");
        e1.setPosition("BBC NEWS");

        Exhibitor e2 = new Exhibitor();
        e2.setNombre("Alan");
        e2.setApellido("T. Hit");
        e2.setPosition("Banking Standards Board");

        Exhibitor e3 = new Exhibitor();
        e3.setNombre("Bean");
        e3.setApellido("Ridhard");
        e3.setPosition("Executive Manager");

        Exhibitor e4 = new Exhibitor();
        e4.setNombre("Crist");
        e4.setApellido("Rupert");
        e4.setPosition("Executive Manager");

        Exhibitor e5 = new Exhibitor();
        e5.setNombre("Dionicius");
        e5.setApellido("Lopold");
        e5.setPosition("Executive Manager");

        listExhibitors.add(e1);
        listExhibitors.add(e2);
        listExhibitors.add(e3);
        listExhibitors.add(e4);
        listExhibitors.add(e5);
    }

    private void loadPojos(){
        for (char ch: "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()) {
            if(searchCapital(ch+"")){
                Exhibitor capital = new Exhibitor();
                capital.setNombre(ch+"");
                listPojos.add(capital);
                listPojos.addAll(getExhibitorsByCapital(ch + ""));
            }
        }

        exhibitorAdapterOld.notifyDataSetChanged();

        for(Exhibitor e : listPojos){
            Log.i("resultado2","Name "+e.getNombre());
        }

    }

    private boolean searchCapital(String c){
        boolean flag = false;
        for(Exhibitor e : listExhibitors){
            if(e.getNombre().substring(0,1).equals(c)){
                flag = !flag; break;
            }
        }
        return flag;
    }

    private ArrayList<Exhibitor> getExhibitorsByCapital(String c){
        ArrayList<Exhibitor> list = new ArrayList<>();
        for(Exhibitor e : listExhibitors){
            if(e.getNombre().substring(0,1).equals(c)){
                e.setIsExhibitor(true); list.add(e);
            }
        }
        return list;
    }

}
