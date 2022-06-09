package com.example.demo.persistence;

import com.example.demo.domain.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {
    @Override
    List<Timeslot> findAll();
}
