import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.utils.URIBuilder;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Sender {
    public static void main(String[] args) {

        Sender sender = new Sender();
        sender.createCounterByName("abc");
//        sender.createCounterByName("def");

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    sender.incrementNamedCounter("abc");
//                    sender.incrementNamedCounter("abc");
//                    sender.incrementNamedCounter("abc");
//                    sender.incrementNamedCounter("abc");
//                    sender.incrementNamedCounter("abc");
                }
            });


        }
        sender.getCounterValue("abc");
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
            log.error("Ошибка при обращении к методу create", e);
        }
    }

    private void incrementNamedCounter(String counterName) {
        log.debug("Отправка запроса на инкремент счётчика {}", counterName);
        try {
            URI uri = new URIBuilder("http://localhost:8080/api/increment").addParameter("name", counterName).build();
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("POST");
            int responseCode = connection.getResponseCode();
//            log.debug("При отправке increment получен код {}", responseCode);
            InputStream stream = connection.getInputStream();
            log.debug("Ответ на increment: " + IOUtils.toString(stream, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Ошибка при инкрементировании счётчика {}.", counterName, e);
        }
    }

    private void getCounterValue(String counterName) {
        log.debug("Отправка запроса на получение значения счётчика {}", counterName);
        try {
            URI uri = new URIBuilder("http://localhost:8080/api/value").addParameter("name", counterName).build();
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
//            log.debug("При отправке value получен код {}", responseCode);
            InputStream stream = connection.getInputStream();
            log.debug("Ответ на value: " + IOUtils.toString(stream, StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("Ошибка при инкрементировании счётчика {}.", counterName, e);
        }
    }
}
