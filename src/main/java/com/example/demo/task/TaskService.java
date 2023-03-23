package com.example.demo.task;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public void addNewTask(Task task) {
        taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new IllegalStateException("Task does not exists");
        }

        taskRepository.deleteById(taskId);
    }

    @Transactional
    public void updateTask(Long taskId, Task task) {
        Task existingTask = taskRepository.findById(taskId)
            .orElseThrow(() -> new IllegalStateException(
                    "Task does not exist"
            ));

        if(task.getTitle() != null &&
                task.getTitle().length() > 0 &&
                !Objects.equals(existingTask.getTitle(), task.getTitle())) {
            existingTask.setTitle(task.getTitle());
        }

        if(task.getDescription() != null &&
                task.getDescription().length() > 0 &&
                !Objects.equals(existingTask.getDescription(), task.getDescription())) {
            existingTask.setDescription(task.getDescription());
        }

        if(task.getCompleted() != null && !Objects.equals(existingTask.getCompleted(), task.getCompleted())) {
            existingTask.setCompleted(task.getCompleted());
        }
    }
}
