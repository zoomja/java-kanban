package test.http;

import com.google.gson.reflect.TypeToken;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HistoryHandlerTest {

    private TaskManager manager;
    private HttpTaskServer taskServer;
    private HttpClient client;
    private final String baseUrl = "http://localhost:8080/history/";

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
    void testGetHistory() throws IOException, InterruptedException {
        Task task = new Task("New Task", "Test description", TaskType.TASK, 60, LocalDateTime.now());
        manager.addNewTask(task);
        manager.getTaskById(task.getId());

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Не удалось получить историю");

        List<Task> history = taskServer.getGson().fromJson(response.body(), new TypeToken<List<Task>>() {
        }.getType());
        assertEquals(1, history.size(), "История должна содержать одну задачу");
    }
}
