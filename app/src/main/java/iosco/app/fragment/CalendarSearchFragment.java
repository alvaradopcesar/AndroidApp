package iosco.app.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import iosco.app.R;
import iosco.app.activity.MainActivityNew;
import iosco.app.adapters.CalendarSearchAdapter;
import iosco.app.model.entity.CalendarEntity;
import iosco.app.model.entity.Exhibitor;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarSearchFragment extends Fragment {

    private ListView lstList;
    private EditText txtSearch;
    private ArrayList<CalendarEntity> arrayCompleted;
    private ArrayList<CalendarEntity> arrayFiltred;

    private CalendarSearchAdapter adapter;

    public CalendarSearchFragment() {
    }

    public static CalendarSearchFragment newInstance(ArrayList<CalendarEntity> list) {
        CalendarSearchFragment fragment = new CalendarSearchFragment();
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

        arrayFiltred = new ArrayList<>();

        if(getArguments().getSerializable("list") != null)
            arrayCompleted = (ArrayList<CalendarEntity>) getArguments().getSerializable("list");

        adapter = new CalendarSearchAdapter(getActivity(),arrayFiltred);
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
                ((MainActivityNew) getActivity()).showCalendarDetail(arrayFiltred.get(i));
            }
        });

        (rootView.findViewById(R.id.imgExhibitorX)).setOnClickListener(new View.OnClickListener() {
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
        arrayFiltred.clear();
        for(CalendarEntity x : arrayCompleted){
            if(x.getTitle().toUpperCase().indexOf(word.toUpperCase()) != -1) {
                arrayFiltred.add(x);
            }
        }
        adapter.notifyDataSetChanged();
    }

}
