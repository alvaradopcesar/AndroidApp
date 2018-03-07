package iosco.app.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import iosco.app.R;
import iosco.app.model.entity.File;

/**
 * Created by Victor Casas on 08/02/2016.
 */
public class CalenderAdapter extends BaseAdapter{

    private Activity activity;
    private ArrayList<File> lista;
    private static LayoutInflater inflater = null;

    public CalenderAdapter(Activity activity, ArrayList<File> lista){
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

        if(lista.get(position).isEvent()){
            ViewHolder holder;
                //if (item == null) {
                    LayoutInflater inflater = activity.getLayoutInflater();
                    item = inflater.inflate(R.layout.model_day_detail_back, null);

                    holder = new ViewHolder();
                    holder.txtName = (TextView) item.findViewById(R.id.txtNameEvent);
                    holder.txtTimeEvent = (TextView) item.findViewById(R.id.txtTimeEvent);

                    /*item.setTag(holder);
                } else {
                    holder = (ViewHolder) item.getTag();
                }*/

                holder.txtName.setText(lista.get(position).getName());
                holder.txtTimeEvent.setText(lista.get(position).getDuration());

        }else{
            ViewHolder holder;
            //if(item == null){
                LayoutInflater inflater = activity.getLayoutInflater();
                item = inflater.inflate(R.layout.model_day_back, null);

                holder = new ViewHolder();
                holder.txtName = (TextView)item.findViewById(R.id.txtDay);
                holder.imgState = (ImageView)item.findViewById(R.id.imgDayActive);

                /*item.setTag(holder);
            }
            else
            {
                Log.i("resultado3",lista.get(position).getName());
                holder = (ViewHolder)item.getTag();
                Log.i("resultado3","paso");
            }*/

            holder.txtName.setText(lista.get(position).getName());
            if(lista.get(position).isShow()){
                holder.imgState.setImageResource(R.drawable.arrow_colapse);
            }else{
                holder.imgState.setImageResource(R.drawable.arrow_expand);
            }
        }


        return(item);
    }

    static class ViewHolder{
        TextView txtName;
        TextView txtTimeEvent;
        ImageView imgState;
        ImageView imgCheckCalendar;
    }

    /*static class ViewHolder{
        TextView txtName;
        TextView txtTimeEvent;
    }*/
}
