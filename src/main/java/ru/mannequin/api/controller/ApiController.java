package ru.mannequin.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.mannequin.api.controller.message.ApiMessage;
import ru.mannequin.api.controller.message.ApiValue;

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
        log.info("Запрос на создание нового счётчика: {}", counterName);

        if (counters.containsKey(counterName)) {
            log.info("Счётчик с именем \"{}\" уже существует. Создание нового не производится.");
            ApiMessage message = new ApiMessage(false, "Счётчик " + counterName + " уже существует.");
            return ResponseEntity.ok().body(message);
        }

        /* Добавляем новый счётчик */
        counters.put(counterName, new AtomicInteger(0));
        ApiMessage message = new ApiMessage(true, "Счётчик " + counterName + " добавлен.");
        return ResponseEntity.ok().body(message);
    }

    /**
     * Инкремент значения счетчика с указанным именем
     * @param counterName
     * @return
     */
    @PostMapping("/increment")
    public ResponseEntity<Object> incrementCounter(@RequestParam(name="name") String counterName) {
        log.info("Запрос на инкремент счётчика {}.", counterName);

        if (!counters.containsKey(counterName)) {
            return ResponseEntity.ok(new ApiMessage(false, "Счётчик с именем \"" + counterName + "\" не найден."));
        }

        counters.get(counterName).incrementAndGet();

        return ResponseEntity.ok(new ApiMessage(true, "Счётчик " + counterName + " инкрементирован."));
    }

    /* Получить значения счетчика с указанным именем; */
    @GetMapping("/value")
    public ResponseEntity<Object> getCounterValue(@RequestParam(name="name") String counterName) {
        log.info("Запрос на получение значения счётчика {}", counterName);

        if (!counters.containsKey(counterName)) {
            return ResponseEntity.ok(new ApiMessage(false, "Счётчик с именем \"" + counterName + "\" не найден."));
        }

        return  ResponseEntity.ok(new ApiValue(true, counters.get(counterName).intValue()));
    }

    /* TODO Удалить счетчик с указанным именем; */

    /* TODO Получить суммарное значение всех счетчиков; */

    /* TODO Получить уникальные имена счетчиков в виде списка. */
}
