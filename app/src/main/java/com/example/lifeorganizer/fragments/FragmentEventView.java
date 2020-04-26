package com.example.lifeorganizer.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lifeorganizer.Backend.AfterDeleteEvent;
import com.example.lifeorganizer.Backend.AfterEditEvent;
import com.example.lifeorganizer.Backend.AfterGetEvent;
import com.example.lifeorganizer.Backend.EventManager;
import com.example.lifeorganizer.Data.Event;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.dialogs.AddEventDialog;
import com.example.lifeorganizer.dialogs.IAddEventDialog;
import com.example.lifeorganizer.interfaces.iOnEventOperation;

import java.util.Date;

public class FragmentEventView extends Fragment {


    private static Event event;
    private static int eventID;
    private static iOnEventOperation onEventOperation;

    public static FragmentEventView newInstance(iOnEventOperation onEventOperation, int eventID) {
        FragmentEventView.onEventOperation = onEventOperation;
        FragmentEventView.eventID = eventID;
        FragmentEventView fragment = new FragmentEventView();
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_view, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EventManager.getInstance(getActivity()).getEvent(eventID, new AfterGetEvent() {
            @Override
            public void afterGetEvent(Event event) {
                FragmentEventView.event = event;
                Log.i("Reall", event.toString());
                showData(view, event);
            }
        });

        ((Button) view.findViewById(R.id.event_delete)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setMessage("Are you sure you want to delete this Event?")
                        .setTitle("Delete Event");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        EventManager.getInstance(getActivity()).deleteEvent(event, new AfterDeleteEvent() {

                            @Override
                            public void afterDeleteEvent() {
                                getActivity().onBackPressed();
                                FragmentEventView.onEventOperation.onEventOperation();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        ((Button)view.findViewById(R.id.event_edit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddEventDialog mainDialog = new AddEventDialog();
                mainDialog.createDialog(getActivity().getSupportFragmentManager(), new IAddEventDialog() {
                    @Override
                    public void onPositiveClicked(String title, String description, int startHours, int startMins, int duration, Date startDate) {
                        final Event event = new Event(title, description, startDate, startHours, startMins, duration);
                        event.setId(eventID);
                        EventManager.getInstance(getActivity()).editEvent(event, new AfterEditEvent() {
                            @Override
                            public void afterEditEvent() {
                                FragmentEventView.onEventOperation.onEventOperation();
                                showData(view, event);
                            }
                        });
                    }
                }, "Edit Event");
                mainDialog.showDialog();
            }
        });

    }

    private void showData(View view, Event event){
        ((TextView) view.findViewById(R.id.event_name)).setText(event.getTitle());
        ((TextView) view.findViewById(R.id.event_description)).setText(event.getDescription());
        ((TextView) view.findViewById(R.id.event_start_hours)).setText(String.valueOf(event.getStartHour()));
        ((TextView) view.findViewById(R.id.event_min)).setText(String.valueOf(event.getStartMinute()));
        ((TextView) view.findViewById(R.id.event_duration)).setText(String.valueOf(event.getDuration()));
        ((TextView) view.findViewById(R.id.event_date)).setText(event.getStartDate().toString());
    }

}
