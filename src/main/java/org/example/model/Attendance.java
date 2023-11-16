package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;

@Entity
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long studentId;
    private Date date;
    private boolean present;

    public Attendance(Long id, Long studentId, Date date, boolean present){
        this.id = id;
        this.studentId = studentId;
        this.date = date;
        this.present = present;
    }
    public Attendance(Long id, Long studentId, Date date){
        this(id, studentId, date, false);
    }
    public Attendance(){}

    public Long getId() {
        return id;
    }

    public Long getStudentId() {
        return studentId;
    }

    public Date getDate() {
        return date;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
}
