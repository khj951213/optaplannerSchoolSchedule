package com.example.demo.persistence;

import com.example.demo.domain.Lesson;
import com.example.demo.domain.Room;
import com.example.demo.domain.Timeslot;
import com.example.demo.solver.TimeTableController;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
public class ProblemChangedRepositoryEventListener{
    @Autowired
    private TimeTableController timeTableController;

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    private void timeslotCreateSaveDelete(Timeslot timeslot) {
        assertNotSolving();
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    private void roomCreateSaveDelete(Room room) {
        assertNotSolving();
    }

    @HandleBeforeCreate
    @HandleBeforeSave
    @HandleBeforeDelete
    private void lessonCreateSaveDelete(Lesson lesson) {
        assertNotSolving();
    }

    private void assertNotSolving() {
        if(timeTableController.getSolverStatus() != SolverStatus.NOT_SOLVING) {
            throw new IllegalStateException("The solver is solving. Please stop solving first");
        }
    }
}
