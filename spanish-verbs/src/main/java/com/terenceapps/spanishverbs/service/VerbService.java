package com.terenceapps.spanishverbs.service;

import com.terenceapps.spanishverbs.model.Verb;
import com.terenceapps.spanishverbs.model.VerbConjugated;
import com.terenceapps.spanishverbs.repository.VerbRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VerbService {

    private final VerbRepository verbRepository;

    public VerbService(VerbRepository verbRepository) {
        this.verbRepository = verbRepository;
    }

    public void save(Verb verb) {
        verbRepository.save(verb.getInfinitive(), verb.getMood(), verb.getTense());
    }

    public void unsave(Verb verb) {
        verbRepository.unsave(verb.getInfinitive(), verb.getMood(), verb.getTense());
    }

    public Optional<List<VerbConjugated>> get(String infinitive, String mood, String tense) {
        return verbRepository.findByCompositeKey(infinitive, mood, tense);
    }

    public Optional<List<Verb>> getSavedVerbs() {
        return verbRepository.findAll();
    }
}
