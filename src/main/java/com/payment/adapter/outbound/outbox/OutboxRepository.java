package com.payment.adapter.outbound.outbox;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<OutboxEntity, Long> {

    List<OutboxEntity> findByStatus(OutboxStatus status);
}
