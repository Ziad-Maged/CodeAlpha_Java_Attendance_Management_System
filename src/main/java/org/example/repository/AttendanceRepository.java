package org.example.repository;

import org.example.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long>{
    List<Attendance> findByStudentId(Long studentId);

    List<Attendance> findByStudentIdAndDate(Long studentId, Date currentDate);
}
