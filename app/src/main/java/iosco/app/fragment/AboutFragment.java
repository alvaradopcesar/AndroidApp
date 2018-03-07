package iosco.app.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import iosco.app.R;
import iosco.app.utils.Helpers;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {

    private static AboutFragment instance;

    private LinearLayout viewAbout1;
    private LinearLayout viewAbout2;
    private LinearLayout viewAbout3;

    private int fragmentActual;

    public static AboutFragment getInstance(){
        if(instance == null){
            instance = new AboutFragment();
        }
        return instance;
    }

    public AboutFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_about, container, false);

        viewAbout1 = (LinearLayout)rootView.findViewById(R.id.txtAbout1);
        viewAbout2 = (LinearLayout)rootView.findViewById(R.id.txtAbout2);
        viewAbout3 = (LinearLayout)rootView.findViewById(R.id.layoutAbout3);

        FragmentTransaction ft2 = getActivity().getSupportFragmentManager().beginTransaction();
        ft2.replace(R.id.content_frame_about, AboutContentFragment.newInstance(1), "fragment");
        ft2.commit();

        viewAbout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("respuesta12", "1");
                changeFragment(1);
            }
        });
        viewAbout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("respuesta12", "2");
                changeFragment(2);
            }
        });
        viewAbout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("respuesta12", "3");
                changeFragment(3);
            }
        });

        Helpers.changeFont(getActivity(), ((TextView) viewAbout1.getChildAt(0)), "HelveticaNeueLTStd-Md.otf");
        Helpers.changeFont(getActivity(), ((TextView) viewAbout2.getChildAt(0)), "HelveticaNeueLTStd-Md.otf");
        Helpers.changeFont(getActivity(), ((TextView) viewAbout3.getChildAt(0)), "HelveticaNeueLTStd-Md.otf");
        Helpers.changeFont(getActivity(), ((TextView) viewAbout3.getChildAt(1)), "HelveticaNeueLTStd-Md.otf");

        fragmentActual = 1;

        viewAbout1.setBackgroundColor(Color.parseColor("#B87EC2"));
        ((TextView)viewAbout1.getChildAt(0)).setTextColor(Color.parseColor("#ffffff"));

        return rootView;
    }

    private void changeFragment(int id){
        if(id != fragmentActual) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

            if (id > fragmentActual)
                ft.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            else
                ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);

            ft.replace(R.id.content_frame_about, AboutContentFragment.newInstance(id), "fragment1");
            ft.commit();

            fragmentActual = id;

            viewAbout1.setBackgroundColor(Color.parseColor("#00ffffff"));
            viewAbout2.setBackgroundColor(Color.parseColor("#00ffffff"));
            viewAbout3.setBackgroundColor(Color.parseColor("#00ffffff"));
            ((TextView)viewAbout1.getChildAt(0)).setTextColor(Color.parseColor("#666666"));
            ((TextView)viewAbout2.getChildAt(0)).setTextColor(Color.parseColor("#666666"));
            ((TextView)viewAbout3.getChildAt(0)).setTextColor(Color.parseColor("#666666"));
            ((TextView)viewAbout3.getChildAt(1)).setTextColor(Color.parseColor("#666666"));

            switch(id){
                case 1:
                    viewAbout1.setBackgroundColor(Color.parseColor("#B87EC2"));
                    ((TextView)viewAbout1.getChildAt(0)).setTextColor(Color.parseColor("#ffffff")); break;
                case 2:
                    viewAbout2.setBackgroundColor(Color.parseColor("#B87EC2"));
                    ((TextView)viewAbout2.getChildAt(0)).setTextColor(Color.parseColor("#ffffff")); break;
                case 3:
                    viewAbout3.setBackgroundColor(Color.parseColor("#B87EC2"));
                    ((TextView)viewAbout3.getChildAt(0)).setTextColor(Color.parseColor("#ffffff"));
                    ((TextView)viewAbout3.getChildAt(1)).setTextColor(Color.parseColor("#ffffff")); break;
            }
        }
    }


}
