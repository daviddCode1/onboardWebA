package com.OnbaordWeb.Repositories;


import com.OnbaordWeb.Models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    //guery for get question by no
    @Query("SELECT qus FROM Question AS qus WHERE questionNo = ?1")
    Question getQuestionByNo(Integer qusNo);
}
