package org.example.service;

import org.example.model.Attendance;
import java.util.List;

public interface AttendanceService {
    List<Attendance> getAllAttendances();
    Attendance getAttendanceById(Long id);
    void markAttendance(Long studentId, boolean present);
    List<Attendance> getStudentAttendances(Long studentId);
}
