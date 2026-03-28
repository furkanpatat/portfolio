package com.furkanpatat.portfolio.repository;

import com.furkanpatat.portfolio.entity.Experience;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    // Vitrinde deneyimleri sırasıyla ve sadece aktif olanları göstermek için
    List<Experience> findAllByIsActiveTrueOrderByDisplayOrderAsc();

}