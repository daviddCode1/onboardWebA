package com.OnbaordWeb.Repositories;

import com.OnbaordWeb.Models.DocumentReading;
import com.OnbaordWeb.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentReadingRepository extends JpaRepository<DocumentReading, Long> {

    List<DocumentReading> findByUser(User user);

    void deleteByUser(User user);
}
