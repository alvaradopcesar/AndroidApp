package iosco.app.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import iosco.app.R;
import iosco.app.activity.MainActivityNew;
import iosco.app.adapters.ExhibitorSearchAdapter;
import iosco.app.model.entity.CalendarEntity;
import iosco.app.model.entity.Exhibitor;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExhibitorSearchFragment extends Fragment {

    private ListView lstList;
    private EditText txtSearch;
    private ArrayList<Exhibitor> arrayExhibitors;
    private ArrayList<Exhibitor> arrayExhibitorsFiltred;

    private ExhibitorSearchAdapter adapter;

    public ExhibitorSearchFragment() {
    }

    public static ExhibitorSearchFragment newInstance(ArrayList<Exhibitor> list) {
        ExhibitorSearchFragment fragment = new ExhibitorSearchFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("list", list);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exhibitor_search, container, false);

        lstList = (ListView)rootView.findViewById(R.id.lstExhibitorSearch);
        txtSearch = (EditText)rootView.findViewById(R.id.txtExhibitorSearch);

        arrayExhibitorsFiltred = new ArrayList<>();

        if(getArguments().getSerializable("list") != null)
        arrayExhibitors = (ArrayList<Exhibitor>) getArguments().getSerializable("list");

        adapter = new ExhibitorSearchAdapter(getActivity(),arrayExhibitorsFiltred);
        lstList.setAdapter(adapter);

        txtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchWord(editable.toString());
            }
        });

        lstList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                hideKeyboard();
                ((MainActivityNew) getActivity()).showExhibitorDetail(arrayExhibitorsFiltred.get(i));
            }
        });

        ((ImageView)rootView.findViewById(R.id.imgExhibitorX)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtSearch.setText("");
            }
        });

        return rootView;
    }

    private void hideKeyboard(){
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void searchWord(String word){
        arrayExhibitorsFiltred.clear();
        for(Exhibitor x : arrayExhibitors){
            Log.i("respuesta7", "total "+x.getFirstName());
            if(( x.getFirstName()+x.getLastName()+x.getJobTitle()).toUpperCase().indexOf(word.toUpperCase()) != -1) {
                arrayExhibitorsFiltred.add(x);
                Log.i("respuesta7", "filtrado "+x.getFirstName());
            }
        }
        adapter.notifyDataSetChanged();
    }

}
