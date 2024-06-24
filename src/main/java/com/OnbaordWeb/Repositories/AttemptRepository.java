package com.OnbaordWeb.Repositories;

import com.OnbaordWeb.Models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Long> {

    //guery for get all attempts by userId
    @Query("SELECT attempt FROM Attempt AS attempt WHERE user = :us AND quiz = :qus")
    List<Attempt> getAllAttemptsByUserId(User us, Quiz qus);

    //guery for get all attempts by quiz
    @Query("SELECT attempt FROM Attempt AS attempt WHERE quiz = :qus")
    List<Attempt> getAllAttemptsByQuiz(Quiz qus);

    //guery for get first 5 by score DESC
    List<Attempt> findFirst5ByOrderByScoreDesc();
}
///