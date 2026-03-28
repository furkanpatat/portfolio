package com.furkanpatat.portfolio.service;

import com.furkanpatat.portfolio.entity.Project;
import com.furkanpatat.portfolio.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    // Lombok'un @RequiredArgsConstructor anatosyonu sayesinde
    // constructor injection otomatik yapılıyor, kod tertemiz kalıyor.
    private final ProjectRepository projectRepository;

    // Admin paneli için tüm projeleri getirir
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    // Portföy vitrini (önyüz) için sadece aktif olanları belirlediğimiz sırada getirir
    public List<Project> getActiveProjects() {
        return projectRepository.findAllByIsActiveTrueOrderByDisplayOrderAsc();
    }

    // ID'ye göre tek bir proje getirir
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proje bulunamadı! ID: " + id));
    }

    // Yeni proje ekleme
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    // Mevcut projeyi güncelleme
    public Project updateProject(Long id, Project projectDetails) {
        Project existingProject = getProjectById(id);

        existingProject.setTitle(projectDetails.getTitle());
        existingProject.setDescription(projectDetails.getDescription());
        existingProject.setTechStack(projectDetails.getTechStack());
        existingProject.setGithubUrl(projectDetails.getGithubUrl());
        existingProject.setLiveUrl(projectDetails.getLiveUrl());
        existingProject.setImageUrl(projectDetails.getImageUrl());
        existingProject.setCategory(projectDetails.getCategory());
        existingProject.setDisplayOrder(projectDetails.getDisplayOrder());
        existingProject.setActive(projectDetails.isActive());

        return projectRepository.save(existingProject);
    }

    // Projeyi veritabanından tamamen silme
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }
}