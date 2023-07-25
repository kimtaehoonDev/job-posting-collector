package org.kimtaehoondev.jobpostingcollector.email.repository;

import java.util.List;
import org.kimtaehoondev.jobpostingcollector.email.dto.response.EmailResponseDto;
import org.kimtaehoondev.jobpostingcollector.email.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
    List<EmailResponseDto> findAllBy();
}
