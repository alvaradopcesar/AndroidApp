package iosco.app.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import iosco.app.R;
import iosco.app.model.entity.CalendarEntity;
import iosco.app.model.entity.Exhibitor;
import iosco.app.service.ApiImplementation;
import iosco.app.utils.Global;
import iosco.app.utils.Helpers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ExhibitorDetailFragment extends Fragment {

    private Exhibitor exhibitor;

    private LinearLayout layoutExhibitorEvents;

    public ExhibitorDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public static ExhibitorDetailFragment newInstance(Exhibitor exhibitor) {
        ExhibitorDetailFragment fragment = new ExhibitorDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("exhibitor", exhibitor);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_exhibitor_detail, container, false);

        exhibitor = (Exhibitor) getArguments().getSerializable("exhibitor");

        ((TextView)rootView.findViewById(R.id.txtExhibitorName)).setText(exhibitor.getFirstName() + " " + exhibitor.getLastName());
        ((TextView)rootView.findViewById(R.id.txtExhibitorPosition)).setText(exhibitor.getJobTitle());
        ((TextView)rootView.findViewById(R.id.txtExhibitorText)).setText(exhibitor.getBrief());

        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtExhibitorName)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtExhibitorPosition)), "HelveticaNeueLTStd-Lt.otf");
     //   Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtOrganization)), "HelveticaNeueLTStd-Md.otf");

        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtExhibitorLabel1)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtExhibitorLabel2)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtExhibitorText)), "HelveticaNeueLTStd-Lt.otf");

        layoutExhibitorEvents = (LinearLayout)rootView.findViewById(R.id.layoutExhibitorEvents);

        if(exhibitor.getMobileNumber() != null && !exhibitor.getMobileNumber().equals(""))
        rootView.findViewById(R.id.layoutExhibitorMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent smsIntent = new Intent(Intent.ACTION_VIEW);
                    smsIntent.setType("vnd.android-dir/mms-sms");
                    smsIntent.putExtra("address", exhibitor.getMobileNumber());
                    startActivity(smsIntent);
                }catch (Exception e){}
            }
        });
        if(exhibitor.getEmailAddress() != null && !exhibitor.getEmailAddress().equals(""))
        rootView.findViewById(R.id.layoutExhibitorMail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{exhibitor.getEmailAddress()});
                startActivity(Intent.createChooser(i, "Send mail"));
            }
        });
        if(exhibitor.getTwitterURL() != null && !exhibitor.getTwitterURL().equals(""))
        rootView.findViewById(R.id.layoutExhibitorTwitter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(exhibitor.getTwitterURL())));
            }
        });
     //   if(exhibitor.getFacebookURL() != null && !exhibitor.getFacebookURL().equals(""))
        rootView.findViewById(R.id.layoutExhibitorFacebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(exhibitor.getFacebookURL())));

           //   startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.modelocurriculum.net/wp-content/formato_hoja_vida.pdf")));
            }
        });

        ImageLoader.getInstance().displayImage(ApiImplementation.getBaseUrl() + "api/speaker/" + exhibitor.getId() + "/profilepicture", ((ImageView) rootView.findViewById(R.id.exhibitorPhoto)));
        loadEvents();

        return rootView;
    }

    private void loadEvents(){
        ApiImplementation.getService().getEventsBySpeaker(Global.getUserToken(getActivity()),exhibitor.getId()).enqueue(new Callback<ArrayList<CalendarEntity>>() {
            @Override
            public void onResponse(Call<ArrayList<CalendarEntity>> call, Response<ArrayList<CalendarEntity>> response) {
                LayoutInflater inflater = LayoutInflater.from(getActivity());
                boolean flag = true;
                for(CalendarEntity c : response.body()){
                    LinearLayout viewGroup = (LinearLayout) inflater.inflate(R.layout.model_exhibitor_event, null, false);
                    TextView txtExhibitorEventName = (TextView)viewGroup.findViewById(R.id.txtExhibitorEventName);
                    TextView txtExhibitorEventDuration = (TextView)viewGroup.findViewById(R.id.txtExhibitorEventDuration);
                    TextView txtExhibitorEventDate = (TextView)viewGroup.findViewById(R.id.txtExhibitorEventDate);

                    Helpers.changeFont(getActivity(), txtExhibitorEventName, "HelveticaNeueLTStd-Md.otf");
                    Helpers.changeFont(getActivity(), txtExhibitorEventDuration, "HelveticaNeueLTStd-Lt.otf");
                    Helpers.changeFont(getActivity(), txtExhibitorEventDate, "HelveticaNeueLTStd-Lt.otf");

                    txtExhibitorEventName.setText(c.getTitle());
                    txtExhibitorEventDuration.setText(c.getDuration());
                    txtExhibitorEventDate.setText(c.getDate());
                    layoutExhibitorEvents.addView(viewGroup);

                    if(flag)
                        viewGroup.setBackgroundColor(Color.parseColor("#ffffff"));

                    flag = !flag;
                }
            }

            @Override
            public void onFailure(Call<ArrayList<CalendarEntity>> call, Throwable t) {

            }
        });
    }

}