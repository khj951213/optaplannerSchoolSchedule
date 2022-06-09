package com.example.demo.persistence;

import com.example.demo.domain.Lesson;
import com.example.demo.domain.TimeTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TimeTableRepository {
    // There is only one timetable, so there is only timeTableId (= problemId)
    public static final Long SINGLETON_TIME_TABLE_ID = 1L;

    @Autowired
    private TimeslotRepository timeslotRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private LessonRepository lessonRepository;

    public TimeTable findById(Long id) {
        if(!SINGLETON_TIME_TABLE_ID.equals(id)) {
            throw new IllegalStateException("There is no timetable with id (" + id + ").");
        }

        return new TimeTable(
                timeslotRepository.findAll(),
                roomRepository.findAll(),
                lessonRepository.findAll()
        );
    }

    public void save(TimeTable timeTable) {
        for(Lesson lesson : timeTable.getLessonList()) {
            lessonRepository.save(lesson);
        }
    }
}
