package com.OnbaordWeb.Repositories;


import com.OnbaordWeb.Models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    //get quiz by quizNo
    @Query("SELECT DISTINCT quiz FROM Quiz as quiz WHERE quizNo = ?1")
    Quiz getQuizByQuizNo(Integer quizNo);
}
