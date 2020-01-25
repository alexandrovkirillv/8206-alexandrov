package ru.focusstart.tomsk.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "isbn")
    private String isbn;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
}
