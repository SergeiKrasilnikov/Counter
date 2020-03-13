package ru.mannequin.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mannequin.api.controller.message.ApiMessage;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
@RequestMapping("/api")
public class ApiController {

    /* Хранилище счётчиков */
    private Map<String, AtomicInteger> counters = new HashMap<>();

    /**
     * Создать счетчик с уникальным именем;
     * @param counterName Имя нового счётчика
     * @return
     */
    @PutMapping("/create")
    public ResponseEntity<Object> createCounter(@RequestParam(name = "name") String counterName) {
        log.info("Получен запрос на создание нового счётчика: {}", counterName);

        if (counters.keySet().contains(counterName)) {
            log.info("Счётчик с именем \"{}\" уже существует. Создание нового не производится.");
            ApiMessage message = new ApiMessage(false, "Счётчик " + counterName + " уже существует.");
            return ResponseEntity.ok().body(message);
        }

        /* Добавляем новый счётчик */
        counters.put(counterName, new AtomicInteger(0));
        ApiMessage message = new ApiMessage(true, "Счётчик " + counterName + " добавлен.");
        return ResponseEntity.ok().body(message);
    }

    /* TODO Инкремент значения счетчика с указанным именем; */
    @PostMapping("/increment")
    public ResponseEntity<Object> incrementCounter(@RequestParam(name="name") String counterName) {
        log.info("Получен запрос на инкремент счётчика {}.", counterName);

        counters.get(counterName).incrementAndGet();

        ApiMessage message = new ApiMessage(true, "Счётчик " + counterName + " инкрементирован.");
        return ResponseEntity.ok(message);
    }

    /* TODO Получить значения счетчика с указанным именем; */

    /* TODO Удалить счетчик с указанным именем; */

    /* TODO Получить суммарное значение всех счетчиков; */

    /* TODO Получить уникальные имена счетчиков в виде списка. */
}
