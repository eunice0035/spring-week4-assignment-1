package com.codesoom.assignment.Task.application;

import com.codesoom.assignment.Task.TaskNotFoundException;
import com.codesoom.assignment.Task.domain.Task;
import com.codesoom.assignment.Task.domain.TaskRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


/**
 * Task Entity 를 다루는 메소드 작성
 */
@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public Task getTask(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task createTask(Task source) {
        Task task = new Task();
        task.setTitle(source.getTitle());

        return taskRepository.save(task);
    }

    public Task updateTask(Long id, Task source) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));;
        task.setTitle(source.getTitle());

        return task;
    }

    public Task deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        taskRepository.delete(task);
        return task;
    }

}
