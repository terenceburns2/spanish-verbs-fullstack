package com.terenceapps.spanishverbs.repository;

import com.terenceapps.spanishverbs.model.Verb;
import com.terenceapps.spanishverbs.model.VerbConjugated;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Repository
public class JdbcVerbRepository implements VerbRepository {

    private final JdbcTemplate jdbcTemplate;
    private final Logger logger = Logger.getLogger(JdbcVerbRepository.class.getName());


    public JdbcVerbRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(String infinitive, String mood, String tense) {
        String sql = "INSERT INTO saved VALUES (?, ?, ?)";

        try {
            jdbcTemplate.update(sql, infinitive, mood, tense);
        } catch (Exception e) {
            logger.info("Failed to save verb: " + e);
        }
    }


    @Override
    public void unsave(String infinitive, String mood, String tense) {
        String sql = "DELETE FROM saved WHERE infinitive=? AND mood=? AND tense=?";
        try {
            jdbcTemplate.update(sql, infinitive, mood, tense);
        } catch (Exception e) {
            logger.info("Failed to delete verb: " + e);
        }

    }


    @Override
    public Optional<List<Verb>> findAll() {
        String sql = "SELECT * FROM saved";

        List<Verb> resultSet = jdbcTemplate.query(sql,
                (row, index) -> new Verb(row.getString("infinitive"), row.getString("mood"), row.getString("tense")));
        return Optional.of(resultSet);
    }


    @Override
    public Optional<List<VerbConjugated>> findByCompositeKey(String infinitive, String mood, String tense) {
        String sql = "SELECT * FROM verbs WHERE infinitive=? AND mood=? AND tense=?";

        RowMapper<VerbConjugated> verbConjugatedRowMapper = (row, index) -> {
            VerbConjugated verb = new VerbConjugated();
            verb.setInfinitive(row.getString("infinitive"));
            verb.setMood(row.getString("mood"));
            verb.setTense(row.getString("tense"));
            verb.setVerbEnglish(row.getString("verb_english"));
            verb.setForm1s(row.getString("form_1s"));
            verb.setForm2s(row.getString("form_2s"));
            verb.setForm3s(row.getString("form_3s"));
            verb.setForm1p(row.getString("form_1p"));
            verb.setForm2p(row.getString("form_2p"));
            verb.setForm3p(row.getString("form_3p"));
            return verb;
        };

        List<VerbConjugated> verbConjugated = jdbcTemplate.query(sql, verbConjugatedRowMapper, infinitive, mood, tense);

        return Optional.of(verbConjugated);

    }
}
