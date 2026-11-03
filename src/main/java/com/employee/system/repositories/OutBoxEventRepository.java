package com.employee.system.repositories;

import com.employee.system.entity.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutBoxEventRepository extends JpaRepository<OutboxEvent, Long> {
}
