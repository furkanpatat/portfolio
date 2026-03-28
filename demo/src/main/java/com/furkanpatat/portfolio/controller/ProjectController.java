package com.furkanpatat.portfolio.controller;

import com.furkanpatat.portfolio.entity.Project;
import com.furkanpatat.portfolio.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // İleride Next.js frontend'inden rahatça istek atabilmek için CORS'u şimdilik herkese açıyoruz.
public class ProjectController {

    private final ProjectService projectService;

    // Admin paneli için tüm projeleri listeler
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    // Vitrin (Portföy ana sayfası) için sadece aktif projeleri listeler
    @GetMapping("/active")
    public ResponseEntity<List<Project>> getActiveProjects() {
        return ResponseEntity.ok(projectService.getActiveProjects());
    }

    // ID'ye göre tek bir projeyi getirir (Detay sayfası için)
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    // Yeni proje ekler (Örn: Flo Overstock veya İzleCine)
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        return new ResponseEntity<>(projectService.createProject(project), HttpStatus.CREATED);
    }

    // Mevcut bir projeyi günceller
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project projectDetails) {
        return ResponseEntity.ok(projectService.updateProject(id, projectDetails));
    }

    // Bir projeyi siler
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}