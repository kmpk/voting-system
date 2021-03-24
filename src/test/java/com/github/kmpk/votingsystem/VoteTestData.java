package com.github.kmpk.votingsystem;

import com.github.kmpk.votingsystem.model.Vote;
import com.github.kmpk.votingsystem.to.VotesCountTo;
import com.github.kmpk.votingsystem.web.TestUtil;
import org.springframework.test.web.servlet.ResultMatcher;

import java.time.LocalDateTime;
import java.util.List;

import static com.github.kmpk.votingsystem.RestaurantTestData.*;
import static com.github.kmpk.votingsystem.UserTestData.ADMIN;
import static com.github.kmpk.votingsystem.UserTestData.USER;
import static com.github.kmpk.votingsystem.model.AbstractBaseEntity.START_SEQ;
import static com.github.kmpk.votingsystem.web.JsonUtil.readListFromJsonMvcResult;
import static org.assertj.core.api.Assertions.assertThat;

public class VoteTestData {
    public static final int VOTE_1_ID = START_SEQ + 17;
    public static final int VOTE_2_ID = START_SEQ + 18;
    public static final int VOTE_3_ID = START_SEQ + 19;
    public static final int VOTE_4_ID = START_SEQ + 20;

    public static final Vote VOTE_1 = new Vote(VOTE_1_ID, USER, REST_1, LocalDateTime.of(2000, 1, 1, 8, 0, 0));
    public static final Vote VOTE_2 = new Vote(VOTE_2_ID, USER, REST_2, LocalDateTime.of(2000, 1, 1, 10, 0, 0));
    public static final Vote VOTE_3 = new Vote(VOTE_3_ID, USER, REST_3, LocalDateTime.of(2000, 1, 1, 10, 30, 0));
    public static final Vote VOTE_4 = new Vote(VOTE_4_ID, ADMIN, REST_1, LocalDateTime.of(2000, 1, 1, 9, 0, 0));

    public static final VotesCountTo VOTES_COUNT_1 = new VotesCountTo(REST_1,1L);
    public static final VotesCountTo VOTES_COUNT_2 = new VotesCountTo(REST_3,1L);


    public static void assertMatch(Vote actual, Vote expected) {
        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringFields("restaurant", "user","registered")
                .isEqualTo(expected);
    }

    public static void assertCountsMatch(Iterable<VotesCountTo> actual, Iterable<VotesCountTo> expected) {
        assertThat(actual)
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Vote> actual, Vote... expected) {
        assertMatch(actual, List.of(expected));
    }

    public static void assertMatch(Iterable<Vote> actual, Iterable<Vote> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("restaurant", "user","registered").isEqualTo(expected);
    }

    public static ResultMatcher getVoteMatcher(Vote expected) {
        return result -> assertMatch(TestUtil.readFromJsonMvcResult(result, Vote.class), expected);
    }

    public static ResultMatcher getVoteMatcher(Vote... expected) {
        return result -> assertMatch(readListFromJsonMvcResult(result, Vote.class), List.of(expected));
    }
}
