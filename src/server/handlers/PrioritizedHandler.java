package server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import interfaces.TaskManager;
import server.adapters.DurationAdapter;
import server.adapters.LocalDateTimeAdapter;
import tasks.Task;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.TreeSet;

import static server.HttpTaskServer.writeResponse;

public class PrioritizedHandler implements HttpHandler {
    private TaskManager taskManager;
    private Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();

    public PrioritizedHandler(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            writeResponse(exchange, "Метод не разрешен", 405);
            return;
        }

        try (OutputStream os = exchange.getResponseBody()) {
            TreeSet<Task> prioritizedTasks = taskManager.getPrioritizedTasks();
            writeResponse(exchange, gson.toJson(prioritizedTasks), 200);
        } catch (Exception e) {
            writeResponse(exchange, "Внутренняя ошибка сервера", 500);
        }
    }

}
