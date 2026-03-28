package com.furkanpatat.portfolio.service;

import com.furkanpatat.portfolio.entity.Skill;
import com.furkanpatat.portfolio.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;

    public List<Skill> getAllSkills() {
        return skillRepository.findAll();
    }

    public List<Skill> getActiveSkills() {
        return skillRepository.findAllByIsActiveTrueOrderByCategoryAscDisplayOrderAsc();
    }

    public Skill getSkillById(Long id) {
        return skillRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Yetenek bulunamadı! ID: " + id));
    }

    public Skill createSkill(Skill skill) {
        return skillRepository.save(skill);
    }

    public Skill updateSkill(Long id, Skill skillDetails) {
        Skill existingSkill = getSkillById(id);

        existingSkill.setName(skillDetails.getName());
        existingSkill.setCategory(skillDetails.getCategory());
        existingSkill.setDisplayOrder(skillDetails.getDisplayOrder());
        existingSkill.setActive(skillDetails.isActive());

        return skillRepository.save(existingSkill);
    }

    public void deleteSkill(Long id) {
        skillRepository.deleteById(id);
    }
}