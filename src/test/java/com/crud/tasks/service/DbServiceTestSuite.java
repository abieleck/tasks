package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTestSuite {

    @InjectMocks
    DbService dbService;

    @Mock
    TaskRepository taskRepository;

    @Test
    public void testGetAllTasksEmptyList() {
        //Given
        when(taskRepository.findAll()).thenReturn(new ArrayList<>());
        //When
        List<Task> tasks = dbService.getAllTasks();
        //Then
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void testGetAllTasks() {
        //Given
        List<Task> tasks = Arrays.asList(
                new Task(1L, "title1", "content1"),
                new Task(2L, "title2", "content2"));
        when(taskRepository.findAll()).thenReturn(tasks);
        //When
        List<Task> returnedTasks = dbService.getAllTasks();
        assertNotNull(returnedTasks);
        assertEquals(tasks.size(), returnedTasks.size());
        for(int i=0; i<tasks.size(); i++) {
            assertEquals(tasks.get(i).getId(), returnedTasks.get(i).getId());
            assertEquals(tasks.get(i).getTitle(), returnedTasks.get(i).getTitle());
            assertEquals(tasks.get(i).getContent(), returnedTasks.get(i).getContent());
        }
    }

    @Test
    public void testGetTaskByIdNotFound() {
        //Given
        when(taskRepository.findById(anyLong())).thenReturn(Optional.empty());
        //When
        Optional<Task> task = dbService.getTask(1L);
        //Then
        assertNotNull(task);
        assertFalse(task.isPresent());
    }

    @Test
    public void testSaveTask() {
        final Long CREATED_ID =  314L;
        //Given
        Task task = new Task(null, "title", "content");
        when(taskRepository.save(task)).thenReturn(new Task(CREATED_ID, task.getTitle(), task.getContent()));
        //When
        Task createdTask = dbService.saveTask(task);
        //Then
        assertEquals(CREATED_ID, createdTask.getId());
        assertEquals(task.getTitle(), createdTask.getTitle());
        assertEquals(task.getContent(), createdTask.getContent());

    }

    @Test
    public void testDeleteTask() {
        //Given
        //When
        dbService.deleteTask(1L);
        //Then
        verify(taskRepository, times(1)).deleteById(1L);
    }


}
