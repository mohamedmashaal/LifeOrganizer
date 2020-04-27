package com.example.lifeorganizer.fragments;

import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.alamkanak.weekview.WeekViewLoader;
import com.example.lifeorganizer.Backend.AfterCreateEvent;
import com.example.lifeorganizer.Backend.AfterGetEvents;
import com.example.lifeorganizer.Backend.EventManager;
import com.example.lifeorganizer.Data.Event;
import com.example.lifeorganizer.Data.WeekEvent;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.dialogs.AddEventDialog;
import com.example.lifeorganizer.dialogs.IAddEventDialog;
import com.example.lifeorganizer.interfaces.iOnEventOperation;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class FragmentEvent extends Fragment {

    private WeekView mWeekView;
    private LinkedList<WeekEvent> weekEvents = new LinkedList<>();

    private boolean listAdded = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i("Created", "heeeeeeeeed");
        View rootView = inflater.inflate(R.layout.fragment_event, container, false);

        getEvents();

        mWeekView = (WeekView) rootView.findViewById(R.id.weekView);
        mWeekView.setWeekViewLoader(weekViewLoader);

        mWeekView.setOnEventClickListener(new WeekView.EventClickListener() {
            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
/*
                transaction.replace(R.id.habit_view_fragment_container,
                        FragmentHabitView.newInstance(habit));
*/
                transaction.replace(R.id.main_container,
                        FragmentEventView.newInstance(new iOnEventOperation() {
                            @Override
                            public void onEventOperation() {
                                getEvents();
                            }
                        }, (int) event.getId()));

                transaction.addToBackStack(null);
                transaction.commit();
            }
        });


        return rootView;
    }

    private void getEvents() {
        weekEvents = new LinkedList<>();
        EventManager.getInstance(getActivity()).getEvents(new AfterGetEvents() {
            @Override
            public void afterGetEvents(List<Event> event) {
                Log.i("Events", event.size() + "");
                for (Event e : event)
                    addWeekEventFromEvent(e);
                mWeekView.notifyDatasetChanged();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((FloatingActionButton) view.findViewById(R.id.fb_add_event)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEventDialog mainDialog = new AddEventDialog();
                mainDialog.createDialog(getActivity().getSupportFragmentManager(), new IAddEventDialog() {

                    @Override
                    public void onPositiveClicked(String title, String description, int startHours, int startMins, int duration, Date startDate) {
                        final Event event = new Event(title, description, startDate, startHours, startMins, duration);
                        EventManager.getInstance(getActivity()).createEvent(event, new AfterCreateEvent() {
                            @Override
                            public void afterCreateEvent() {
                                getEvents();
                            }
                        });
                    }
                }, "Add Event");
                mainDialog.showDialog();
            }
        });

    }

    private void addWeekEventFromEvent(Event event) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(event.getStartDate());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);

        weekEvents.add(new WeekEvent(event.getId(), event.getTitle(), year, month, day, event.getStartHour(), event.getStartMinute(), year, month, day, event.getStartHour() + event.getDuration(), event.getStartMinute()));
    }


    private WeekViewLoader weekViewLoader = new WeekViewLoader() {
        @Override
        public double toWeekViewPeriodIndex(Calendar instance) {
            return 0;
        }

        @Override
        public List<? extends WeekViewEvent> onLoad(int periodIndex) {
            Log.i("lol", periodIndex + "");

            if (periodIndex == 0)
                return weekEvents;
            else
                return new LinkedList<>();
        }
    };
}
