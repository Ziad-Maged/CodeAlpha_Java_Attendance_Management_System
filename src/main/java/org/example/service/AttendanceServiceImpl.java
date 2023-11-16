package org.example.service;

import org.example.model.Attendance;
import org.example.repository.AttendanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService{

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Override
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    @Override
    public Attendance getAttendanceById(Long id) {
        Optional<Attendance> optionalAttendance = attendanceRepository.findById(id);
        return optionalAttendance.orElse(null);
    }

    @Override
    public void markAttendance(Long studentId, boolean present) {
        // Assuming you have a method to get the current date
        Date currentDate = getCurrentDate();

        // Check if attendance record for the current date and student already exists
        List<Attendance> existingAttendance = attendanceRepository.findByStudentIdAndDate(studentId, currentDate);
        if (existingAttendance.isEmpty()) {
            Attendance newAttendance = new Attendance();
            newAttendance.setStudentId(studentId);
            newAttendance.setDate(currentDate);
            newAttendance.setPresent(present);
            attendanceRepository.save(newAttendance);
        } else {
            // Update the existing attendance record
            Attendance attendance = existingAttendance.get(0);
            attendance.setPresent(present);
            attendanceRepository.save(attendance);
        }
    }

    @Override
    public List<Attendance> getStudentAttendances(Long studentId) {
        return attendanceRepository.findByStudentId(studentId);
    }

    // Assuming you have a method to get the current date
    private Date getCurrentDate() {
        return new Date();
    }
}
