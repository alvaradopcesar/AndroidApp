package iosco.app.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import iosco.app.R;
import iosco.app.adapters.AssistantAdapter;
import iosco.app.model.entity.Assistant;
import iosco.app.model.entity.Exhibitor;

import java.util.Locale;
import java.util.regex.Pattern;

import iosco.app.adapters.AssistantAdapter.Item;
import iosco.app.adapters.AssistantAdapter.Row;
import iosco.app.adapters.AssistantAdapter.Section;
import iosco.app.service.ApiImplementation;
import iosco.app.utils.Global;
import iosco.app.utils.Helpers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.util.Log;
import android.view.Gravity;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class AssistantsFragment extends Fragment {

    private static AssistantsFragment instance;

    private AssistantAdapter adapter = new AssistantAdapter();
    private GestureDetector mGestureDetector;
    private List<Object[]> alphabet = new ArrayList<>();
    private HashMap<String, Integer> sections = new HashMap<String, Integer>();
    private int sideIndexHeight;
    private static float sideIndexX;
    private static float sideIndexY;
    private int indexListSize;
    private ListView lstLista;
    private ListAdapter listAdapter;
    private ProgressBar progressBar;

    private List<Assistant> listExhibitors = new ArrayList<>();
    private List<Row> rows;
    private static ArrayList<Assistant> staticList;

    private int imagenesCargadas = 0;

    private boolean startFragment;

    class SideIndexGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            sideIndexX = sideIndexX - distanceX;
            sideIndexY = sideIndexY - distanceY;

            if (sideIndexX >= 0 && sideIndexY >= 0) {
                displayListItem();
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }

    public static AssistantsFragment getInstance(){
        if(instance == null){
            instance = new AssistantsFragment();
        }
        return instance;
    }

    private AssistantsFragment() {

    }

    public static ArrayList<Assistant> getList(){
        return staticList;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_assistants, container, false);

        lstLista = (ListView) rootView.findViewById(R.id.listAssistant);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarFragmentAttendees);

        mGestureDetector = new GestureDetector(getActivity(), new SideIndexGestureListener());

        ApiImplementation.configImageLoader(getActivity());

        Log.i("resultado3", "entra");
        rootView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.i("resultado3", "guuuuu");
                if (mGestureDetector.onTouchEvent(motionEvent)) {
                    //return true;
                } else {
                    //return false;
                }
                return true;
            }
        });

        adapter.setContext(getActivity());

        if(!startFragment) {

            try {
                getExhibitors();
            }catch (Exception e){}

        }else{
            progressBar.setVisibility(View.GONE);
            adapter.setRows(rows);
            lstLista.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            try {
                updateList(rootView);
            }catch (Exception e){}
        }
        return rootView;
    }

    public boolean touch(MotionEvent e){
        if (mGestureDetector.onTouchEvent(e)) {
            return true;
        } else {
            return false;
        }
    }

    public void updateList(View view) {
        LinearLayout sideIndex = (LinearLayout)view.findViewById(R.id.sideIndexAssistant);
        sideIndex.removeAllViews();
        indexListSize = alphabet.size();
        if (indexListSize < 1) {
            return;
        }

        int indexMaxSize = (int) Math.floor(sideIndex.getHeight() / 20);
        int tmpIndexListSize = indexListSize;
        while (tmpIndexListSize > indexMaxSize) {
            tmpIndexListSize = tmpIndexListSize / 2;
        }
        double delta;
        if (tmpIndexListSize > 0) {
            delta = indexListSize / tmpIndexListSize;
        } else {
            delta = 1;
        }

        TextView tmpTV;
        for (double i = 1; i <= indexListSize; i = i + delta) {
            Object[] tmpIndexItem = alphabet.get((int) i - 1);
            String tmpLetter = tmpIndexItem[0].toString();

            tmpTV = new TextView(getActivity());
            tmpTV.setText(tmpLetter);
            tmpTV.setGravity(Gravity.CENTER);
            tmpTV.setTextSize(getResources().getDimension(R.dimen.index_letter_size));
            tmpTV.setTextColor(Color.parseColor("#999999"));
            Helpers.changeFont(getActivity(),tmpTV,"HelveticaNeueLTStd-Md.otf");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
            tmpTV.setLayoutParams(params);



            /*LayoutInflater inflater = LayoutInflater.from(getActivity());
            TextView txtL = (TextView) inflater.inflate(R.layout.model_letter, null, false);
            txtL.setText(tmpLetter);*/

            sideIndex.addView(tmpTV);
        }

        sideIndexHeight = sideIndex.getHeight();

        sideIndex.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // now you know coordinates of touch
                sideIndexX = event.getX();
                sideIndexY = event.getY();

                // and can display a proper item it country list
                displayListItem();

                return false;
            }
        });
    }

    public void displayListItem() {
        LinearLayout sideIndex = (LinearLayout)getView().findViewById(R.id.sideIndexAssistant);
        sideIndexHeight = sideIndex.getHeight();
        // compute number of pixels for every side index item
        double pixelPerIndexItem = (double) sideIndexHeight / indexListSize;

        // compute the item index for given event position belongs to
        int itemPosition = (int) (sideIndexY / pixelPerIndexItem);

        // get the item (we can do it since we know item index)
        if (itemPosition < alphabet.size()) {
            Object[] indexItem = alphabet.get(itemPosition);
            int subitemPosition = sections.get(indexItem[0]);

            //ListView listView = (ListView) findViewById(android.R.id.list);
            lstLista.setSelection(subitemPosition);
        }
    }

    private void getExhibitors(){
        listExhibitors = new ArrayList<>();
        staticList = new ArrayList<>();
        /*Assistant e1 = new Assistant();
        e1.setNombre("Amon");
        e1.setApellido("Allardice");
        e1.setPosition("BBC NEWS");

        Assistant e2 = new Assistant();
        e2.setNombre("Alan");
        e2.setApellido("T. Hit");
        e2.setPosition("Banking Standards Board");

        Assistant e3 = new Assistant();
        e3.setNombre("Bean");
        e3.setApellido("Ridhard");
        e3.setPosition("Executive Manager");

        Assistant e4 = new Assistant();
        e4.setNombre("Crist");
        e4.setApellido("Rupert");
        e4.setPosition("Executive Manager");

        Assistant e5 = new Assistant();
        e5.setNombre("Dionicius");
        e5.setApellido("Lopold");
        e5.setPosition("Executive Manager");

        listExhibitors.add(e1);
        listExhibitors.add(e2);
        listExhibitors.add(e3);
        listExhibitors.add(e4);
        listExhibitors.add(e5);*/
        int g = 32;
        ApiImplementation.getService().getAssistants(Global.getUserToken(getActivity())).enqueue(new Callback<ArrayList<Assistant>>() {
            @Override
            public void onResponse(Call<ArrayList<Assistant>> call, Response<ArrayList<Assistant>> response) {
                if(response.isSuccess()){
                    listExhibitors.addAll(response.body());
                    /*
                    Collections.sort(listExhibitors, new Comparator<Assistant>() {
                        public int compare(Assistant v1, Assistant v2) {
                            return v1.getFirstName().compareTo(v2.getFirstName());
                        }
                    });*/
                    staticList.addAll(listExhibitors);
                    configView();
                    /*
                    ///
                    for(int i=0; i<11; i++){
                        ImageLoader.getInstance().loadImage(ApiImplementation.getBaseUrl() + "api/attendee/" + listExhibitors.get(i).getId() + "/profilepicture", new ImageLoadingListener() {
                            @Override
                            public void onLoadingStarted(String s, View view) {

                            }

                            @Override
                            public void onLoadingFailed(String s, View view, FailReason failReason) {
                                imagenesCargadas();
                            }

                            @Override
                            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                                imagenesCargadas();
                            }

                            @Override
                            public void onLoadingCancelled(String s, View view) {
                                imagenesCargadas();
                            }
                        });
                    }
*/


                }
            }

            @Override
            public void onFailure(Call<ArrayList<Assistant>> call, Throwable t) {

            }
        });
    }

    private void imagenesCargadas(){
        imagenesCargadas++;
        if(imagenesCargadas >=10) {
            try {
                configView();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void configView(){
        rows = new ArrayList<Row>();
        int start = 0;
        int end = 0;
        String previousLetter = null;
        Object[] tmpIndexItem = null;
        Pattern numberPattern = Pattern.compile("[0-9]");
        alphabet.clear();

        for (Assistant country : listExhibitors) {
            String firstLetter = country.getLastName().substring(0, 1).toUpperCase();

            // Group numbers together in the scroller
            if (numberPattern.matcher(firstLetter).matches()) {
                firstLetter = "#";
            }

            // If we've changed to a new letter, add the previous letter to the alphabet scroller
            if (previousLetter != null && !firstLetter.equals(previousLetter)) {
                end = rows.size() - 1;
                tmpIndexItem = new Object[3];
                tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
                tmpIndexItem[1] = start;
                tmpIndexItem[2] = end;
                alphabet.add(tmpIndexItem);

                start = end + 1;
            }

            // Check if we need to add a header row
            if (!firstLetter.equals(previousLetter)) {
                rows.add(new Section(firstLetter));
                sections.put(firstLetter, start);
            }

            // Add the country to the list
            rows.add(new Item(country.getFirstName(), country.getLastName(),
                    country.getOrganization().getName(),country.getId(),
                    country.getCountry().getName(),country.getJobTitle(), country.isHasProfilePicture()));
            previousLetter = firstLetter;
        }

        if (previousLetter != null) {
            // Save the last letter
            tmpIndexItem = new Object[3];
            tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
            tmpIndexItem[1] = start;
            tmpIndexItem[2] = rows.size() - 1;
            alphabet.add(tmpIndexItem);
        }

        adapter.setRows(rows);
        lstLista.setAdapter(adapter);

        updateList(getView());

        progressBar.setVisibility(View.GONE);

        startFragment = !startFragment;
    }



}
