package hr.eestec_zg.frmsbackend.controllers;

import java.util.List;

import hr.eestec_zg.frmsbackend.domain.models.Task;
import hr.eestec_zg.frmsbackend.domain.models.TaskStatus;
import hr.eestec_zg.frmsbackend.domain.models.dto.TaskDto;
import hr.eestec_zg.frmsbackend.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @RequestMapping(method= RequestMethod.GET)
    @ResponseStatus(value= HttpStatus.OK)
    public List <Task> getActiveTasks(){
        return taskService.getTaskByStatus(TaskStatus.IN_PROGRESS);
    }

    @RequestMapping(method= RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public TaskDto createTask(@RequestBody TaskDto task){
        taskService.createTask(task);
        return task;
    }

    @RequestMapping(value="{id}", method = RequestMethod.GET)
    @ResponseStatus(value= HttpStatus.OK)
    public Task getTaskByID(@PathVariable("id") Long id){
        return taskService.getTask(id);
    }

    @RequestMapping(value="{id}", method = RequestMethod.PUT)
    @ResponseStatus(value= HttpStatus.CREATED)
    public void updateTask(@RequestBody Task task){
        taskService.updateTask(task);
    }

    @RequestMapping(value="{id}", method = RequestMethod.DELETE)
    @ResponseStatus(value= HttpStatus.OK)
    public void deleteTask(@PathVariable("id") Long id){
        taskService.deleteTask(taskService.getTask(id));
    }
}
