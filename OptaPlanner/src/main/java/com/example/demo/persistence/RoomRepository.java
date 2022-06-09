package com.example.demo.persistence;

import com.example.demo.domain.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Override
    List<Room> findAll();
}
