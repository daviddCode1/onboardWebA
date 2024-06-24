package com.OnbaordWeb.Repositories;


import com.OnbaordWeb.Models.AnswerOption;
import com.OnbaordWeb.Models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {

    //guery for get answer options by no and ques
    @Query("SELECT ansOp FROM AnswerOption AS ansOp WHERE optionNo = :optionNo AND question = :ques")
    AnswerOption getAnswerOptionByOptionNoAndQues(String optionNo, Question ques);
}
