package iosco.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.Date;

import iosco.app.R;
import iosco.app.activity.MainActivityNew;
import iosco.app.model.dao.NoteDao;
import iosco.app.model.entity.CalendarEntity;
import iosco.app.service.ApiImplementation;
import iosco.app.utils.Global;
import iosco.app.utils.Helpers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CalendarDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CalendarDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarDetailFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventFragment.
     */
    // TODO: Rename and change types and number of parameters

    private static CalendarDetailFragment instance;
    private CalendarEntity calendar;

    private ImageView imgCalendarSketch;

    public static CalendarDetailFragment getInstance(CalendarEntity calendar) {
        if(instance == null) {
            instance = new CalendarDetailFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable("calendar", calendar);
        instance.setArguments(bundle);
        return instance;
    }

    public static CalendarDetailFragment newInstance(CalendarEntity calendar) {
        CalendarDetailFragment fragment = new CalendarDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("calendar", calendar);
        fragment.setArguments(bundle);

        return fragment;
    }

    public static CalendarDetailFragment newInstance(String param1, String param2) {
        CalendarDetailFragment fragment = new CalendarDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public CalendarDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar_detail, container, false);

        calendar = (CalendarEntity) getArguments().getSerializable("calendar");

        ((TextView)rootView.findViewById(R.id.txtTitle)).setText(calendar.getTitle());
        ((TextView)rootView.findViewById(R.id.txtDuration)).setText(calendar.getDuration());
        ((TextView)rootView.findViewById(R.id.txtDate)).setText(calendar.getDate());
        ((TextView)rootView.findViewById(R.id.txtLocation)).setText(calendar.getEventLocation().getDescription());
        ((TextView)rootView.findViewById(R.id.txtSketchDescription)).setText(calendar.getSketchDescription());
        (rootView.findViewById(R.id.imgCalendarNote)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivityNew) getActivity()).showCalendarNote(calendar.getId(), calendar);
            }
        });
        (rootView.findViewById(R.id.imgCalendarAsk)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeAsk();
            }
        });

        if(calendar.isPrivate()){
            rootView.findViewById(R.id.imgCalendarAsk).setVisibility(View.VISIBLE);
        }else{
            rootView.findViewById(R.id.imgCalendarAsk).setVisibility(View.GONE);
        }

        Helpers.changeFont(getActivity(), ((TextView) rootView.findViewById(R.id.txtTitle)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView)rootView.findViewById(R.id.txtDuration)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView)rootView.findViewById(R.id.txtDate)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView)rootView.findViewById(R.id.txtLocation)), "HelveticaNeueLTStd-Roman.otf");
        Helpers.changeFont(getActivity(), ((TextView)rootView.findViewById(R.id.lblLocation)), "HelveticaNeueLTStd-Bd.otf");
        Helpers.changeFont(getActivity(), ((TextView)rootView.findViewById(R.id.txtSketchDescription)), "HelveticaNeueLTStd-Roman.otf");

        imgCalendarSketch = (ImageView)rootView.findViewById(R.id.imgCalendarSketch);

        //e.tito
        imgCalendarSketch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivityNew)getActivity()).showLocationDetail(1, calendar.getSketchUrlJpg(), calendar);
              //  ImageLoader.getInstance().displayImage(calendar.getSketchUrlJpg(), imgCalendarSketch);
              //  controllerBackground(view);
            }
        });


        ImageLoader.getInstance().displayImage(calendar.getSketchUrlJpg(), imgCalendarSketch);

        //HttpImageRequestTask request = new HttpImageRequestTask();
        //request.execute();

        /*ApiImplementation.getService().getSVG(Global.getUserToken(getActivity()),calendar.getSketchUrl().replace(ApiImplementation.getBaseUrl(), "")).enqueue(new Callback<okhttp3.ResponseBody>() {
            @Override
            public void onResponse(Call<okhttp3.ResponseBody> call, Response<okhttp3.ResponseBody> response) {
                //Log.i("respuesta9",Helpers.responseToString(response));
                Log.i("respuesta9","llegó");
                Log.i("respuesta9",String.valueOf(Helpers.responseToString(response)));
                try {
                    //final URL url = new URL(calendar.getSketchUrl());
                    //HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    //InputStream inputStream = urlConnection.getInputStream();

                    ////InputStream streamm = new ByteArrayInputStream(String.valueOf(Helpers.responseToString(response)).getBytes());
                    ////SVG svg = SVGParser.getSVGFromInputStream(streamm);
                    ////imgCalendarSketch.setImageDrawable(svg.createPictureDrawable());

                } catch (Exception e) {
                    Log.e("respuesta9", e.getMessage(), e);
                }
            }

            @Override
            public void onFailure(Call<okhttp3.ResponseBody> call, Throwable t) {
                Log.i("respuesta9","error :s");
                t.printStackTrace();
            }
        });*/
