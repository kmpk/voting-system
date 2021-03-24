package com.github.kmpk.votingsystem.repository;

import com.github.kmpk.votingsystem.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
public interface VoteRepository extends JpaRepository<Vote, Integer> {

    List<Vote> findAllByDateTimeBetweenOrderByUser_IdAscDateTimeDesc(LocalDateTime start, LocalDateTime end);

    boolean existsByDateTimeBetweenAndUser_Id(LocalDateTime start, LocalDateTime end, int userId);
}
