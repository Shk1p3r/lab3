package com.lab3.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lab3.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Project findById(int id);
    List<Project> findAll();
    List<Project> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);
    @Query("""
        select p.id as id, count(t.id) as count
        from Project p
        left join p.tasks t
        where t.flag = false or t.id is null
        group by p.id
    """)
    List<Map<String, Number>> countProjectWithTasksFlagFalse();
}
