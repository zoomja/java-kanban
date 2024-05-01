package test;

import interfaces.TaskManager;
import managers.InMemoryTaskManager;
import managers.TaskType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PrioritizedHandlerTest {

    private TaskManager manager;
    private HttpTaskServer taskServer;
    private HttpClient client;
    private final String baseUrl = "http://localhost:8080/prioritized/";

    @BeforeEach
    void setUp() throws IOException {
        manager = new InMemoryTaskManager();
        taskServer = new HttpTaskServer(manager);
        taskServer.start();
        client = HttpClient.newHttpClient();
    }

    @AfterEach
    void tearDown() {
        taskServer.stop();
    }

    @Test
    void testGetPrioritizedTasks() throws IOException, InterruptedException {
        manager.addNewTask(new Task("New Task", "Test description", TaskType.TASK, 60, LocalDateTime.now()));
        manager.addNewTask(new Task("New Task", "Test description", TaskType.TASK, 60, LocalDateTime.now().plusDays(1)));


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Не удалось получить приоритетные задачи");


    }
}
