package manager;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {

    protected HashMap<Integer, Task> tasks;
    protected HashMap<Integer, Epic> epics;
    protected HashMap<Integer, Subtask> subtasks;

    public Manager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    //     создается новый объект класса Task и добавляется в HashMap tsaks
    public void newTask(String tittle, String description) {
        Task task = new Task(tittle, description);
        tasks.put(task.getId(), task);
        System.out.println("id - " + task.getId() + " / " + task.getTittle() + " / " + task.getDescription());
    }

    //    ищем и выводим task по id из созданных tasks
    public void getIdTask(int id) {
        Task task = tasks.get(id);
        String tittle = task.getTittle();
        String description = task.getDescription();
        Status status = task.getStatus();
        System.out.println("id - " + id + " / " + tittle + " / " + description + " / " + status);
    }

    //    выводим все tasks, перебирая все id имеющихся task
    public void getAllTasks() {
        for (Integer id : tasks.keySet()) {
            getIdTask(id);
        }
    }

    //    обновление task
    public void updateTask(int taskId, String tittle, String description, Status status) {
        Task task = tasks.get(taskId);
        task.setTittle(tittle);
        task.setDescription(description);
        task.setStatus(status);
        tasks.put(taskId, task);
        System.out.println(taskId + " таска обновлена.");
    }

    //    Удаление по id
    public void deleteTask(int taskId) {
        tasks.remove(taskId);
        System.out.println("id - " + taskId + " значение удаленно.");
    }

    //    сздание эпика
    public void newEpic(String tittle, String description) {
        Epic epic = new Epic(tittle, description);
        epics.put(epic.getId(), epic);
        System.out.println("id - " + epic.getId() + " / " + epic.getTittle() + " / " + epic.getDescription());
    }

    //    поиск эпика по id
    public void getIdEpic(int id) {
        Epic epic = epics.get(id);
        String tittle = epic.getTittle();
        String description = epic.getDescription();
        Status status = epic.getStatus();
        calculateEpicStatus(id);
        System.out.println("id - " + id + " / " + tittle + " / " + description);
    }

//    Вывод всех эпиков
    public void getAllEpics() {
        for (Integer id : epics.keySet()) {
            getIdEpic(id);
        }
    }

//    создание subtast
    public void newSubtask (String tittle, String description, int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Integer> subtasksId = epic.getSubtaskId();
        Subtask subtask = new Subtask(tittle, description, epicId);
        int subtaskId = subtask.getId();
        subtasks.put(subtaskId, subtask);
        subtasksId.add(subtaskId);
        epic.setSubtaskId(subtasksId);

        System.out.println("В эпик ID-" + epicId + " добавлен новый Subtask");
        System.out.println("ID - " + subtaskId + " / " + tittle);
    }

    // Метод для вывода всех эпиков с их подзадачами
    public void getAllEpicsWithSubtasks() {
        for (Integer epicId : epics.keySet()) {
            Epic epic = epics.get(epicId);
            String epicTitle = epic.getTittle();
            String epicDescription = epic.getDescription();
            Status epicStatus = epic.getStatus();
            calculateEpicStatus(epicId);

            System.out.println("Эпик ID - " + epicId + " / " + epicTitle + " / " + epicDescription);

            ArrayList<Integer> subtaskIds = epic.getSubtaskId();
            for (Integer subtaskId : subtaskIds) {
                Subtask subtask = subtasks.get(subtaskId);
                String subtaskTitle = subtask.getTittle();
                String subtaskDescription = subtask.getDescription();
                Status subtaskStatus = subtask.getStatus();

                System.out.println("   - Subtask ID - " + subtaskId + " / " + subtaskTitle + " / " + subtaskDescription
                        + " / " + subtaskStatus);
            }
        }
    }

//    обновление subtask
    public void updateSubtask(int subId, String tittle ,String description, Status status) {
        Subtask subtask = subtasks.get(subId);
        subtask.setDescription(description);
        subtask.setTittle(tittle);
        subtask.setStatus(status);
        subtasks.put(subId, subtask);

        System.out.println("id - " + subId + " / " + tittle + " / " + description + " / " + status);
    }

    private void calculateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Integer> subtasksIds = epic.getSubtaskId();
        Status oldStatus = epic.getStatus();
        Status newStatus;

        if (subtasksIds.size() == 0) {
            newStatus = Status.NEW;
        } else {
            boolean isExistNew = false;
            boolean isExistDone = false;
            boolean isExistWIP = false;

            for (Integer subId : subtasksIds) {
                Status subStatus = subtasks.get(subId).getStatus();
                if (subStatus == Status.NEW) {
                    isExistNew = true;
                } else if (subStatus == Status.DONE) {
                    isExistDone = true;
                } else if (subStatus == Status.IN_Progress) {
                    isExistWIP = true;
                }
            }

            if (isExistNew && !isExistDone && !isExistWIP) {
                newStatus = Status.NEW;
            } else if (!isExistNew && isExistDone && !isExistWIP) {
                newStatus = Status.DONE;
            } else {
                newStatus = Status.IN_Progress;
            }
        }

        if (oldStatus != newStatus) {
            epic.setStatus(newStatus);
            epics.put(epicId, epic);
            System.out.println("Статус эпика " + newStatus);
        } else {
            System.out.println("Статус эпика " + oldStatus);
        }
    }
    public void deleteAllEpics() {
        subtasks.clear();
        epics.clear();
        System.out.println("Эпики даленны");
    }

//    удалиение эпика по id
    public void deleteEpic(int epicId) {
        if (epics.containsKey(epicId)) {
            epics.remove(epicId);
            ArrayList<Integer> subtaskIdsToRemove = new ArrayList<>();
            for (Subtask subtask : subtasks.values()) {
                if (subtask.getEpicId() == epicId) {
                    subtaskIdsToRemove.add(subtask.getId());
                }
            }
            for (Integer subtaskId : subtaskIdsToRemove) {
                subtasks.remove(subtaskId);
            }
            System.out.println("Эпик ID-" + epicId + " удален вместе с подзадачами.");
        } else {
            System.out.println("Эпик с ID-" + epicId + " не найден.");
        }
    }

}