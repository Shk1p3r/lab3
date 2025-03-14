package com.lab3.entity;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "task", schema = "public")
@Entity
public class Task {
    @Id
    @SequenceGenerator(name = "task_seq", sequenceName = "task_id_task_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="task_seq")
    @Column(name = "id_task") 
    private int id;
    @Column(name = "name_task") 
    private String name;
    @Column(name = "description_task") 
    private String description;
    @Temporal(value = TemporalType.DATE)
    @Column(name = "end_date_task") 
    private Date endDate;
    @Column(name = "flag_finish") 
    private boolean flag;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_project", nullable = false)
    private Project project;
}
