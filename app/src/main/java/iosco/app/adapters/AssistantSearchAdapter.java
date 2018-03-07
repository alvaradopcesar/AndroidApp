package iosco.app.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

import iosco.app.R;
import iosco.app.model.entity.Assistant;
import iosco.app.model.entity.CalendarEntity;
import iosco.app.model.entity.Exhibitor;
import iosco.app.service.ApiImplementation;

/**
 * Created by usuario on 25/02/2016.
 */
public class AssistantSearchAdapter extends ArrayAdapter<Assistant> {

    public AssistantSearchAdapter(Context context, ArrayList<Assistant> datos) {
        super(context, R.layout.model_assistant, datos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View item = convertView;
        ViewHolder holder;

        if(item == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            item = inflater.inflate(R.layout.model_assistant, null);

            holder = new ViewHolder();
            holder.name = (TextView)item.findViewById(R.id.txtAssistantName);
            //holder.lastName = (TextView)item.findViewById(R.id.txtAssistantLastName);
            holder.lastName = (TextView)item.findViewById(R.id.txtAssistantLastName);
            holder.position = (TextView)item.findViewById(R.id.txtAssistantPosition);
            holder.institution = (TextView)item.findViewById(R.id.txtAssistantCargo);
            holder.jurisdiction= (TextView)item.findViewById(R.id.txtAssistantJurisdiction);
            holder.imgPerfil = (ImageView) item.findViewById(R.id.AssistantPhoto);



            item.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)item.getTag();
        }


        holder.name.setText(Html.fromHtml(getItem(position).getFirstName()+" <b>"+getItem(position).getLastName()+"</b>"));
      //  holder.lastName.setText(" "+getItem(position).getLastName());
        holder.lastName.setText("");
        holder.institution.setText(getItem(position).getOrganization().getName());
        holder.jurisdiction.setText(getItem(position).getCountry().getName());
        holder.position.setText(getItem(position).getJobTitle());

        if(getItem(position).isHasProfilePicture()) {

            ImageLoader.getInstance().displayImage(ApiImplementation.getBaseUrl() + "api/attendee/" + getItem(position).getId() + "/profilepicture", holder.imgPerfil);

        }else{
            holder.imgPerfil.setImageResource(R.drawable.ic_speaker_perfil);
        }
        return(item);
    }

    static class ViewHolder {
        TextView name;
        TextView lastName;
        TextView position;
        TextView jurisdiction;
        TextView institution;
        ImageView imgPerfil;
    }
}
