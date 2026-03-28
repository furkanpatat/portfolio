package com.furkanpatat.portfolio.repository;

import com.furkanpatat.portfolio.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    // Yetenekleri sadece aktif olanlar arasından, önce kategoriye sonra sıraya göre getirir
    List<Skill> findAllByIsActiveTrueOrderByCategoryAscDisplayOrderAsc();

}