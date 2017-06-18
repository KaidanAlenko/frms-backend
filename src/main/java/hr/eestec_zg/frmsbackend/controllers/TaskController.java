package hr.eestec_zg.frmsbackend.controllers;

import hr.eestec_zg.frmscore.domain.models.SponsorshipType;
import hr.eestec_zg.frmscore.domain.models.Task;
import hr.eestec_zg.frmscore.domain.models.TaskStatus;
import hr.eestec_zg.frmscore.domain.models.dto.TaskDto;
import hr.eestec_zg.frmscore.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public List<Task> getActiveTasks() {
        return taskService.getTaskByStatus(TaskStatus.IN_PROGRESS);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.CREATED)
    public Task createTask(@RequestBody TaskDto task) {
        return taskService.createTask(task);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public Task getTaskByID(@PathVariable("id") Long id) {
        return taskService.getTask(id);
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Task> filterTasks(Integer eventId, Integer companyId, SponsorshipType type, TaskStatus status) {
        return taskService.filterTasks(eventId, companyId, type, status);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateTask(@PathVariable("id") Long id, @RequestBody TaskDto task) {
        if (id == null) {
            throw new IllegalArgumentException("Id is not defined");
        }

        this.taskService.updateTask(id, task);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteTask(@PathVariable("id") Long id) {
        taskService.deleteTask(taskService.getTask(id));
    }
}