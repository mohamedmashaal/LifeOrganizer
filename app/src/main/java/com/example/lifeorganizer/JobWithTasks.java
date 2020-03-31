package com.example.lifeorganizer;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class JobWithTasks {
    @Embedded
    public Job job;
    @Relation(
            parentColumn = "id",
            entityColumn = "jobID"
    )
    public List<Task> tasks;
}
