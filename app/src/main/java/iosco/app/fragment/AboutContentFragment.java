package iosco.app.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import iosco.app.R;
import iosco.app.utils.Helpers;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutContentFragment extends Fragment {


    public AboutContentFragment() {
        // Required empty public constructor
    }

    public static AboutContentFragment newInstance(int id) {
        AboutContentFragment fragment = new AboutContentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("id", id);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int id = (int) getArguments().getSerializable("id");

        View rootView;
        WebView cont;
        if(id == 1) {
            rootView = inflater.inflate(R.layout.fragment_about_content1, container, false);
            cont = (WebView) rootView.findViewById(R.id.wv_content_1);
            cont.loadUrl("file:///android_asset/about_1.html");
        }
        else if(id == 2) {
            rootView = inflater.inflate(R.layout.fragment_about_content2, container, false);
            cont = (WebView) rootView.findViewById(R.id.wv_content_2);
            cont.loadUrl("file:///android_asset/about_2.html");
        }
        else {
            rootView = inflater.inflate(R.layout.fragment_about_content3, container, false);
             cont = (WebView)rootView.findViewById(R.id.wv_content);
            cont.loadUrl("file:///android_asset/about_3.html");

        }
        cont.setBackgroundColor(Color.parseColor("#00ffffff"));
        Helpers.changeFont(getActivity(), (TextView)rootView.findViewById(R.id.about_detail_text1), "HelveticaNeueLTStd-Roman.otf");
    //    Helpers.changeFont(getActivity(), (TextView)rootView.findViewById(R.id.about_detail_text2), "HelveticaNeueLTStd-Th.otf");

        return rootView;
    }

}
