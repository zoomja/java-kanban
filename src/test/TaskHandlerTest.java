package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import interfaces.TaskManager;
import managers.InMemoryTaskManager;
import managers.Managers;
import managers.TaskType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;
import server.adapters.DurationAdapter;
import server.adapters.LocalDateTimeAdapter;
import tasks.Status;
import tasks.Task;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static server.HttpTaskServer.*;

public class TaskHandlerTest {

    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    private HttpClient client;
    private final String baseUrl = "http://localhost:8080/tasks/";

    public TaskHandlerTest() throws IOException {
    }

    @BeforeEach
    void setUp() throws IOException {
        taskServer.start();
        client = HttpClient.newHttpClient();
    }

    @AfterEach
    void tearDown() {
        taskServer.stop();
    }

    @Test
    void testAddTask() throws IOException, InterruptedException {
        Task task = new Task("New Task", "Test description", TaskType.TASK, 60, LocalDateTime.now());
        String requestBody = getGson().toJson(task);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode(), "Создание задачи не удалось");

        Task resultTask = getGson().fromJson(response.body(), Task.class);
        assertNotNull(resultTask, "Возвращенная задача не должна быть null");
        assertEquals(task.getTittle(), resultTask.getTittle(), "Название задачи не совпадает");
    }

    @Test
    void testDeleteTask() throws IOException, InterruptedException {
        Task task = new Task("New Task", "Test description", TaskType.TASK, 60, LocalDateTime.now());
        manager.addNewTask(task);
        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + task.getId()))
                .DELETE()
                .build();

        HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, deleteResponse.statusCode(), "Удаление задачи не удалось");
        assertEquals("Удаление таски прошло успешно", deleteResponse.body(), "таска не удалена");
    }

    @Test
    void testGetAllTasks() throws IOException, InterruptedException {
        manager.addNewTask(new Task("New Task", "Test description", TaskType.TASK, 60, LocalDateTime.now()));
        manager.addNewTask(new Task("New Task", "Test description", TaskType.TASK, 60, LocalDateTime.now().plusDays(1)));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Не удалось получить список задач");

        List<Task> tasks = getGson().fromJson(response.body(), new TypeToken<ArrayList<Task>>(){}.getType());
        assertEquals(tasks.size(), manager.getAllTasks().size(), "вернулись не все таски");
    }

    @Test
    void testUpdateTask() throws IOException, InterruptedException {
        Task task = new Task("Task", "task_description", TaskType.TASK, 20, LocalDateTime.now());
        manager.addNewTask(task);

        Task updatedTask = new Task("Updated Task", "Updated description", TaskType.TASK, 60, LocalDateTime.now().plusMinutes(40));
        updatedTask.setId(task.getId());
        manager.updateTask(updatedTask);

        HttpRequest updateRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + task.getId()))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(getGson().toJson(updatedTask)))
                .build();

        HttpResponse<String> updateResponse = client.send(updateRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, updateResponse.statusCode(), "Обновление задачи не удалось");
    }
}
