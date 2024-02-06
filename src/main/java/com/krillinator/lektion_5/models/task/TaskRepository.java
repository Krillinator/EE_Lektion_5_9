package com.krillinator.lektion_5.models.task;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<TaskEntity ,Long> {
}
