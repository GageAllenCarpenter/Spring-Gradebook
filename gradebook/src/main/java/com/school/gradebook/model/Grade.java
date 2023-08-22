package com.school.gradebook.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name="points")
    private int points;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "division_id")
    private Division division;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "student_id")
    private Student student;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "professor_id")
    private Professor professor;
}
