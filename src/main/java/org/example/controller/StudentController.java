package org.example.controller;

import org.example.model.Student;
import org.example.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/list")
    public String listStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        System.out.println(model);
        StringBuilder startingTags = new StringBuilder("<!DOCTYPE html><html><head><title>Student List</title></head><body><h2>Student List</h2><table border=\"1\"><tr><th>Student ID</th><th>Name</th></tr>");
        String endingTags = "</table></br><a href=\"http://localhost:8080/student/add\">Add a Student</a></body></html>";
        for(Student e : students){
            String tag = "<tr>";
            tag += "<td>" + e.getId();
            tag += "<td>" + e.getName();
            tag += "<td><a href=\"http://localhost:8080/mark/" + e.getId() + "\">Mark</a>";
            tag += "<td><a href=\"http://localhost:8080/student/delete/" + e.getId() + "\">Delete Student</a>";
            tag += "<td><a href=\"http://localhost:8080/student/" + e.getId() + "\">View Student Attendances</a>";
            startingTags.append(tag);
        }
        return startingTags + endingTags;
    }

    @GetMapping("/add")
    public String addStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head>" +
                "    <title>Add Student</title>" +
                "</head>" +
                "<body>" +
                "<h2>Add Student</h2>" +
                "<form action=\"/student/add\" method=\"post\">" +
                "    <label>Name:</label>" +
                "    <input type=\"text\" name=\"name\" required>" +
                "    <br>" +
                "    <input type=\"submit\" value=\"Submit\">" +
                "</form>" +
                "</body>" +
                "</html>";
    }

    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student) {
        studentService.saveStudent(student);
        return "<script>window.onload = function(){window.location=\"http://localhost:8080/student/list\"}</script>";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "<script>window.onload = function(){window.location=\"http://localhost:8080/student/list\"}</script>";
    }
}
