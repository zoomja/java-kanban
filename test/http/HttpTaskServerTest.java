package http;

import interfaces.TaskManager;
import managers.InMemoryTaskManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpTaskServerTest {
    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    private static final String BASE_URL = "http://localhost:8080/tasks/";


    public HttpTaskServerTest() throws IOException {
    }

    @BeforeEach
    void setUp() {
        taskServer.start();
    }

    @AfterEach
    void tearDown() {
        taskServer.stop();
    }

    @Test
    public void serverShouldStart() throws IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newBuilder().build();
        URI uri = URI.create(BASE_URL);
        HttpRequest httpRequest = HttpRequest.newBuilder(uri).header("Accept", "application/json").GET().build();
        HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Сервер не ответил ожидаемым статусным кодом.");
    }
}