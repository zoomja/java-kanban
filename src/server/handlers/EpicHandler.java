package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import interfaces.TaskManager;
import tasks.Epic;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static server.HttpTaskServer.writeResponse;

public class EpicHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public EpicHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String[] path = exchange.getRequestURI().getPath().split("/");

        switch (method) {
            case "GET":
                if (path.length == 2) {
                    getAllEpics(exchange);
                } else if (path.length == 3) {
                    getEpic(exchange, Integer.parseInt(path[2]));
                }
                break;
            case "POST":
                createEpic(exchange);
                break;
            case "DELETE":
                deleteEpic(exchange, Integer.parseInt(path[2]));
                break;
            default:
                writeResponse(exchange, "Внутренняя ошибка сервера", 500);
        }
    }

    private void getAllEpics(HttpExchange exchange) throws IOException {
        String response = gson.toJson(taskManager.getALlEpics());
        writeResponse(exchange, response, 200);
    }

    private void getEpic(HttpExchange exchange, int epicId) throws IOException {
        try {
            Epic epic = taskManager.getEpicById(epicId);
            if (epic != null) {
                writeResponse(exchange, gson.toJson(epic), 200);
            } else {
                writeResponse(exchange, "Эпик не найден", 404);
            }
        } catch (NumberFormatException e) {
            writeResponse(exchange, "Неверный формат ID", 400);
        }
    }

    private void createEpic(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        Epic epicData = gson.fromJson(isr, Epic.class);
        Epic responseEpic = taskManager.addNewEpic(epicData);
        if (responseEpic != null) {
            writeResponse(exchange, gson.toJson(responseEpic), 201);
        } else {
            writeResponse(exchange, "Ошибка создания эпика", 400);
        }
    }

    private void deleteEpic(HttpExchange exchange, int i) throws IOException {
        String[] splitPath = exchange.getRequestURI().getPath().split("/");
        if (splitPath.length == 3) {
            try {
                int epicId = Integer.parseInt(splitPath[2]);
                taskManager.deleteEpic(epicId);
                writeResponse(exchange, "Эпик успешно удален", 200);
            } catch (NumberFormatException e) {
                writeResponse(exchange, "Неверный формат ID", 400);
            }
        } else {
            writeResponse(exchange, "Эпик не найден", 404);
        }
    }


}
