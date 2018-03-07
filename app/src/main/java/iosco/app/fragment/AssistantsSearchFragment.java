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
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import iosco.app.R;
import iosco.app.adapters.AssistantSearchAdapter;
import iosco.app.model.entity.Assistant;
import iosco.app.model.entity.CalendarEntity;
import iosco.app.model.entity.Exhibitor;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssistantsSearchFragment extends Fragment {

    private ListView lstList;
    private EditText txtSearch;
    private ArrayList<Assistant> arrayCompleted;
    private ArrayList<Assistant> arrayFiltred;

    private AssistantSearchAdapter adapter;

    public AssistantsSearchFragment() {
    }

    public static AssistantsSearchFragment newInstance(ArrayList<Assistant> list) {
        AssistantsSearchFragment fragment = new AssistantsSearchFragment();
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
            arrayCompleted = (ArrayList<Assistant>) getArguments().getSerializable("list");

        adapter = new AssistantSearchAdapter(getActivity(),arrayFiltred);
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
        for(Assistant x : arrayCompleted){
            if((x.getCountry().getName()
                    +
                    x.getOrganization().getName()+ x.getFirstName()+x.getLastName()+x.getJobTitle()).toUpperCase().indexOf(word.toUpperCase()) != -1) {
                arrayFiltred.add(x);
            }
        }
        adapter.notifyDataSetChanged();
    }

}
