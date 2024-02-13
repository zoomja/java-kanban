package managers;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private final File defaultFile;
    private final String newLine = System.lineSeparator();

    public FileBackedTasksManager() {
        this.defaultFile = Csv.createIfFileNotExist();
    }

    public FileBackedTasksManager(File defaultFile) {
        this.defaultFile = defaultFile;
    }

    @Override
    public void addNewTask(Task task) {
        super.addNewTask(task);
        save();
    }

    @Override
    public Task getTaskById(int taskId) {
        Task task = super.getTaskById(taskId);
        save();
        return task;
    }

    @Override
    public Subtask getSubtaskById(int subId) {
        Subtask subtask = super.getSubtaskById(subId);
        save();
        return subtask;
    }


    @Override
    public Epic getEpicById(int epicId) {
        Epic epic = super.getEpicById(epicId);
        save();
        return epic;
    }

    @Override
    public void updateTask(Task newTask) {
        super.updateTask(newTask);
        save();
    }

    @Override
    public void deleteTask(int taskId) {
        super.deleteTask(taskId);
        save();
    }

    @Override
    public void deleteSubtaskById(int subId) {
        super.deleteSubtaskById(subId);
        save();
    }

    @Override
    public void addNewEpic(Epic epic) {
        super.addNewEpic(epic);
        save();
    }

    @Override
    public void addNewSubtask(Subtask subtask) {
        super.addNewSubtask(subtask);
        save();
    }

    @Override
    public void printAllEpicsWithSubtasks() {
        super.printAllEpicsWithSubtasks();
        save();
    }

    @Override
    public void updateSubtask(Subtask newSubtask) {
        super.updateSubtask(newSubtask);
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteEpic(int epicId) {
        super.deleteEpic(epicId);
        save();
    }

    @Override
    public void remove(int id) {
        super.remove(id);
        save();
    }

    @Override
    public void printTaskById(int id) {
        super.printTaskById(id);
        save();
    }

    @Override
    public void printAllTasks() {
        super.printAllTasks();
        save();
    }

    @Override
    public void printIdEpic(int id) {
        super.printIdEpic(id);
        save();
    }

    @Override
    public void printAllEpics() {
        super.printAllEpics();
        save();
    }

    @Override
    public void calculateEpicStatus(int epicId) {
        super.calculateEpicStatus(epicId);
        save();
    }

    public void save() {
        try (FileWriter fileWriter = new FileWriter(defaultFile)) {
            fileWriter.write("id,type,name,status,description,epic\n");
            for (Task task : getAllTasks()) {
                fileWriter.write(task.toString());
                fileWriter.write(newLine);
            }

            for (Epic epic : getALlEpics()) {
                fileWriter.write(epic.toString());
                fileWriter.write(newLine);
            }

            for (Subtask subtask : getAllSubTasks()) {
                fileWriter.write(subtask.toString());
                fileWriter.write(newLine);
            }

            fileWriter.write(newLine);
            fileWriter.write(historyToString(historyManager));
        } catch (IOException e) {
            throw new ManagerSaveException("Не удалось сохранить");
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        List<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            while (bufferedReader.ready()) {
                lines.add(bufferedReader.readLine());
            }
        } catch (IOException ex) {
            throw new ManagerSaveException("Не удалось прочитать из файла.");
        }


        for (int i = 1; i < lines.size() - 2; i++) {
            Task task = Csv.fromString(lines.get(i));
            if (task != null) {
                if (task.getTaskType() == TaskType.TASK) {
                    fileBackedTasksManager.addNewTask(task);
                } else if (task.getTaskType() == TaskType.EPIC) {
                    fileBackedTasksManager.addNewEpic((Epic) task);
                } else {
                    fileBackedTasksManager.addNewSubtask((Subtask) task);
                }
            }
        }

        int maxId = getMaxId(lines.subList(1, lines.size() - 2));
        fileBackedTasksManager.getTaskById(maxId++);

        List<Integer> ids = historyFromString(lines.get(lines.size() - 1));
        for (Integer id : ids) {
            fileBackedTasksManager.historyManager.addTask(fileBackedTasksManager.getTask(id));
        }
        return fileBackedTasksManager;
    }

    private static String historyToString(HistoryManager manager) {
        List<Task> tasks = manager.getHistory();
        return tasks.stream().map(s -> String.valueOf(s.getId())).collect(Collectors.joining(","));
    }

    private static List<Integer> historyFromString(String value) {
        String[] lines = value.split(",");
        return Arrays.stream(lines).map(Integer::parseInt).collect(Collectors.toList());
    }

    private static int getMaxId(List<String> strings) {
        return strings.stream().map(s -> s.split(",")).mapToInt(m -> Integer.parseInt(m[0])).max().orElseThrow(NoSuchElementException::new);
    }

    private Task getTask(int id) {
        if (tasks.get(id) != null) {
            return tasks.get(id);
        } else if (epics.get(id) != null) {
            return epics.get(id);
        }
        return subtasks.get(id);
    }
}