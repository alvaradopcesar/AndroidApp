package iosco.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import iosco.app.R;
import iosco.app.model.entity.Event;
import iosco.app.model.entity.Head;
import iosco.app.model.entity.Row;

/**
 * Created by Victor Casas on 08/02/2016.
 */
public class EveningEventAdapter extends BaseAdapter{

    private Activity activity;
    private ArrayList<Row> lista;
    private static LayoutInflater inflater = null;

    public EveningEventAdapter(Activity activity, ArrayList<Row> lista){
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

        if(lista.get(position) instanceof Event){

            ViewHolder holder;

            if (item == null) {
                LayoutInflater inflater = activity.getLayoutInflater();
                item = inflater.inflate(R.layout.model_event_name_back, null);

                holder = new ViewHolder();
                holder.txtName = (TextView) item.findViewById(R.id.txtEventName);

                item.setTag(holder);
            } else {
                holder = (ViewHolder) item.getTag();
            }

            holder.txtName.setText(((Event) lista.get(position)).getName());

            if(lista.size() > position+1 && lista.get(position+1) instanceof Head){
                (item.findViewById(R.id.viewEventLine)).setVisibility(View.GONE);
            }

        }else{
            ViewHolder holder;

            if (item == null) {
                LayoutInflater inflater = activity.getLayoutInflater();
                item = inflater.inflate(R.layout.model_event_hour, null);

                holder = new ViewHolder();
                holder.txtName = (TextView) item.findViewById(R.id.txtEventHour);

                item.setTag(holder);
            } else {
                holder = (ViewHolder) item.getTag();
            }

            holder.txtName.setText(((Head) lista.get(position)).getName());
        }


        return(item);
    }

    static class ViewHolder{
        TextView txtName;
    }
}
