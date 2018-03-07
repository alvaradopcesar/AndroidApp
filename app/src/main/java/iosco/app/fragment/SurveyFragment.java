package iosco.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import java.util.ArrayList;
import java.util.Arrays;

import iosco.app.R;
import iosco.app.model.entity.SurveyResultResponse;
import iosco.app.model.entity.SurveyItemResult;
import iosco.app.service.ApiImplementation;
import iosco.app.utils.Global;
import iosco.app.utils.Helpers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SurveyFragment extends Fragment {

    private static SurveyFragment instance;

    private LinearLayout layoutSurveySend;

    public SurveyFragment() {
    }

    public static SurveyFragment getInstance(){
        if(instance == null){
            instance = new SurveyFragment();
        }
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_survey, container, false);

        layoutSurveySend = (LinearLayout)rootView.findViewById(R.id.layoutSurveySend);

        layoutSurveySend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSurvey();
            }
        });

        setupFonts(rootView);

        return rootView;
    }

    private void setupFonts(View rootView){

        ViewGroup container = (ViewGroup)rootView.findViewById(R.id.layoutAsksConteiner);

        for(int i=0; i<container.getChildCount(); i++){
            if(container.getChildAt(i) instanceof ViewGroup) {
                if (((ViewGroup) container.getChildAt(i)).getChildAt(1) instanceof TextView) {
                    Helpers.changeFont(getActivity(), ((TextView) ((ViewGroup) container.getChildAt(i)).getChildAt(1)), "HelveticaNeueLTStd-Bd.otf");
                }else{
                    ViewGroup subContainer = ((ViewGroup)((ViewGroup) container.getChildAt(i)).getChildAt(1));
                    for(int j=0; j<subContainer.getChildCount();j++){
                        if(((ViewGroup)subContainer.getChildAt(j)).getChildAt(0) instanceof TextView){
                            Helpers.changeFont(getActivity(), ((TextView) ((ViewGroup)subContainer.getChildAt(j)).getChildAt(0)), "HelveticaNeueLTStd-Lt.otf");
                        }
                    }
                }
            }
        }

    }

    private void sendSurvey(){
        Log.i("respuesta15","sendSurvey()");
        ArrayList<Integer> listResponses = new ArrayList<>();
        try {
            listResponses.add((int) getView().findViewById(R.id.containerStars1).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars2).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars3).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars4).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars5).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars6).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars7).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars8).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars9).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars10).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars11).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars12).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars13).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars14).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars15).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars16).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars17).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars18).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars19).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars20).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars21).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars22).getTag());
            listResponses.add((int) getView().findViewById(R.id.containerStars23).getTag());


            SurveyItemResult[] results = new SurveyItemResult[23];
            int cont = 0;
            Log.i("respuesta15", "too good");
            SurveyItemResult ent;
            for(Integer n : listResponses){
                ent = new SurveyItemResult();
                ent.setQuestionNumber(cont+1);
                ent.setQuestionValue(n);
                results[cont]=ent;
                cont++;
            }

            SurveyResultResponse bodyReq = new SurveyResultResponse();

            bodyReq.setResults(results);

            final MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                    .title("Send survey")
                    .content("Please wait...")
                    .progress(true, 0)
                    .cancelable(false)
                    .show();
            ApiImplementation.getService().sendSurvey(Global.getUserToken(getActivity()), Arrays.asList(results)).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.i("respuesta15", "envio correcto");
                   // Log.i("respuesta15", Helpers.responseToString(response));
                    new MaterialDialog.Builder(getActivity())
                            .title("Survey")
                            .content(Helpers.responseToString(response))
                            .positiveText("Accept")
                            .show();
                    dialog.cancel();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.i("respuesta15", "envio fallido");
                    Log.i("respuesta15", call.request().body().toString());
                    t.printStackTrace();
                    dialog.cancel();
                    new MaterialDialog.Builder(getActivity())
                            .title("Survey")
                            .content("An error occurred")
                            .positiveText("Accept")
                            .show();
                }
            });
        } catch (Exception e){
            e.printStackTrace();
            Log.i("respuesta15", "almenos uno no fue llenado");
            new MaterialDialog.Builder(getActivity())
                    .title("Survey")
                    .content("Please complete the entire survey")
                    .positiveText("Accept")
                    .show();
        }

    }

    public void manageSubContainer(View v){
        ViewGroup viewParent = (ViewGroup)v.getParent();
        int indexView = viewParent.indexOfChild(v)+1;
        String tag = viewParent.getChildAt(indexView).getTag().toString();
        if(tag.equals("1")){
            Helpers.collapse((ViewGroup) viewParent.getChildAt(indexView));
            viewParent.getChildAt(indexView).setTag("0");
        }else{
            Helpers.expand((ViewGroup) viewParent.getChildAt(indexView));
            viewParent.getChildAt(indexView).setTag("1");
        }
    }

    public void manageStars(View v){
        ViewGroup viewParent = (ViewGroup)v.getParent();
        int indexView = viewParent.indexOfChild(v);
        for(int i=0; i<viewParent.getChildCount(); i++){
            if(i <= indexView){
                ((ImageView)viewParent.getChildAt(i)).setImageResource(R.drawable.ic_survey_start_1);
            }else{
                ((ImageView)viewParent.getChildAt(i)).setImageResource(R.drawable.ic_survey_start_0);
            }
        }
        viewParent.setTag(indexView+1);
    }

}
