package org.kimtaehoondev.jpcollector.email.repository;

import java.util.List;
import java.util.Optional;
import org.kimtaehoondev.jpcollector.email.dto.response.EmailResponseDto;
import org.kimtaehoondev.jpcollector.email.Email;
import org.kimtaehoondev.jpcollector.email.dto.response.EmailWithPwdDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<Email, Long> {
    List<EmailResponseDto> findAllBy();

    Optional<EmailWithPwdDto> findByEmail(String email);

    boolean existsByEmail(String email);
}
