package com.example.lifeorganizer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lifeorganizer.Adapters.MyJobRecyclerViewAdapter;
import com.example.lifeorganizer.Backend.AfterCreateJob;
import com.example.lifeorganizer.Backend.AfterDeleteJob;
import com.example.lifeorganizer.Backend.AfterGetJobs;
import com.example.lifeorganizer.Backend.AfterGetTasks;
import com.example.lifeorganizer.Backend.JobManager;
import com.example.lifeorganizer.Backend.TaskManager;
import com.example.lifeorganizer.Data.Job;
import com.example.lifeorganizer.Data.Task;
import com.example.lifeorganizer.R;
import com.example.lifeorganizer.dialogs.AddJobDialog;
import com.example.lifeorganizer.dialogs.EditJobDialog;
import com.example.lifeorganizer.dialogs.IAddJobDialog;
import com.example.lifeorganizer.dialogs.IEditJobDialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class JobFragment extends Fragment   {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private ArrayList<Job> currentJobs;
    private HashMap<Job, ArrayList<Task>> currentJobsTasks;
    private MyJobRecyclerViewAdapter mAdapter;
    private JobManager jobManager;
    private TaskManager taskManager;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public JobFragment() {
        currentJobs = new ArrayList<>();
        currentJobsTasks = new HashMap<>();
        jobManager = JobManager.getInstance(getContext());
        taskManager = TaskManager.getInstance(getContext());
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static JobFragment newInstance(int columnCount) {
        JobFragment fragment = new JobFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_job_list, container, false);
        View list = view.findViewById(R.id.job_item_list);
        // Set the adapter
        if (list instanceof RecyclerView) {
            Context context = list.getContext();
            RecyclerView recyclerView = (RecyclerView) list;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            mAdapter = new MyJobRecyclerViewAdapter(currentJobs, currentJobsTasks, mListener, getContext());
            recyclerView.setAdapter(mAdapter);
        }
        jobManager.getJobs(new AfterGetJobs() {
            @Override
            public void afterGetJobs(List<Job> jobs) {
                currentJobs.clear();
                currentJobs.addAll(jobs);
                for(int i = 0 ; i < currentJobs.size() ; i ++){
                    final Job current = currentJobs.get(i);
                    taskManager.getTasksForJob(current, new AfterGetTasks() {
                        @Override
                        public void afterGetTasks(List<Task> tasks) {
                            currentJobsTasks.put(current, new ArrayList<Task>(tasks));
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //if (context instanceof OnListFragmentInteractionListener) {
            mListener = new OnListFragmentInteractionListener() {
                @Override
                public void onListFragmentInteraction(final Job item) {
                    EditJobDialog mainDialog = new EditJobDialog();
                    mainDialog.createDialog(getActivity().getSupportFragmentManager(), new IEditJobDialog(){

                        @Override
                        public void onPositiveClicked(String title, String description, Date deadline, final ArrayList<Task> tasks) {
                            final Job job = new Job(title, description, deadline);
                            jobManager.deleteJob(item, new AfterDeleteJob() {
                                @Override
                                public void afterDeleteJob() {
                                    currentJobs.remove(item);
                                    currentJobsTasks.remove(item);
                                    mAdapter.notifyDataSetChanged();
                                    jobManager.createJob(job, tasks, new AfterCreateJob() {
                                        @Override
                                        public void afterCreateJob(final Job job) {
                                            currentJobs.add(job);
                                            mAdapter.notifyDataSetChanged();
                                            taskManager.getTasksForJob(job, new AfterGetTasks() {
                                                @Override
                                                public void afterGetTasks(List<Task> tasks) {
                                                    currentJobsTasks.put(job, new ArrayList<Task>(tasks));
                                                    mAdapter.notifyDataSetChanged();
                                                }
                                            });
                                        }
                                    });
                                }
                            });

                        }
                    }, item, currentJobsTasks.get(item));
                    mainDialog.showDialog();
                }
            };
        /*} else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Job item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((FloatingActionButton) view.findViewById(R.id.add_job_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddJobDialog mainDialog = new AddJobDialog();
                mainDialog.createDialog(getActivity().getSupportFragmentManager(), new IAddJobDialog() {

                    @Override
                    public void onPositiveClicked(String title, String description, Date deadline, ArrayList<Task> tasks) {
                        Job job = new Job(title, description, deadline);
                        jobManager.createJob(job, tasks, new AfterCreateJob() {
                            @Override
                            public void afterCreateJob(final Job job) {
                                Log.d("I came back", "Indeed");
                                currentJobs.add(job);
                                mAdapter.notifyDataSetChanged();
                                taskManager.getTasksForJob(job, new AfterGetTasks() {
                                    @Override
                                    public void afterGetTasks(List<Task> tasks) {
                                        currentJobsTasks.put(job, new ArrayList<Task>(tasks));
                                        mAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        });

                    }
                });
                mainDialog.showDialog();
            }
        });
    }
}
