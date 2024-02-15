package managers;

import tasks.Epic;
import tasks.Status;
import tasks.Subtask;
import tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTaskManager implements TaskManager {


    protected Map<Integer, Task> tasks;
    protected Map<Integer, Epic> epics;
    protected Map<Integer, Subtask> subtasks;
    protected final HistoryManager historyManager;

    int id = 0;

    public InMemoryTaskManager() {
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        historyManager = Managers.getDefaultHistoryManager();
    }


    private int countId() {
        return ++id;
    }

    @Override
    public void addNewTask(Task task) {
        if(task.getId() == 0) {
            task.setId(countId());
        }
        tasks.put(task.getId(), task);
        System.out.println("id - " + task.getId() + " / " + task.getTittle() + " / " + task.getDescription());
    }

    //    возвращение Task по id
    @Override
    public Task getTaskById(int taskId) {
        Task task = tasks.get(taskId);
        if (task != null) {
            historyManager.addTask(task);
        }
        return tasks.get(taskId);
    }


    // возврат Sabtask по ID
    @Override
    public Subtask getSubtaskById(int subId) {
        Subtask subtask = subtasks.get(subId);
        if (subtask != null) {
            historyManager.addTask(subtask);
        }
        return subtasks.get(subId);
    }

    //  возвращение Epic по id
    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic != null) {
            historyManager.addTask(epic);
        }
        return epics.get(epicId);
    }


    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }


    @Override
    public void printTaskById(int id) {
        Task task = tasks.get(id);
        String tittle = task.getTittle();
        String description = task.getDescription();
        Status status = task.getStatus();
        System.out.println("id - " + id + " / " + tittle + " / " + description + " / " + status);
    }

    //    выводим все tasks, перебирая все id имеющихся task
    @Override
    public void printAllTasks() {
        for (Integer id : tasks.keySet()) {
            printTaskById(id);
        }
    }

    //    обновление task
    @Override
    public void updateTask(Task newTask) {
        Task task = tasks.get(newTask.getId());
        task.setTittle(newTask.getTittle());
        task.setDescription(newTask.getDescription());
        task.setStatus(newTask.getStatus());
        tasks.put(newTask.getId(), task);
        System.out.println(newTask.getId() + " таска обновлена.");
    }

    @Override
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getALlEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<Subtask> getAllSubTasks() {
        return new ArrayList<>(subtasks.values());
    }

    //    Удаление по id
    @Override
    public void deleteTask(int taskId) {
        tasks.remove(taskId);
        System.out.println("id - " + taskId + " значение удаленно.");
    }

    @Override
    public void deleteSubtaskById(int subId) {
        if (subtasks.containsKey(subId)) {
            Subtask subtask = subtasks.get(subId);
            int epicId = subtask.getEpicId();
            Epic epic = epics.get(epicId);
            ArrayList<Integer> subtaskIds = epic.getSubtaskId();
            subtaskIds.remove(Integer.valueOf(subId));
            subtasks.remove(subId);
            System.out.println("Subtask ID-" + subId + " удален.");
            calculateEpicStatus(epicId);
        } else {
            System.out.println("Subtask с ID-" + subId + " не найден.");
        }
    }

    //    сздание эпика
    @Override
    public void addNewEpic(Epic epic) {
        if (epic.getId() == 0) {
            epic.setId(countId());
        }
        epics.put(epic.getId(), epic);
        System.out.println("id - " + epic.getId() + " / " + epic.getTittle() + " / " + epic.getDescription());
    }

    //    поиск эпика по id
    @Override
    public void printIdEpic(int id) {
        Epic epic = epics.get(id);
        String tittle = epic.getTittle();
        String description = epic.getDescription();
        Status status = epic.getStatus();
        calculateEpicStatus(id);
        System.out.println("id - " + id + " / " + tittle + " / " + description);
    }

    //    Вывод всех эпиков
    @Override
    public void printAllEpics() {
        for (Integer id : epics.keySet()) {
            printIdEpic(id);
        }
    }

    //    создание subtask
    @Override
    public void addNewSubtask(Subtask subtask) {
        if (subtask.getId() == 0) {
            subtask.setId(countId());
        }
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);
        ArrayList<Integer> subtasksId = epic.getSubtaskId();
        int subtaskId = subtask.getId();
        subtasks.put(subtaskId, subtask);
        subtasksId.add(subtaskId);
        epic.setSubtaskId(subtasksId);

        System.out.println("В эпик ID-" + epicId + " добавлен новый Subtask");
        calculateEpicStatus(epicId);
    }

    // Метод для вывода всех эпиков с их подзадачами
    @Override
    public void printAllEpicsWithSubtasks() {
        for (Integer epicId : epics.keySet()) {
            Epic epic = epics.get(epicId);
            String epicTitle = epic.getTittle();
            String epicDescription = epic.getDescription();
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
    @Override
    public void updateSubtask(Subtask newSubtask) {
        Subtask subtask = subtasks.get(newSubtask.getId());
        subtask.setDescription(newSubtask.getDescription());
        subtask.setTittle(newSubtask.getTittle());
        subtask.setStatus(newSubtask.getStatus());
        subtasks.put(subtask.getId(), subtask);

        System.out.println("id - " + subtask.getId() + " / " + subtask.getTittle() + " / " + subtask.getDescription() + " / " + subtask.getStatus());
        calculateEpicStatus(subtask.getEpicId());
    }

    public void calculateEpicStatus(int epicId) {
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
    @Override
    public void deleteAllEpics() {
        subtasks.clear();
        epics.clear();
        System.out.println("Эпики даленны");
    }

    //    удалиение эпика по id
    @Override
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

    @Override
    public void remove(int id) {
        historyManager.remove(id);
    }

}