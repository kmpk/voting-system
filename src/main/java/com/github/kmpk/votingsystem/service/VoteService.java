package com.github.kmpk.votingsystem.service;

import com.github.kmpk.votingsystem.model.Vote;
import com.github.kmpk.votingsystem.to.VotesCountTo;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

public interface VoteService {

    List<Vote> getAllByDate(LocalDate localDate);

    Vote add(int restaurantId, int userId);

    Vote get(int id);

    List<VotesCountTo> getResult(LocalDate localDate);

    void setClock(Clock clock);
}
