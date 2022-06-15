package com.example.demo.solver;

import com.example.demo.domain.Lesson;
import com.example.demo.domain.Room;
import com.example.demo.domain.TimeTable;
import com.example.demo.domain.Timeslot;
import com.example.demo.persistence.LessonRepository;
import com.example.demo.persistence.RoomRepository;
import com.example.demo.persistence.TimeTableRepository;

import com.example.demo.persistence.TimeslotRepository;
import org.optaplanner.core.api.score.ScoreManager;
import org.optaplanner.core.api.score.buildin.hardsoft.HardSoftScore;
import org.optaplanner.core.api.solver.SolverManager;
import org.optaplanner.core.api.solver.SolverStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping()
public class TimeTableController {
    @Autowired
    private TimeTableRepository timeTableRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private LessonRepository lessonRepository;
    @Autowired
    private TimeslotRepository timeslotRepository;
    @Autowired
    private SolverManager<TimeTable, Long> solverManager;
    @Autowired
    private ScoreManager<TimeTable, HardSoftScore> scoreManager;

    // GET http://localhost:8080/timeTable
    @GetMapping("/timeTable")
    public TimeTable getTimeTable() {
        SolverStatus solverStatus = getSolverStatus();
        TimeTable solution = timeTableRepository.findById(TimeTableRepository.SINGLETON_TIME_TABLE_ID);
        scoreManager.updateScore(solution);//sets the score
        solution.setSolverStatus(solverStatus);
        return solution;
    }


    public SolverStatus getSolverStatus() {
        return solverManager.getSolverStatus(TimeTableRepository.SINGLETON_TIME_TABLE_ID);
    }

    @PostMapping("/timeTable/solve")
    public void solve() {
        solverManager.solveAndListen(TimeTableRepository.SINGLETON_TIME_TABLE_ID,
                timeTableRepository::findById,
                timeTableRepository::save);
    }


    @PostMapping("/lessons")
    public void lessons(@RequestBody Lesson lesson) {
        lessonRepository.save(lesson);
    }

    @DeleteMapping("/lessons/{id}" )
    public void lessons(@PathVariable Long id) {
        lessonRepository.deleteById(id);
    }

    @PostMapping("/rooms")
    public void rooms(@RequestBody Room room) { roomRepository.save(room); }

    @DeleteMapping("/rooms/{id}")
    public void rooms(@PathVariable Long id) { roomRepository.deleteById(id);}

    @GetMapping("/timeslots")
    public List<Timeslot> timeslots() {
        return timeslotRepository.findAll();
    }

    @PostMapping("/timeslots")
    public void timeslot(@RequestBody Timeslot timeslot) {
        timeslotRepository.save(timeslot);
    }

    @DeleteMapping("/timeslots/{id}")
    public void timeslot(@PathVariable Long id) {
        timeslotRepository.deleteById(id);
    }

    @PostMapping("/timeTable/stopSolving")
    public void stopSolving() {
        solverManager.terminateEarly(TimeTableRepository.SINGLETON_TIME_TABLE_ID);
    }


}