/*
        ApiImplementation.getService().getSVG(Global.getUserToken(getActivity()),calendar.getSketchUrl().replace(ApiImplementation.getBaseUrl(), "")).enqueue(new com.squareup.okhttp.Response() {
            @Override
            public void onResponse(Call<com.squareup.okhttp.Response> call, com.squareup.okhttp.Response response) {
                Log.i("respuesta9",Helpers.responseToString(response));
            }

            @Override
            public void onFailure(Call<com.squareup.okhttp.Response> call, Throwable t) {

            }
        });

        /*OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(calendar.getSketchUrl())
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.i("respuesta8","entro 6");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                Log.i("respuesta8", "entro 1");
                try { Log.i("respuesta8","entro 2");
                    // Specify the path (relative to the 'assets' folder)
                    //Log.i("respuesta8",response.body().+"");
                    final SVG svg = SVGParser.getSVGFromInputStream(response.body().byteStream()); Log.i("respuesta8","entro 3");
                    imgCalendarSketch.setImageDrawable(svg.createPictureDrawable()); Log.i("respuesta8", "entro 4");
                } catch (Exception e) { Log.i("respuesta8","entro 5");
                    e.printStackTrace();
                    // Handle IOException here
                }
            }
        });*/

        /*try { Log.i("respuesta8","entro 21");
            final SVG svg = SVGParser.getSVGFromAsset(getActivity().getAssets(),"test.svg"); Log.i("respuesta8","entro 31");
            imgCalendarSketch.setImageDrawable(svg.createPictureDrawable()); Log.i("respuesta8", "entro 41");
        } catch (Exception e) { Log.i("respuesta8","entro 51");
            Log.i("respuesta8","entro 51");
            e.printStackTrace();
            // Handle IOException here
        }*/

        /*client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                System.out.println("request failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                response.body().byteStream(); // Read the data from the stream
            }
        });

        ApiImplementation.getService().getSVG(Global.getUserToken(getActivity()),calendar.getSketchUrl().replace(ApiImplementation.getBaseUrl(), "")).enqueue(new Callback<Response<ResponseBody>>() {
            @Override
            public void onResponse(Call<Response<ResponseBody>> call, Response<Response<ResponseBody>> response) {
                Log.i("respuesta8","entro 1");
                try { Log.i("respuesta8","entro 2");
                    // Specify the path (relative to the 'assets' folder)
                    final SVG svg = SVGParser.getSVGFromInputStream(response.body().body().byteStream()); Log.i("respuesta8","entro 3");
                    imgCalendarSketch.setImageDrawable(svg.createPictureDrawable()); Log.i("respuesta8", "entro 4");
                } catch (Exception e) { Log.i("respuesta8","entro 5");
                    e.printStackTrace();
                    // Handle IOException here
                }
            }

            @Override
            public void onFailure(Call<Response<ResponseBody>> call, Throwable t) {
                Log.i("respuesta8","entro 6");
                Log.i("respuesta8",t.getMessage());
                t.printStackTrace();
            }
        });*/



        /*rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i("resultado4","onKey");
                if (keyCode == KeyEvent.KEYCODE_BACK) { Log.i("resultado4","KEYCODE_BACK");
                    ((MainActivity) getActivity()).changeFragment(0);
                    return true;
                }
                return true;
            }
        });*/

        return rootView;
    }

    private static String QuestionResponse = "";

    private void makeAsk(){
        final MaterialDialog dialog =  new MaterialDialog.Builder(getActivity())
                .title(null)
                .positiveText(null)
                .negativeText(null)
                .customView(R.layout.dialog_ask_event,false)
                .show();

        Button btnSubmit = (Button)dialog.getView().findViewById(R.id.btnAskEventSubmit);
        final EditText edtSubmit = (EditText)dialog.getView().findViewById(R.id.editAskEvent);


            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!edtSubmit.getText().toString().equals("")) {
                    dialog.cancel();
                    final MaterialDialog dialogLoading = new MaterialDialog.Builder(getActivity())
                            .title("Send question")
                            .content("Please wait...")
                            .progress(true, 0)
                            .cancelable(false)
                            .show();

                    Log.i("respuesta16", edtSubmit.getText().toString());
                    ApiImplementation.getService().sendAskEvent(
                            Global.getUserToken(getActivity()),
                            calendar.getId(),
                            edtSubmit.getText().toString()
                    ).enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            Log.i("respuesta16", "envio exitoso");
                       //     Log.i("respuesta16", Helpers.responseToString(response));

                            SaveQuestion(edtSubmit.getText().toString(), calendar.getId());
                            QuestionResponse = Helpers.responseToString(response);
                            dialogLoading.cancel();
                            new MaterialDialog.Builder(getActivity())
                                    .title("Question")
                                    .content(QuestionResponse)
                                    .positiveText("Accept")
                                    .show();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Log.i("respuesta16", "envio fallido");
                            //Log.i("respuesta16", call.request().body().toString());
                            t.printStackTrace();
                            dialogLoading.cancel();
                            new MaterialDialog.Builder(getActivity())
                                    .title("Question")
                                    .content("An error occurred")
                                    .positiveText("Accept")
                                    .show();
                        }
                    });

                    }else {
                        new MaterialDialog.Builder(getActivity())
                                .title("Question")
                                .content("The field is empty")
                                .positiveText("Accept")
                                .show();
                    }
                }
            });

    }

    private void SaveQuestion(String question, int idNote){

if(question!=null || question != "") {
    String bodyNote = NoteDao.getInstance(getActivity()).getNote(idNote);

    if(!bodyNote.equals("")){
        bodyNote+="\n";
    }

    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
    bodyNote += "Q. "+ format.format(new Date()).toString() +": "+question;

    NoteDao.getInstance(getActivity()).updateNote(bodyNote, idNote);
}

    }


    /*private class HttpImageRequestTask extends AsyncTask<Void, Void, Drawable> {
        @Override
        protected Drawable doInBackground(Void... params) {
            try {

                final URL url = new URL(calendar.getSketchUrl());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();

                InputStream streamm = new ByteArrayInputStream(("").getBytes(StandardCharsets.UTF_8));
                ////SVG svg = SVGParser.getSVGFromInputStream(streamm);
                ///Drawable drawable = svg.createPictureDrawable();
                return drawable;
            } catch (Exception e) {
                Log.e("respuesta", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Drawable drawable) {
            imgCalendarSketch.setImageDrawable(drawable);
        }
    }*/

}
