package test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import interfaces.TaskManager;
import managers.InMemoryTaskManager;
import managers.TaskType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.HttpTaskServer;
import server.adapters.DurationAdapter;
import server.adapters.LocalDateTimeAdapter;
import tasks.Epic;
import tasks.Subtask;
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
import static server.HttpTaskServer.getGson;

public class SubtaskHandlerTest {

    TaskManager manager = new InMemoryTaskManager();
    HttpTaskServer taskServer = new HttpTaskServer(manager);
    private HttpClient client;
    private final String baseUrl = "http://localhost:8080/subtasks/";

    public SubtaskHandlerTest() throws IOException {
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
    void testAddSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic Test", "Epic Description", TaskType.EPIC);
        manager.addNewEpic(epic);

        Subtask subtask = new Subtask("New Subtask", "Subtask description", epic.getId(), TaskType.SUBTASK, 30, LocalDateTime.now());
        String requestBody = getGson().toJson(subtask);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(201, response.statusCode(), "Не удалось создать подзадачу");

        Subtask resultSubtask = getGson().fromJson(response.body(), Subtask.class);
        assertNotNull(resultSubtask, "Возвращенная подзадача не должна быть null");
        assertEquals(subtask.getTittle(), resultSubtask.getTittle(), "Названия подзадач не совпадают");
    }

    @Test
    void testDeleteSubtask() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic Test", "Epic Description", TaskType.EPIC);
        manager.addNewEpic(epic);
        Subtask subtask = new Subtask("New Subtask", "Subtask description", epic.getId(), TaskType.SUBTASK, 30, LocalDateTime.now());
        manager.addNewSubtask(subtask);

        HttpRequest deleteRequest = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + subtask.getId()))
                .DELETE()
                .build();

        HttpResponse<String> deleteResponse = client.send(deleteRequest, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, deleteResponse.statusCode(), "Не удалось удалить подзадачу");
        assertEquals("Удаление сабтаски прошло успешно", deleteResponse.body(), "Подзадача не была удалена");
    }

    @Test
    void testGetAllSubtasks() throws IOException, InterruptedException {
        Epic epic = new Epic("Epic Test", "Epic Description", TaskType.EPIC);
        manager.addNewEpic(epic);
        manager.addNewSubtask(new Subtask("Subtask 1", "Description 1", epic.getId(), TaskType.SUBTASK, 30, LocalDateTime.now()));
        manager.addNewSubtask(new Subtask("Subtask 2", "Description 2", epic.getId(), TaskType.SUBTASK, 30, LocalDateTime.now().plusDays(1)));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl))
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode(), "Не удалось получить список подзадач");

        List<Subtask> subtasks = getGson().fromJson(response.body(), new TypeToken<ArrayList<Subtask>>(){}.getType());
        assertEquals(subtasks.size(), manager.getAllSubTasks().size(), "Количество возвращенных подзадач не совпадает с ожидаемым");
    }
}
