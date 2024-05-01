package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpServer;
import interfaces.HistoryManager;
import interfaces.TaskManager;
import managers.Managers;
import server.adapters.DurationAdapter;
import server.adapters.LocalDateTimeAdapter;
import server.handlers.*;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.time.Duration;
import java.time.LocalDateTime;

public class HttpTaskServer {
    private static final int PORT = 8080;
    private static HttpServer server;
    private static TaskManager taskManager;
    private static Gson gson;

    public HttpTaskServer(TaskManager manager) throws IOException {
        this.taskManager = manager;
        init();
    }


    public static void init() throws IOException {
        gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .create();

        server = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        server.createContext("/tasks", new TaskHandler(taskManager, gson));
        server.createContext("/subtasks", new SubtaskHandler(taskManager, gson));
        server.createContext("/epics", new EpicHandler(taskManager, gson));
        server.createContext("/history", new HistoryHandler(taskManager, gson));
        server.createContext("/prioritized", new PrioritizedHandler(taskManager));
    }

    public static void start() {
        server.start();
        System.out.println("Сервер запущен на порту " + PORT);
    }

    public static void stop() {
        server.stop(0);
        System.out.println("Остановили сервер на порту " + PORT);
    }

    public static void main(String[] args) throws IOException {
        taskManager = Managers.getDefault();
        init();
        start();
    }

    public static Gson getGson() {
        return gson;
    }
}