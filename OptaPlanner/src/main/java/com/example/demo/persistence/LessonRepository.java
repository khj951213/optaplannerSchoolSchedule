package com.example.demo.persistence;

import com.example.demo.domain.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    @Override
    List<Lesson> findAll();
}
