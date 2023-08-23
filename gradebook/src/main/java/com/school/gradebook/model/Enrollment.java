package com.school.gradebook.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Enrollment")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "enrollDate")
    private String enrollDate;

    @Column(name = "currentGrade")
    private int currentGrade;

    @Column(name = "midtermGrade")
    private int midtermGrade;

    @Column(name = "finalGrade")
    private int finalGrade;

    @ManyToOne
    @JoinColumn(name = "division_id")
    private Division division;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
