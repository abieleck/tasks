package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TaskMapperTestSuite {

    @Autowired
    TaskMapper taskMapper;

    private Task task1 = new Task(1L, "task1", "content1");
    private Task task2 = new Task(2L, "task2", "content2");
    private List<Task> tasks = Arrays.asList(task1, task2);

    private TaskDto taskDto1 = new TaskDto(1L, "task1", "content1");
    private TaskDto taskDto2 = new TaskDto(2L, "task2", "content2");
    private List<TaskDto> taskDtos = Arrays.asList(taskDto1, taskDto2);

    private boolean taskIdenticalToTaskDto(Task task, TaskDto taskDto) {
        return task.getId() == taskDto.getId() &&
                Objects.equals(task.getTitle(), taskDto.getTitle()) &&
                Objects.equals(task.getContent(), taskDto.getContent());
    }

    @Test
    public void testMapToTask() {
        //Given

        //When
        Task mappedTask = taskMapper.mapToTask(taskDto1);
        //Then
        assertTrue(taskIdenticalToTaskDto(mappedTask, taskDto1));
    }

    @Test
    public void testMapToTaskDtoList() {
        //Given
        //When
        List<TaskDto> mappedTaskDtos = taskMapper.mapToTaskDtoList(tasks);
        //Then
        assertEquals(tasks.size(), mappedTaskDtos.size());
        IntStream.range(0, tasks.size())
                .forEach(i -> assertTrue(taskIdenticalToTaskDto(tasks.get(i), mappedTaskDtos.get(i))));
    }

    @Test
    public void testMapEmptyTaskList() {
        //Given
        List<Task> emptyList = new ArrayList<>();
        //When
        List<TaskDto> mappedTaskDtos = taskMapper.mapToTaskDtoList(emptyList);
        //Then
        assertTrue(mappedTaskDtos.isEmpty());
    }

    @Test
    public void testMapToTaskDto() {
        //Given
        //When
        TaskDto taskDto = taskMapper.mapToTaskDto(task1);
        //then
        assertTrue(taskIdenticalToTaskDto(task1, taskDto));
    }


}
