import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.URIBuilder;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Sender {
    /* Количество создаваемых счётчиков */
    private static final int COUNTERS_AMNT = 100;
    /* Количество заданных инкрементов для счётчика */
    private static final int INCREMENTS_AMNT = 10000;

    public static void main(String[] args) throws InterruptedException {

        Sender sender = new Sender();

        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 0; i < COUNTERS_AMNT; i++) {
            executorService.submit(() -> {
                sender.createCounterByName("counter_abc");
            });
            executorService.submit(() -> {
                sender.createCounterByName("counter_def");
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);

        executorService = Executors.newCachedThreadPool();

        sender.getCountersList();

        for (int i = 0; i < INCREMENTS_AMNT; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep((long) (1000*Math.random()));
                } catch (InterruptedException e) {}
                sender.incrementNamedCounter("counter_abc");
            });
            executorService.submit(() -> {
                try {
                    Thread.sleep((long) (1000*Math.random()));
                } catch (InterruptedException e) {}
                sender.incrementNamedCounter("counter_def");
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(15, TimeUnit.MINUTES);

        sender.getCounterValue("counter_abc");
        sender.getCounterValue("counter_def");


        sender.deleteCounter("counter_abc");
        sender.deleteCounter("counter_def");

        sender.getCountersList();
    }

    private void getCountersList() {
        log.debug("Отправка запроса на список счётчиков.");
        try {
            URI uri = new URIBuilder("http://localhost:8080/api/list").build();
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
//            log.debug("При отправке list получен код {}", responseCode);
            InputStream stream = connection.getInputStream();
            log.debug("Ответ на list: " + IOUtils.toString(stream, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Ошибка при получении списка счётчиков: {}", e.getMessage());
        }
    }

    private void getCountersSum() {
//        log.debug("Отправка запроса на сумму всех счётчиков.");
        try {
            URI uri = new URIBuilder("http://localhost:8080/api/sum").build();
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
//            log.debug("При отправке sum получен код {}", responseCode);
            InputStream stream = connection.getInputStream();
            log.debug("Ответ на sum: " + IOUtils.toString(stream, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Ошибка при получении суммы всех счётчиков: {}", e.getMessage());
        }
    }

    private void deleteCounter(String counterName) {
        log.debug("Отправка запроса на удаление счётчика {}.", counterName);
        try {
            URI uri = new URIBuilder("http://localhost:8080/api/delete").addParameter("name", counterName).build();
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("DELETE");
            int responseCode = connection.getResponseCode();
//            log.debug("При отправке delete получен код {}", responseCode);
            InputStream stream = connection.getInputStream();
            log.debug("Ответ на delete: " + IOUtils.toString(stream, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Ошибка при удалении счётчика: {}", e.getMessage());
        }
    }

    private void createCounterByName(String counterName) {
        log.debug("Отправка запроса на создание счётчика {}.", counterName);
        try {
            URI uri = new URIBuilder("http://localhost:8080/api/create").addParameter("name", counterName).build();
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("PUT");
            int responseCode = connection.getResponseCode();
//            log.debug("При отправке create получен код {}", responseCode);
            InputStream stream = connection.getInputStream();
            log.debug("Ответ на create: " + IOUtils.toString(stream, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Ошибка при создании счётчика {}: {}", counterName, e.getMessage());
        }
    }

    private void incrementNamedCounter(String counterName) {
//        log.debug("Отправка запроса на инкремент счётчика {}", counterName);
        try {
            URI uri = new URIBuilder("http://localhost:8080/api/increment").addParameter("name", counterName).build();
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("POST");
            int responseCode = connection.getResponseCode();
//            log.debug("При отправке increment получен код {}", responseCode);
            InputStream stream = connection.getInputStream();
            log.debug("Ответ на increment: " + IOUtils.toString(stream, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Ошибка при инкрементировании счётчика {}: {}", counterName, e.getMessage());
            log.debug("");
        }
    }

    private void getCounterValue(String counterName) {
//        log.debug("Отправка запроса на получение значения счётчика {}", counterName);
        try {
            URI uri = new URIBuilder("http://localhost:8080/api/value").addParameter("name", counterName).build();
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
//            log.debug("При отправке value получен код {}", responseCode);
            InputStream stream = connection.getInputStream();
            log.debug("Ответ на value: " + IOUtils.toString(stream, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Ошибка при получении значения счётчика {}: {}", counterName, e.getMessage());
        }
    }
}
