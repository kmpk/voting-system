package com.github.kmpk.votingsystem.service;

import com.github.kmpk.votingsystem.exception.IllegalVoteChangeTime;
import com.github.kmpk.votingsystem.model.Vote;
import com.github.kmpk.votingsystem.repository.RestaurantRepository;
import com.github.kmpk.votingsystem.repository.UserRepository;
import com.github.kmpk.votingsystem.repository.VoteRepository;
import com.github.kmpk.votingsystem.to.VotesCountTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.kmpk.votingsystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteServiceImpl implements VoteService {
    private static final LocalTime VOTE_TIME_END = LocalTime.of(11, 0);

    private final VoteRepository repository;
    private final RestaurantRepository restaurantRepository;
    private final UserRepository userRepository;

    private Clock clock = Clock.systemDefaultZone();

    @Autowired
    public VoteServiceImpl(VoteRepository repository, RestaurantRepository restaurantRepository, UserRepository userRepository) {
        this.repository = repository;
        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Vote> getAllByDate(LocalDate localDate) {
        return repository.findAllByDateTimeBetweenOrderByUser_IdAscDateTimeDesc(
                LocalDateTime.of(localDate, LocalTime.MIN), LocalDateTime.of(localDate, LocalTime.MAX));
    }

    @Override
    @Transactional
    public Vote add(int restaurantId, int userId) {
        LocalDateTime dateTime = LocalDateTime.now(clock);
        Vote newVote = new Vote(null, userRepository.getOne(userId), restaurantRepository.get(restaurantId), dateTime);

        if (dateTime.toLocalTime().isAfter(VOTE_TIME_END) &&
                repository.existsByDateTimeBetweenAndUser_Id(LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MIN),
                        LocalDateTime.of(dateTime.toLocalDate(), LocalTime.MAX), userId)) {
            throw new IllegalVoteChangeTime("Can't change vote after " + VOTE_TIME_END);
        }
        return repository.save(newVote);
    }

    @Override
    public Vote get(int id) {
        return checkNotFoundWithId(repository.findById(id).orElse(null), id);
    }

    @Override
    public List<VotesCountTo> getResult(LocalDate localDate) {
        List<Vote> votesByDate = getAllByDate(localDate);
        return countVotes(votesByDate).entrySet()
                .stream()
                .map(e -> new VotesCountTo(restaurantRepository.get(e.getKey()), e.getValue()))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    private Map<Integer, Long> countVotes(List<Vote> votesByDate) {
        Map<Integer, Long> restaurantIdVoteCountMap = new HashMap<>();
        int currentUserId = -1;
        for (Vote v : votesByDate) {
            Integer userId = v.getUser().getId();
            if (userId != currentUserId) {
                restaurantIdVoteCountMap.merge(v.getRestaurant().getId(), 1L, Long::sum);
                currentUserId = userId;
            }
        }
        return restaurantIdVoteCountMap;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }
}
