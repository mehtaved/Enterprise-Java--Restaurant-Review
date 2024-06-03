package ca.sheridancollege.mehtaved.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.mehtaved.beans.Poll;

import java.util.List;

@Repository
public class DatabaseAccess {

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    public void insertPollResponse(Long pollId, int selectedOption) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pollId", pollId);
        namedParameters.addValue("selectedOption", selectedOption);

        String query = "INSERT INTO poll_response (poll_id, selected_option) VALUES (:pollId, :selectedOption)";
        int rowsAffected = jdbc.update(query, namedParameters);

        if (rowsAffected > 0) {
            System.out.println("Poll response inserted into the database.");
        }
    }

    public Poll getPollWithResults(Long pollId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("pollId", pollId);

        String query = "SELECT * FROM poll WHERE id = :pollId";
        Poll poll = jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<>(Poll.class));

        if (poll != null) {
            String voteQuery = "SELECT selected_option, COUNT(*) as count FROM poll_response WHERE poll_id = :pollId GROUP BY selected_option";
            List<VoteResult> voteResults = jdbc.query(voteQuery, namedParameters, new BeanPropertyRowMapper<>(VoteResult.class));

            double totalVotes = voteResults.stream().mapToDouble(VoteResult::getCount).sum();

            for (VoteResult result : voteResults) {
                switch (result.getSelectedOption()) {
                    case 1:
                        poll.setVotes1(result.getCount());
                        break;
                    case 2:
                        poll.setVotes2(result.getCount());
                        break;
                    case 3:
                        poll.setVotes3(result.getCount());
                        break;
                }
            }

            poll.calculatePercentages();
        }

        return poll;
    }

    public List<Poll> getAllPolls() {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM poll";
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Poll.class));
    }

    private static class VoteResult {
        private int selectedOption;
        private int count;

        public int getSelectedOption() {
            return selectedOption;
        }

        public void setSelectedOption(int selectedOption) {
            this.selectedOption = selectedOption;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
