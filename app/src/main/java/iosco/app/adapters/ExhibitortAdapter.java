package iosco.app.adapters;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import iosco.app.R;
import iosco.app.service.ApiImplementation;
import iosco.app.utils.Helpers;

public class ExhibitortAdapter extends BaseAdapter {

    private Context context;

    public static abstract class Row {}
    
    public static final class Section extends Row {
        public final String text;

        public Section(String text) {
            this.text = text;
        }
    }
    
    public static final class Item extends Row {
        public final int id;
        public final String text;
        public final String secondName;
        public final String position;
        public final boolean hasProfilePicture;


        public Item(String text,String secondName,String position, int id, boolean hasProfilePicture) {
            this.text = text;
            this.secondName = secondName;
            this.position = position;
            this.id = id;
            this.hasProfilePicture = hasProfilePicture;
        }

    }


    
    private List<Row> rows;
    
    public void setRows(List<Row> rows) {
        this.rows = rows;
    }

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Row getItem(int position) {
        return rows.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    
    @Override
    public int getItemViewType(int position) {
        if (getItem(position) instanceof Section) {
            return 1;
        } else {
            return 0;
        }
    }

    public void setContext(Context context){
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (getItemViewType(position) == 0) { // Item
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = (RelativeLayout) inflater.inflate(R.layout.model_exhibitor, parent, false);
            }
            
            Item item = (Item) getItem(position);
            TextView txtName = (TextView) view.findViewById(R.id.txtExhibitorName);
            TextView txtPosition = (TextView) view.findViewById(R.id.txtExhibitorPosition);
            ImageView imgPerfil = (ImageView) view.findViewById(R.id.exhibitorPhoto);
            txtName.setText(Html.fromHtml(item.text+" <b>"+item.secondName+"</b>"));
            txtPosition.setText(item.position);
            imgPerfil.setImageResource(R.drawable.ic_speaker_perfil);
            Helpers.changeFont(context, txtName, "HelveticaNeueLTStd-Roman.otf");
            Helpers.changeFont(context, txtPosition, "HelveticaNeueLTStd-Md.otf");

            /*ApiImplementation.getServiceImage(context).with(context)
                   .load(ApiImplementation.getBaseUrl() + "api/speaker/"+item.id+"/profilepicture").into(imgPerfil);*/
if(item.hasProfilePicture) {
    ImageLoader.getInstance().displayImage(ApiImplementation.getBaseUrl() + "api/speaker/" + item.id + "/profilepicture", imgPerfil);
    //Picasso.with(context).load("https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xfa1/v/t1.0-1/c15.0.50.50/p50x50/10354686_10150004552801856_220367501106153455_n.jpg?oh=6bd9746bc95d00713749793051da72ab&oe=575E452F&__gda__=1465817095_6dd02167bc923b3c12caebfce4d6ce18").into(imgPerfil);
}else{
    imgPerfil.setImageResource(R.drawable.ic_speaker_perfil);
}
            Log.i("respuesta6",ApiImplementation.getBaseUrl() + "api/speaker/"+item.id+"/profilepicture");

            if(getCount() > position+1 && getItemViewType(position+1) == 1){
                (view.findViewById(R.id.viewExhibitorLine)).setVisibility(View.GONE);
            }
        } else { // Section
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = (RelativeLayout) inflater.inflate(R.layout.model_capital_exhibitor, parent, false);
            }
            
            Section section = (Section) getItem(position);
            TextView textView = (TextView) view.findViewById(R.id.txtCapital);
            textView.setText(section.text);

            Helpers.changeFont(context, textView, "HelveticaNeueLTStd-Bd.otf");

            if(position == 0){
                ((View)view.findViewById(R.id.viewCapitalLine)).setVisibility(View.GONE);
            }
        }
        
        return view;
    }

}
