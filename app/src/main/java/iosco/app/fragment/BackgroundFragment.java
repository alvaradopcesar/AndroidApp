package iosco.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import iosco.app.R;
import iosco.app.model.entity.Exhibitor;

/**
 * A simple {@link Fragment} subclass.
 */
public class BackgroundFragment extends Fragment {

    private int id;

    public BackgroundFragment() {
    }

    public static BackgroundFragment newInstance(int id) {
        BackgroundFragment fragment = new BackgroundFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("id", id);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_background, container, false);

        id = (int) getArguments().getSerializable("id");

        if(id == 1)
            (rootView.findViewById(R.id.layoutBackground)).setBackgroundResource(R.drawable.background_home);
        else
            (rootView.findViewById(R.id.layoutBackground)).setBackgroundResource(0);

        return rootView;
    }

}
