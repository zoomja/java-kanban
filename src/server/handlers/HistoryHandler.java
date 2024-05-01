package server.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import interfaces.HistoryManager;
import interfaces.TaskManager;
import server.adapters.DurationAdapter;
import server.adapters.LocalDateTimeAdapter;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public class HistoryHandler implements HttpHandler {
    private TaskManager taskManager;
    private Gson gson;

    public HistoryHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if (!"GET".equals(exchange.getRequestMethod())) {
            sendResponse(exchange, "Метод не разрешен", 405);
            return;
        }

        try (OutputStream os = exchange.getResponseBody()) {
            sendResponse(exchange, gson.toJson(taskManager.getHistory()), 200);
        } catch (Exception e) {
            sendResponse(exchange, "Внутренняя ошибка сервера", 500);
        }
    }

    private void sendResponse(HttpExchange exchange, String response, int responseCode) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(responseCode, response.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}
