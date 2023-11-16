package org.example.controller;

import org.example.model.Attendance;
import org.example.model.Student;
import org.example.service.AttendanceService;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/list")
    public String listAttendances(Model model) {
        List<Attendance> attendances = attendanceService.getAllAttendances();
        model.addAttribute("attendances", attendances);
        StringBuilder startingTags = new StringBuilder("<!DOCTYPE html><html><head><h2>Attendance List</h2></head><body><table border=\"1\"><tr><th>Attendance ID</th><th>Student Name</th><th>Date</th><th>Present</th></tr>");
        String endingTags = "</table></br><a href=\"http://localhost:8080/student/list\">Back To Student List</a></body></html>";
        for(Attendance e : attendances){
            try{
                Student s = studentService.getStudentById(e.getStudentId());
                String tag = "<tr>";
                tag += "<td>" + e.getId() + "</td>";
                tag += "<td>" + s.getName() + "</td>";
                tag += "<td>" + e.getDate() + "</td>";
                tag += "<td>" + e.isPresent() + "</td></tr>";
                startingTags.append(tag);
            }catch (Exception ignored) {
            }
        }
        return startingTags + endingTags;
    }

    @GetMapping("/mark/{studentId}")
    public String markAttendanceForm(@PathVariable Long studentId, Model model) {
        Student s = studentService.getStudentById(studentId);
        model.addAttribute("student", studentService.getStudentById(studentId));
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <title>Mark Attendance</title>" +
                "</head>" +
                "<body>" +
                "<h2>Mark Attendance for " + s.getName() + "</h2>" +
                "<form action=\"/mark\" method=\"post\">" +
                "    <input type=\"hidden\" name=\"studentId\" value=\"" + s.getId() + "\">" +
                "    <label>Present:</label>" +
                "    <input type=\"radio\" name=\"present\" value=\"true\" checked>" +
                "    <label>Not Present:</label>" +
                "    <input type=\"radio\" name=\"present\" value=\"false\">" +
                "    <br>" +
                "    <input type=\"submit\" value=\"Submit\">" +
                "</form>" +
                "</body>" +
                "</html>";
    }

    @PostMapping("/mark")
    public String markAttendance(@RequestParam Long studentId, @RequestParam boolean present) {
        System.out.println("Entered markAttendance Method");
        System.out.println(present);
        attendanceService.markAttendance(studentId, present);
        return "<script>window.onload = function(){window.location=\"http://localhost:8080/list\"}</script>";
    }

    @GetMapping("/student/{studentId}")
    public String studentAttendances(@PathVariable Long studentId, Model model) {
        List<Attendance> attendances = attendanceService.getStudentAttendances(studentId);
        model.addAttribute("attendances", attendances);
        model.addAttribute("student", studentService.getStudentById(studentId));
        Student s = studentService.getStudentById(studentId);
        StringBuilder startingTags = new StringBuilder("<!DOCTYPE html><html><head><title>Student Attendance</title></head><body><h2>Attendance for " + s.getName() + "</h2><table border=\"1\"><tr><th>Attendance ID</th><th>Date</th><th>Present</th></tr>");
        String endingTags = "</table></br><a href=\"http://localhost:8080/student/list\">Back To Student List</a></body></html>";
        for(Attendance e : attendances){
            String tags = "<tr>";
            tags += "<td>" + e.getId() + "</td>";
            tags += "<td>" + e.getDate() + "</td>";
            tags += "<td>" + e.isPresent() + "</td>";
            tags += "</tr>";
            startingTags.append(tags);
        }
        return startingTags + endingTags;
    }

}
