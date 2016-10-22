package sk.beacode.beacodeapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.fragments.MyEventsFragment;
import sk.beacode.beacodeapp.models.Event;


/**
 * Created by Sandra on 21.10.2016.
 */

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsFragment.MyViewHolder> {

    private ArrayList<Event> list;

    public MyEventsAdapter(ArrayList<Event> Data) {
        list = Data;
    }

    @Override
    public MyEventsFragment.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_items, parent, false);
        MyEventsFragment.MyViewHolder holder;
        holder = new MyEventsFragment.MyViewHolder(view);
        return holder;
    }

    /**
     * binds event's data to the view
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final MyEventsFragment.MyViewHolder holder, int position) {

        String stringMonth = (String) android.text.format.DateFormat.format("MMMM",list.get(position).getStart());
        String day = (String) android.text.format.DateFormat.format("dd",list.get(position).getStart()); //20
        Calendar cal = Calendar.getInstance();
        String stringMonthToday = (String) android.text.format.DateFormat.format("MMMM",cal.getTime());
        String dayToday = (String) android.text.format.DateFormat.format("dd",cal.getTime()); //20
        String date;

        if (stringMonth.equals(stringMonthToday) && day.equals(dayToday)){
            date = "Today";
        } else {
            date = day + ". " + stringMonth;
        }

        holder.titleTextView.setText((list.get(position).getName()));
        holder.eventDescription.setText(list.get(position).getDescription());
        holder.eventStartDate.setText(date);
        holder.eventImage.setImageDrawable((list.get(position).getPicture()));
    }

    /**
     *
     * @return number of events
     */

    @Override
    public int getItemCount() {
        return list.size();
    }
}
