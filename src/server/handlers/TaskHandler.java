package server.handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import interfaces.TaskManager;
import tasks.Task;


import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static server.HttpTaskServer.writeResponse;

public class TaskHandler implements HttpHandler {
    private final TaskManager taskManager;
    private final Gson gson;

    public TaskHandler(TaskManager taskManager, Gson gson) {
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
                    getAllTasks(exchange);
                    break;
                }
                if (path.length == 3) {
                    getTask(exchange, Integer.parseInt(path[2]));
                    break;
                }
            case "PUT":
                handleTaskData(exchange, false);
                break;
            case "POST":
                handleTaskData(exchange, true);
                break;
            case "DELETE":
                deleteTask(exchange);
                break;
            default:
                writeResponse(exchange, "Внутренняя ошибка сервера", 500);
        }
    }

    private void getAllTasks(HttpExchange exchange) throws IOException {
        String response = gson.toJson(taskManager.getAllTasks());
        writeResponse(exchange, response, 200);
    }

    private void getTask(HttpExchange exchange, int taskId) throws IOException {
        try {
            Task task = taskManager.getTaskById(taskId);
            if (task != null) {
                writeResponse(exchange, gson.toJson(task), 200);
            } else {
                writeResponse(exchange, "Таска не найдена", 404);
            }
        } catch (Exception e) {
            writeResponse(exchange, "Не найдена такая задача", 400);
        }
    }

    private void deleteTask(HttpExchange exchange) throws IOException {
        String[] splitPath = exchange.getRequestURI().getPath().split("/");
        if (splitPath.length == 3) {
            try {
                int taskId = Integer.parseInt(splitPath[2]);
                taskManager.deleteTask(taskId);
                writeResponse(exchange, "Удаление таски прошло успешно", 200);
            } catch (NumberFormatException e) {
                writeResponse(exchange, "Не верный формат ID", 400);
            }
        } else {
            writeResponse(exchange, "Таска не найдена", 404);
        }
    }

    private void handleTaskData(HttpExchange exchange, boolean isNewTask) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        Task taskData = gson.fromJson(isr, Task.class);
        Task responseTask;
        if (isNewTask) {
            taskManager.addNewTask(taskData);
            responseTask = taskData;
            writeResponse(exchange, gson.toJson(responseTask), 201);
        } else {
            int taskId = Integer.parseInt(exchange.getRequestURI().getPath().split("/")[2]);
            taskData.setId(taskId);
            responseTask = taskManager.updateTask(taskData);
            writeResponse(exchange, gson.toJson(responseTask), responseTask != null ? 200 : 404);
        }
    }
}