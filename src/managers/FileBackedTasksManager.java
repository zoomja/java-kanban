package managers;

import interfaces.HistoryManager;
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
    public Task updateTask(Task newTask) {
        super.updateTask(newTask);
        save();
        return newTask;
    }

    @Override
    public void deleteTask(int taskId) {
        super.deleteTask(taskId);
        save();
    }

    @Override
    public boolean deleteSubtaskById(int subId) {
        super.deleteSubtaskById(subId);
        save();
        return false;
    }

    @Override
    public Epic addNewEpic(Epic epic) {
        super.addNewEpic(epic);
        save();
        return epic;
    }

    @Override
    public Subtask addNewSubtask(Subtask subtask) {
        super.addNewSubtask(subtask);
        save();
        return subtask;
    }

    @Override
    public Subtask updateSubtask(Subtask newSubtask) {
        super.updateSubtask(newSubtask);
        save();
        return newSubtask;
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
    public void calculateEpicStatus(int epicId) {
        super.calculateEpicStatus(epicId);
        save();
    }

    public void save() {
        try (FileWriter fileWriter = new FileWriter(defaultFile)) {
            fileWriter.write("id,type,name,status,description,startTime,endTime,duration,epic\n");
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

        fileBackedTasksManager.id = getMaxId(lines.subList(1, lines.size() - 2)) + 1;

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