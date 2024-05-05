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
import java.util.ArrayList;
import java.util.List;

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

        Task task = new Task("New Task", "Test description", TaskType.TASK, 60, LocalDateTime.now());
        Task task2 = new Task("New Task", "Test description", TaskType.TASK, 60, LocalDateTime.now().plusDays(1));
        Task task3 = new Task("New Task", "Test description", TaskType.TASK, 60, LocalDateTime.now().plusDays(3));

        manager.addNewTask(task);
        manager.addNewTask(task2);
        manager.addNewTask(task3);


        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Не удалось получить приоритетные задачи");

        List<Task> tasks = taskServer.getGson().fromJson(response.body(), new TypeToken<ArrayList<Task>>() {}.getType());
        assertEquals(task, tasks.get(0), "первая таска не совпала в prioritized");
        assertEquals(task3, tasks.get(2), "последняя таска не совпала в prioritized");


    }
}
