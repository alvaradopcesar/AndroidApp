package iosco.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import iosco.app.R;
import iosco.app.model.entity.CalendarEntity;
import iosco.app.model.entity.Exhibitor;
import iosco.app.utils.Helpers;

/**
 * Created by usuario on 25/02/2016.
 */
public class CalendarSearchAdapter extends ArrayAdapter<CalendarEntity> {

    public CalendarSearchAdapter(Context context, ArrayList datos) {
        super(context, R.layout.model_exhibitor, datos);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View item = convertView;
        ViewHolder holder;

        if(item == null)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            item = inflater.inflate(R.layout.model_day_detail, null);

            holder = new ViewHolder();
            holder.time = (TextView)item.findViewById(R.id.txtTimeEvent);
            holder.name = (TextView)item.findViewById(R.id.txtNameEvent);



            item.setTag(holder);
        }
        else
        {
            holder = (ViewHolder)item.getTag();
        }

        String day =   getItem(position).getStartDay() + " " + Helpers.getNameMonth(getItem(position).getStartMonth()) + " 2016";
        holder.name.setText(getItem(position).getTitle());
        holder.time.setText(day + " / "+getItem(position).getDuration());


        item.setBackgroundResource(0);

        return(item);
    }

    static class ViewHolder {
        TextView time;
        TextView name;
        TextView fecha;
    }
}
