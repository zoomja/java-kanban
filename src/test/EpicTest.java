package test;

import managers.Managers;
import managers.TaskManager;
import managers.TaskType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.Subtask;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {

    TaskManager manager;
    Epic epicOne;
    Subtask subtaskOne;
    Subtask subtaskTwo;

    @BeforeEach
    public void setUp() {
        manager = Managers.getDefault();

        epicOne = new Epic("epic1", "Эпик 1 ", TaskType.EPIC);
        manager.addNewEpic(epicOne);
        subtaskOne = new Subtask("SUB1", "OPIS1", epicOne.getId(), TaskType.SUBTASK, 1440, LocalDateTime.now());
        subtaskTwo = new Subtask("SUB2", "OPIS2", epicOne.getId(), TaskType.SUBTASK, 1440, LocalDateTime.now().plusMonths(1));
    }

    @Test
    public void allSubtaskStatusNew() {
        manager.addNewSubtask(subtaskOne);
        manager.addNewSubtask(subtaskTwo);
        Assertions.assertEquals(Status.NEW, epicOne.getStatus(), "Статус эпика не NEW");
    }

    @Test
    public void allSubtaskStatusDone() {
        subtaskOne.setStatus(Status.DONE);
        subtaskTwo.setStatus(Status.DONE);
        manager.addNewSubtask(subtaskOne);
        manager.addNewSubtask(subtaskTwo);
        assertEquals(Status.DONE, epicOne.getStatus(), "Статус не DONE");
    }

    @Test
    public void subtaskStatusNewDone() {
        subtaskTwo.setStatus(Status.DONE);
        manager.addNewSubtask(subtaskOne);
        manager.addNewSubtask(subtaskTwo);
        assertEquals(Status.IN_Progress, epicOne.getStatus(), "Статус не IN_Progress");
    }

    @Test
    public void subtaskStatusInProgress() {
        subtaskOne.setStatus(Status.IN_Progress);
        subtaskTwo.setStatus(Status.IN_Progress);
        manager.addNewSubtask(subtaskOne);
        manager.addNewSubtask(subtaskTwo);
        assertEquals(Status.IN_Progress, epicOne.getStatus(), "Статус не IN_Progress");
    }


}