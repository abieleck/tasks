package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/task")
public class TaskController {

    @RequestMapping(method = RequestMethod.GET)
    public List<TaskDto> getTasks() {
        return new ArrayList<>();
    }

    @RequestMapping(method = RequestMethod.GET, params = "taskId")
    public TaskDto getTask(@RequestParam long taskId) {
        return new TaskDto(taskId, "test title", "test_content");
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteTask(String taskId) {

    }

    @RequestMapping(method = RequestMethod.PUT)
    public TaskDto updateTask(TaskDto taskDto) {
        return new TaskDto((long)1, "Edited test title", "Test content");
    }

    @RequestMapping(method = RequestMethod.POST)
    public void createTask(TaskDto taskDto) {

    }



}
