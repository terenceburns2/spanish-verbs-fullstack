package com.terenceapps.spanishverbs.repository;

import com.terenceapps.spanishverbs.model.Verb;
import com.terenceapps.spanishverbs.model.VerbConjugated;

import java.util.List;
import java.util.Optional;

public interface VerbRepository {
    void save(String infinitive, String mood, String tense);
    void unsave(String infinitive, String mood, String tense);
    Optional<List<Verb>> findAll();
    Optional<List<VerbConjugated>> findByCompositeKey(String infinitive, String mood, String tense);
}
