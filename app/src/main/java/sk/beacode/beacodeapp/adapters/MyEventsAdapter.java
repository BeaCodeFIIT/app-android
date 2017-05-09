package sk.beacode.beacodeapp.adapters;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.fragments.MyEventsFragment;
import sk.beacode.beacodeapp.models.Event;
import sk.beacode.beacodeapp.models.Image;


/**
 * Adapter for displaying events in the My Events fragment.
 */
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
            date = "Today";
        } else {
            date = day + ". " + stringMonth;
        }

        holder.titleTextView.setText((data.get(position).getName()));
        holder.eventDescription.setText(data.get(position).getDescription());
        holder.eventStartDate.setText(date);

        Image image = data.get(position).getMainImage();

        if (image != null) {
            Glide.with(holder.eventImage.getContext()).load(image.getUri()).into(holder.eventImage);
        } else {
            Glide.clear(holder.eventImage);
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
