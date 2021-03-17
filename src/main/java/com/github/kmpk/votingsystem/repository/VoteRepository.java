package com.github.kmpk.votingsystem.repository;

import com.github.kmpk.votingsystem.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface VoteRepository extends JpaRepository<Vote,Integer> {
}
