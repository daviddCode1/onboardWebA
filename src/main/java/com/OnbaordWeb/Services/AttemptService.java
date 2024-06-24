package com.OnbaordWeb.Services;



import com.OnbaordWeb.Models.Attempt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AttemptService {

    Attempt saveAttempt(Attempt attempt);

    List<Attempt> getAllAttempts();

    Optional<Attempt> findById(Long id);
}
