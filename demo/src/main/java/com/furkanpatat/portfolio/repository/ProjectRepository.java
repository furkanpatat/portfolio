package com.furkanpatat.portfolio.repository;

import com.furkanpatat.portfolio.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // Spring Data JPA bizim için isimlendirmeden otomatik SQL sorgusu üretecek:
    // Sitede sadece aktif olan projeleri listelemek için kullanacağız.
    List<Project> findAllByIsActiveTrueOrderByDisplayOrderAsc();

}