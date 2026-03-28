package com.furkanpatat.portfolio.service;

import com.furkanpatat.portfolio.entity.Experience;
import com.furkanpatat.portfolio.repository.ExperienceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExperienceService {

    private final ExperienceRepository experienceRepository;

    public List<Experience> getAllExperiences() {
        return experienceRepository.findAll();
    }

    public List<Experience> getActiveExperiences() {
        return experienceRepository.findAllByIsActiveTrueOrderByDisplayOrderAsc();
    }

    public Experience getExperienceById(Long id) {
        return experienceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Deneyim bulunamadı! ID: " + id));
    }

    public Experience createExperience(Experience experience) {
        return experienceRepository.save(experience);
    }

    public Experience updateExperience(Long id, Experience experienceDetails) {
        Experience existingExperience = getExperienceById(id);

        existingExperience.setCompanyName(experienceDetails.getCompanyName());
        existingExperience.setTitle(experienceDetails.getTitle());
        existingExperience.setStartDate(experienceDetails.getStartDate());
        existingExperience.setEndDate(experienceDetails.getEndDate());
        existingExperience.setDescription(experienceDetails.getDescription());
        existingExperience.setDisplayOrder(experienceDetails.getDisplayOrder());
        existingExperience.setActive(experienceDetails.isActive());

        return experienceRepository.save(existingExperience);
    }

    public void deleteExperience(Long id) {
        experienceRepository.deleteById(id);
    }
}