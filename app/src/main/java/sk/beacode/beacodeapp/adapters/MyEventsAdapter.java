package sk.beacode.beacodeapp.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.fragments.MyEventsFragment;
import sk.beacode.beacodeapp.models.Event;

public class MyEventsAdapter extends RecyclerView.Adapter<MyEventsFragment.MyViewHolder> {

    private List<Event> data = new ArrayList<>();

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

        String stringMonth = (String) DateFormat.format("MMMM", data.get(position).getStart());
        String day = (String) DateFormat.format("dd", data.get(position).getStart()); //20
        Calendar cal = Calendar.getInstance();
        String stringMonthToday = (String) DateFormat.format("MMMM", cal.getTime());
        String dayToday = (String) DateFormat.format("dd", cal.getTime()); //20
        String date;

        if (stringMonth.equals(stringMonthToday) && day.equals(dayToday)){
            date = "Today"; // TODO: remove hardcoded string !
        } else {
            date = day + ". " + stringMonth;
        }

        holder.titleTextView.setText((data.get(position).getName()));
        holder.eventDescription.setText(data.get(position).getDescription());
        holder.eventStartDate.setText(date);

        Bitmap image = data.get(position).getMainImage();

        System.out.println(image);
        if (image != null) {
            holder.eventImage.setImageBitmap(image);
        } else {
            holder.eventImage.setVisibility(View.GONE);
        }

        holder.bind(data.get(position));
    }

    /**
     *
     * @return number of events
     */
    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<Event> data) {
        this.data = data;
        notifyDataSetChanged();
    }
}
