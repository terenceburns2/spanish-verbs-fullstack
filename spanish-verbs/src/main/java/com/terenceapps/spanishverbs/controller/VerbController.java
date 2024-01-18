package com.terenceapps.spanishverbs.controller;

import com.terenceapps.spanishverbs.model.Verb;
import com.terenceapps.spanishverbs.model.VerbConjugated;
import com.terenceapps.spanishverbs.service.VerbService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class VerbController {

    private final VerbService verbService;

    public VerbController(VerbService verbService) {
        this.verbService = verbService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> save(@RequestBody Verb verbDto) {
        verbService.save(verbDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verb")
    public ResponseEntity<List<VerbConjugated>> getVerb(@RequestParam String infinitive, @RequestParam String mood, @RequestParam String tense) {
        Optional<List<VerbConjugated>> verb = verbService.get(infinitive, mood, tense);

        return verb.map(verbConjugated -> new ResponseEntity<>(verbConjugated, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(List.of(), HttpStatus.OK));

    }

    @GetMapping("/verbs")
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
