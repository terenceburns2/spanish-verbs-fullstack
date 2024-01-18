package com.terenceapps.spanishverbs.controller;

import com.terenceapps.spanishverbs.model.Verb;
import com.terenceapps.spanishverbs.model.VerbConjugated;
import com.terenceapps.spanishverbs.service.VerbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
public class VerbController {

    private final VerbService verbService;

    private Logger logger = Logger.getLogger(VerbController.class.getName());

    public VerbController(VerbService verbService) {
        this.verbService = verbService;
    }

    // Ideally, we would limit the response and add some form of paging.
    @GetMapping("/conjugated-verbs")
    public ResponseEntity<List<VerbConjugated>> getConjugatedVerbs() {
        Optional<List<VerbConjugated>> verbs = verbService.getNonSavedConjugatedVerbs();

        return verbs.map(conjugatedVerbs -> new ResponseEntity<>(conjugatedVerbs, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Verb verbDto) {
        verbService.save(verbDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/conjugated-verb")
    public ResponseEntity<VerbConjugated> getConjugatedVerb(@RequestParam String infinitive, @RequestParam String mood, @RequestParam String tense) {
        Optional<VerbConjugated> verb = verbService.getConjugatedVerb(infinitive, mood, tense);

        return verb.map(verbConjugated -> new ResponseEntity<>(verbConjugated, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));

    }

    @GetMapping("/saved-verbs")
    public ResponseEntity<List<Verb>> getVerbs() {
        Optional<List<Verb>> verbs = verbService.getSavedVerbs();

        return verbs.map(verbList -> new ResponseEntity<>(verbList, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(List.of(), HttpStatus.OK));

    }

    @DeleteMapping("/unsave")
    public ResponseEntity<String> unsave(@RequestBody Verb verbDto) {
        verbService.unsave(verbDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
