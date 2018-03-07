package iosco.app.adapters;

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

import java.util.List;

import iosco.app.R;
import iosco.app.service.ApiImplementation;
import iosco.app.utils.Helpers;

public class AssistantAdapter extends BaseAdapter {

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
        public final String jurisdictionName;
        public final String cargo;
        public final boolean hasProfilePicture;
        public Item(String text,String secondName,String position, int id, String jurisdictionName, String cargo, boolean hasProfilePicture) {
            this.text = text;
            this.secondName = secondName;
            this.position = position;
            this.id = id;
            this.jurisdictionName = jurisdictionName;
            this.cargo = cargo;
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
            //if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = (RelativeLayout) inflater.inflate(R.layout.model_assistant, parent, false);
            //}
            
            Item item = (Item) getItem(position);
            TextView txtName = (TextView) view.findViewById(R.id.txtAssistantName);
            TextView txtLastName = (TextView) view.findViewById(R.id.txtAssistantLastName);
            TextView txtCargo = (TextView) view.findViewById(R.id.txtAssistantCargo);
            TextView txtJurisdiction = (TextView) view.findViewById(R.id.txtAssistantJurisdiction);
            TextView txtPosition = (TextView) view.findViewById(R.id.txtAssistantPosition);
            ImageView imgPerfil = (ImageView) view.findViewById(R.id.AssistantPhoto);

            imgPerfil.setImageResource(R.drawable.ic_speaker_perfil);
            txtName.setText(Html.fromHtml(item.text+" <B>"+item.secondName+"</B>"));
      //      txtLastName.setText(" "+item.secondName);
            txtLastName.setText("");
            txtCargo.setText(item.cargo);
            txtPosition.setText(item.position);
            txtJurisdiction.setText(item.jurisdictionName);

            Helpers.changeFont(context, txtName, "HelveticaNeueLTStd-Roman.otf");
            Helpers.changeFont(context, txtLastName, "HelveticaNeueLTStd-Md.otf");
            Helpers.changeFont(context, txtCargo, "HelveticaNeueLTStd-Md.otf");
            Helpers.changeFont(context, txtPosition, "HelveticaNeueLTStd-Md.otf");
            Helpers.changeFont(context, txtJurisdiction, "HelveticaNeueLTStd-Md.otf");

            if(item.hasProfilePicture) {

                ImageLoader.getInstance().displayImage(ApiImplementation.getBaseUrl() + "api/attendee/" + item.id + "/profilepicture", imgPerfil);

            }else{
                imgPerfil.setImageResource(R.drawable.ic_speaker_perfil);
            }
            /*if(getCount() > position+1 && getItemViewType(position+1) == 1){
                (view.findViewById(R.id.viewAssistantLine)).setVisibility(View.GONE);
            }*/

        } else { // Section
            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = (RelativeLayout) inflater.inflate(R.layout.model_capital_assistant, parent, false);
            }

            if(position == 0){
                (view.findViewById(R.id.viewCapitalLine)).setVisibility(View.GONE);
            }
            
            Section section = (Section) getItem(position);
            TextView textView = (TextView) view.findViewById(R.id.txtCapital);
            Helpers.changeFont(context, textView, "HelveticaNeueLTStd-Bd.otf");
            textView.setText(section.text);
        }

        Log.i("respuesta11",position+"");
        
        return view;
    }

}
