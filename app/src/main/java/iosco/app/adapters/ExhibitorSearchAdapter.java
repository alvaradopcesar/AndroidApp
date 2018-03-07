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
import iosco.app.model.entity.Exhibitor;
import iosco.app.service.ApiImplementation;

/**
 * Created by usuario on 25/02/2016.
 */
public class ExhibitorSearchAdapter extends ArrayAdapter<Exhibitor> {
    public ExhibitorSearchAdapter(Context context, ArrayList datos) {
        super(context, R.layout.model_exhibitor, datos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View item = convertView;
        ViewHolder holder;

        if(item == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            item = inflater.inflate(R.layout.model_exhibitor, null);

            holder = new ViewHolder();
            holder.name = (TextView)item.findViewById(R.id.txtExhibitorName);
            holder.position = (TextView)item.findViewById(R.id.txtExhibitorPosition);
            holder.imgPerfil = (ImageView) item.findViewById(R.id.exhibitorPhoto);
            item.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)item.getTag();
        }

        holder.name.setText(Html.fromHtml(getItem(position).getFirstName()+" <b>"+getItem(position).getLastName()+"</b>"));
        holder.position.setText(getItem(position).getJobTitle());
if(getItem(position).isHasProfilePicture()) {
    ImageLoader.getInstance().displayImage(ApiImplementation.getBaseUrl() + "api/speaker/" + getItem(position).getId() + "/profilepicture", holder.imgPerfil);
}else{
    holder.imgPerfil.setImageResource(R.drawable.ic_speaker_perfil);;
}
        item.setBackgroundResource(0);

        return(item);
    }

    static class ViewHolder {
        ImageView imgPerfil;
        TextView name;
        TextView position;
    }
}
