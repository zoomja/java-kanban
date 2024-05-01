package test;

import com.google.gson.Gson;
import interfaces.TaskManager;
import managers.InMemoryTaskManager;
import org.junit.jupiter.api.BeforeAll;
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
    private HttpClient client;
    private final String baseUrl = "http://localhost:8080/tasks/";


    public HttpTaskServerTest() throws IOException {
    }

    @BeforeEach
    void setUp() throws IOException {
        taskServer.start();
        client = HttpClient.newHttpClient();
    }

    @Test
    public void serverShouldStart() {
        HttpClient httpClient = HttpClient.newBuilder().build();
        URI uri = URI.create("http://localhost:8080/tasks");
        HttpRequest httpRequest = HttpRequest.newBuilder(uri).header("Accept", "application/json").GET().build();
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode(), "Сервер не ответил ожидаемым статусным кодом.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        } finally {
            HttpTaskServer.stop();
        }
    }
}