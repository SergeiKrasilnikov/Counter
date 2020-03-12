package ru.mannequin.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api")
public class ApiController {

    /* Ъранилище счётчиков */
    private Map<String, Integer> counters = new HashMap<>();

    @PutMapping("/create")
    public ResponseEntity<Map<String, String>> createCounter(@RequestParam(name = "name") String counterName) {
        log.info("Получен запрос на создание нового счётчика: {}", counterName);

        Map<String, String> ret = new HashMap<>();
        ret.put("success", "true");
        return ResponseEntity.ok().body(ret);
    }
}
