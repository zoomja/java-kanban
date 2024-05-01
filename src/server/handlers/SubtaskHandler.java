package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import interfaces.TaskManager;
import tasks.Subtask;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class SubtaskHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public SubtaskHandler(TaskManager taskManager, Gson gson) {
        this.taskManager = taskManager;
        this.gson = gson;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String[] path = exchange.getRequestURI().getPath().split("/");

        try {
            switch (method) {
                case "GET":
                    if (path.length == 2) {
                        getAllSubtasks(exchange);
                    } else if (path.length == 3) {
                        getSubtask(exchange, Integer.parseInt(path[2]));
                    }
                    break;
                case "POST":
                    handleSubtaskData(exchange, true);
                    break;
                case "PUT":
                    if (path.length == 3) {
                        handleSubtaskData(exchange, false);
                    }
                    break;
                case "DELETE":
                    deleteSubtask(exchange); break;
                default:
                    exchange.sendResponseHeaders(405, -1);
            }
        } catch (Exception e) {
            writeResponse(exchange, "Внутренняя ошибка сервера", 500);
        } finally {
            exchange.close();
        }
    }

    private void getAllSubtasks(HttpExchange exchange) throws IOException {
        String response = gson.toJson(taskManager.getAllSubTasks());
        writeResponse(exchange, response, 200);
    }

    private void getSubtask(HttpExchange exchange, int subtaskId) throws IOException {
        Subtask subtask = taskManager.getSubtaskById(subtaskId);
        if (subtask != null) {
            writeResponse(exchange, gson.toJson(subtask), 200);
        } else {
            writeResponse(exchange, "Сабтаска не найдена", 404);
        }
    }

    private void deleteSubtask(HttpExchange exchange) throws IOException {
        String[] splitPath = exchange.getRequestURI().getPath().split("/");
        if (splitPath.length == 3) {
            try {
                int subTsaskId = Integer.parseInt(splitPath[2]);
                taskManager.deleteTask(subTsaskId);
                writeResponse(exchange, "Удаление сабтаски прошло успешно", 200);
            } catch (NumberFormatException e) {
                writeResponse(exchange, "Не верный формат ID", 400);
            }
        } else {
            writeResponse(exchange, "Сабска не найдена", 404);
        }
    }

    private void writeResponse(HttpExchange exchange, String responseString, int responseCode) throws IOException {
        exchange.sendResponseHeaders(responseCode, responseString.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(responseString.getBytes(StandardCharsets.UTF_8));
        }
    }

    private void handleSubtaskData(HttpExchange exchange, boolean isNewSubtask) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        Subtask subtaskData = gson.fromJson(isr, Subtask.class);


        Subtask responseSubtask;
        if (isNewSubtask) {
            responseSubtask = taskManager.addNewSubtask(subtaskData);
        } else {
            int subtaskId = Integer.parseInt(exchange.getRequestURI().getPath().split("/")[2]);
            subtaskData.setId(subtaskId);
            responseSubtask = taskManager.updateSubtask(subtaskData);
        }
        if (responseSubtask != null) {
            writeResponse(exchange, gson.toJson(responseSubtask), isNewSubtask ? 201 : 200);
        } else {
            writeResponse(exchange, "Сабтаска не найдена или данные некорректны", 404);
        }
    }
}
