package com.krillinator.lektion_5.controllers;

import com.krillinator.lektion_5.models.task.TaskEntity;
import com.krillinator.lektion_5.models.task.TaskRepository;
import com.krillinator.lektion_5.models.user.Roles;
import com.krillinator.lektion_5.models.user.UserEntity;
import com.krillinator.lektion_5.models.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("myTasks")
    public String showTasksPage(TaskEntity taskEntity, Model model) {

        // Retrieve authentication object to get the current user's information
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get the username of the currently logged-in user
        String username = authentication.getName();

        // Find the user entity based on the username (you may need to implement this method in your UserRepository)
        UserEntity currentUser = userRepository.findByUsername(username);

        // Get all tasks associated with the user
        List<TaskEntity> userTasks = currentUser.getTasks();

        model.addAttribute("userTasks", userTasks);

        return "my-tasks";
    }

    @GetMapping("/task")
    public String registerUserPage(TaskEntity task, Model model) {

        model.addAttribute("task", task);

        return "task";
    }

    @PostMapping("/task")
    public String registerUser(
            @Valid TaskEntity task,   // Enables Error Messages
            BindingResult result      // Ties the object with result
    ) {

        // Check FOR @Valid Errors
        if (result.hasErrors()) {
            return "task";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        UserEntity currentUser = userRepository.findByUsername(username);

        task.setUser(currentUser);

        // Save the task with the user association
        taskRepository.save(task);

        return "redirect:/";
    }

    @GetMapping("/editTask")
    public String showEditTaskPage(@RequestParam("taskId") Long taskId, Model model) {

        // Fetch the task details from the repository based on taskId
        TaskEntity task = taskRepository.findById(taskId).orElse(null);

        if (task == null) {
            // Handle task not found error
            return "error-page";
        }

        model.addAttribute("taskId", taskId);
        model.addAttribute("taskTitle", task.getTitle());
        model.addAttribute("taskDescription", task.getDescription());

        return "edit-task";
    }

    @PostMapping("/save-edit-task")
    public String saveTask(@RequestParam("taskId") Long taskId,
                           @RequestParam("title") String title,
                           @RequestParam("description") String description) {
        // Update the task details in the repository based on taskId
        TaskEntity task = taskRepository.findById(taskId).orElse(null);
        if (task == null) {
            // Handle task not found error
            return "error-page";
        }
        task.setTitle(title);
        task.setDescription(description);
        taskRepository.save(task);
        // Redirect back to the task page after editing
        return "redirect:/myTasks";
    }

}
