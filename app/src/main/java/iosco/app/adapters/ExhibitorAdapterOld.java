package iosco.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;

import iosco.app.R;
import iosco.app.model.entity.Exhibitor;

/**
 * Created by Victor Casas on 08/02/2016.
 */
public class ExhibitorAdapterOld extends BaseAdapter{

    private Activity activity;
    private ArrayList<Exhibitor> lista;
    private static LayoutInflater inflater = null;

    public ExhibitorAdapterOld(Activity activity, ArrayList<Exhibitor> lista){
        this.activity = activity;
        this.lista = lista;
        this.inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount(){return lista.size();}

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View item = convertView;

        if(lista.get(position).isExhibitor()){
            ViewHolderExhibitor holder;
            if (item == null) {
                LayoutInflater inflater = activity.getLayoutInflater();
                item = inflater.inflate(R.layout.model_exhibitor, null);

                holder = new ViewHolderExhibitor();
                holder.txtName = (TextView) item.findViewById(R.id.txtExhibitorName);
                holder.txtPosition = (TextView) item.findViewById(R.id.txtExhibitorPosition);
                holder.imgPhoto = (CircularImageView) item.findViewById(R.id.exhibitorPhoto);
                holder.viewLine = (View)item.findViewById(R.id.viewExhibitorLine);

                item.setTag(holder);
            } else {
                    holder = (ViewHolderExhibitor) item.getTag();
            }

            holder.txtName.setText(lista.get(position).getNombre());
            holder.txtPosition.setText(lista.get(position).getPosition());

        }else{
            ViewHolderCapital holder;
            if(item == null){
                LayoutInflater inflater = activity.getLayoutInflater();
                item = inflater.inflate(R.layout.model_day_back, null);

                holder = new ViewHolderCapital();
                holder.txtCapital = (TextView)item.findViewById(R.id.txtCapital);
                holder.viewLine = (View)item.findViewById(R.id.viewCapitalLine);

                item.setTag(holder);
            }
            else{
                holder = (ViewHolderCapital)item.getTag();
            }

            holder.txtCapital.setText(lista.get(position).getNombre());
        }


        return(item);
    }

    static class ViewHolderCapital{
        TextView txtCapital;
        View viewLine;
    }
    static class ViewHolderExhibitor{
        TextView txtName;
        TextView txtPosition;
        ImageView imgPhoto;
        View viewLine;
    }
}
