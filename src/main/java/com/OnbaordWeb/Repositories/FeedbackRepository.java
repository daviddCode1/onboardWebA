package com.OnbaordWeb.Repositories;

import com.OnbaordWeb.Models.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    //guery for get geedbacks by user
    @Query("SELECT feedback FROM Feedback as feedback WHERE userId = :userId")
    List<Feedback> getFeedbacksByUserId(Long userId);
}
