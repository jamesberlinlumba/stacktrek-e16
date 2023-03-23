package com.example.demo.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TaskController implements ErrorController {
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/")
    public String getTasks(Model model) {
        model.addAttribute("tasks", taskService.getTasks());
        model.addAttribute("showTaskForm", false);
        return "index.html";
    }

    @GetMapping("/create-task")
    public String createTask(Model model) {
        model.addAttribute("tasks", taskService.getTasks());
        Task task = new Task();
        model.addAttribute("task", task);
        model.addAttribute("showTaskForm", true);
        return "index.html";
    }

    @GetMapping("/edit-task/{id}")
    public String editTasks(@PathVariable("id") Long id, Model model) {
        model.addAttribute("tasks", taskService.getTasks());
        model.addAttribute("showTaskForm", false);
        Task modifiedTask = new Task();
        model.addAttribute("modifiedTask", modifiedTask);
        model.addAttribute("showEditForm", id);
        return "index.html";
    }

    @PostMapping("/")
    public String registerNewTask(@ModelAttribute("task") Task task) {
        taskService.addNewTask(task);
        return "redirect:/";
    }

    @DeleteMapping("/{id}")
    public String deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(id);
        return "redirect:/";
    }

    @PutMapping("/{id}")
    public String updateTask(@PathVariable("id") Long id, @ModelAttribute("modifiedTask") Task modifiedTask) {
        taskService.updateTask(id, modifiedTask);
        return "redirect:/";
    }

    @RequestMapping(value = "/**")
    public String handleUndeclaredUrl() {
        return "redirect:/";
    }
}
