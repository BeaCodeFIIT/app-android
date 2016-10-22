package sk.beacode.beacodeapp.fragments;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.adapters.MyEventsAdapter;
import sk.beacode.beacodeapp.models.Event;

@EFragment(R.layout.fragment_my_events)
public class MyEventsFragment extends Fragment {

    RecyclerView MyRecyclerView;
    ArrayList<Event> myEvents = new ArrayList<Event>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myEvents = initzializeEvents();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_fragment_my_events);
        }

        View view = inflater.inflate(R.layout.fragment_my_events, container, false);
        MyRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (myEvents.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new MyEventsAdapter(myEvents));
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);

        return view;

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        //@ViewById(R.id.event_title)
        public TextView titleTextView;

       // @ViewById(R.id.event_description)
        public TextView eventDescription;

       // @ViewById(R.id.event_date)
        public TextView eventStartDate;

        //@ViewById(R.id.event_image)
        public ImageView eventImage;

        public MyViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.event_title);
            eventDescription = (TextView) v.findViewById(R.id.event_description);
            eventStartDate = (TextView) v.findViewById(R.id.event_date);
            eventImage = (ImageView) v.findViewById(R.id.event_image);
            titleTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }
    }

    private ArrayList<Event> initzializeEvents(){

        myEvents.clear();
        String titles[] = {"SLOVAK PRESS PHOTO 2016","BRATISLAVA IN MOVEMENT","EVENT"};
        String locations[] = {"Bratislava", "Zilina","Nitra"};
        Calendar startTime[] = {new GregorianCalendar(2016,10,30),new GregorianCalendar(2016,9,24),new GregorianCalendar(2017,1,1)};
        Calendar endTime[] = {new GregorianCalendar(2016,11,22),new GregorianCalendar(2016,11,22),new GregorianCalendar(2017,1,22)};
        int  images[] = {R.drawable.image,R.drawable.images,R.drawable.image};
        String descriptions[] = {"Slovak Press Photo is a contest and a subsequent photo exhibition presenting an independent and authentic visual testimony of life, as seen by journalists from Slovakia and Eastern Europe." +
                "The aim of the contest and of the exhibition is to allow the public to become the eye-witness of past year´s events and phenomena. This year, authors applied for nine photographic categories and the international Short Video category.  191 photographers applied for the contest and entered 1,522 photos in total." +
                "The jury decided to award the main prize of the contest – the Grand Prix – to photographer, film director and camera operator Ivan Holub for his single photograph Dinner. The photo was submitted to the Daily Life category. It shows a group of Pygmean children in south Burundi who survive only thanks to food provided once a day under a Slovak nutrition programme. The children are waiting silently until they get their portion of meal. This silence continues also while eating. The picture was shot during the production of a video document." +
                "", "20th international festival of contemporary dance. Bratislava in Movement is a regular dance event of international significance, which offers works of high profile dance theaters and soloists from all over the world" +
                "Established in 1997,  shaped by a community of dancers and teachers the festival grew into a respected event.\n" +
                "\n", "Slovak Press Photo is a contest and a subsequent photo exhibition presenting an independent and authentic visual testimony of life, as seen by journalists from Slovakia and Eastern Europe." +
                "The aim of the contest and of the exhibition is to allow the public to become the eye-witness of past year´s events and phenomena. This year, authors applied for nine photographic categories and the international Short Video category.  191 photographers applied for the contest and entered 1,522 photos in total." +
                "The jury decided to award the main prize of the contest – the Grand Prix – to photographer, film director and camera operator Ivan Holub for his single photograph Dinner. The photo was submitted to the Daily Life category. It shows a group of Pygmean children in south Burundi who survive only thanks to food provided once a day under a Slovak nutrition programme. The children are waiting silently until they get their portion of meal. This silence continues also while eating. The picture was shot during the production of a video document." +
                ""};
        ArrayList<Event> events = new ArrayList<Event>();

        for (int i = 0; i < 3; i++){
            Event event = new Event();
            event.setName(titles[i]);
            event.setLocation(locations[i]);
            event.setStart(startTime[i].getTime());
            event.setEnd(endTime[i].getTime());
            event.setPicture(getResources().getDrawable(images[i]));
            event.setDescription(descriptions[i]);
            events.add(event);

        }

        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return o1.getStart().compareTo(o2.getStart());
            }
        });

        return events;
    }
}