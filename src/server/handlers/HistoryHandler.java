package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import interfaces.TaskManager;



import java.io.IOException;
import java.io.OutputStream;


import static server.HttpTaskServer.writeResponse;


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
            writeResponse(exchange, "Метод не разрешен", 405);
            return;
        }

        try (OutputStream os = exchange.getResponseBody()) {
            writeResponse(exchange, gson.toJson(taskManager.getHistory()), 200);
        } catch (Exception e) {
            writeResponse(exchange, "Внутренняя ошибка сервера", 500);
        }
    }
}
