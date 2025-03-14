package com.lab3.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Table(name="project", schema="public")
@Entity
public class Project {
    @Id
    @SequenceGenerator(name = "project_seq", sequenceName = "project_id_project_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="project_seq")
    @Column(name = "id_project") 
    private int id;
    @Column(name = "name_project") 
    private String name;
    @Column(name = "description_project") 
    private String description;
    @Temporal(value = TemporalType.DATE)
    @Column(name = "start_date_project") 
    private Date startDate;
    @Temporal(value = TemporalType.DATE)
    @Column(name = "end_date_project") 
    private Date endDate;
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();
}
