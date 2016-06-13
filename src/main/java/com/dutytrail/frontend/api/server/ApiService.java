package com.dutytrail.frontend.api.server;

import com.dutytrail.frontend.api.entity.TaskInput;
import com.dutytrail.frontend.api.entity.TaskOutput;
import com.dutytrail.frontend.api.remote.DutyClient;
import com.dutytrail.frontend.api.remote.TrailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;

@RestController
public class ApiService {

    @Autowired private DutyClient dutyClient;
    @Autowired private TrailClient trailClient;

    @RequestMapping(value = "/task/list/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public TaskOutput listTask(@PathVariable("userId") String userId) {
        return new TaskOutput("Getting list task for user "+userId);
    }

    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public TaskOutput getTask(@PathVariable("taskId") String taskId) {
        return new TaskOutput("Getting task "+taskId);
    }

    @RequestMapping(value = "/task", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public TaskOutput postTask(@RequestBody TaskInput taskInput) {
        return new TaskOutput("Posting task "+taskInput.getTask());
    }

    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
    public TaskOutput deleteTask(@PathVariable("taskId") String taskId) {
        return new TaskOutput("Deleting task "+taskId);
    }

    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
    public TaskOutput putTask(@PathVariable("taskId") String taskId) {
        return new TaskOutput("Putting task "+taskId);
    }

}