package org.kimtaehoondev.jobpostingcollector.email.repository;

import java.util.List;
import java.util.Optional;
import org.kimtaehoondev.jobpostingcollector.email.dto.response.EmailResponseDto;
import org.kimtaehoondev.jobpostingcollector.email.Email;
import org.kimtaehoondev.jobpostingcollector.email.dto.response.EmailWithPwdDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
    List<EmailResponseDto> findAllBy();

    Optional<EmailWithPwdDto> findByEmail(String email);

    boolean existsByEmail(String email);
}
