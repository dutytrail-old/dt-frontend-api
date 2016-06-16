package com.dutytrail.frontend.api.server;

import com.dutytrail.frontend.api.entity.ApiOutput;
import com.dutytrail.frontend.api.entity.DutyInput;
import com.dutytrail.frontend.api.remote.DutyClient;
import com.dutytrail.frontend.api.remote.TrailClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ApiService {

    @Autowired private DutyClient dutyClient;
    @Autowired private TrailClient trailClient;

    @RequestMapping(value = "/duty/list/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ApiOutput listDuty(@PathVariable("userId") String userId) {
        List<com.dutytrail.frontend.api.entity.Duty> apiDutys = new ArrayList<>();
        apiDutys.addAll(this.dutyClient.listDuty(userId).stream().map(duty -> new com.dutytrail.frontend.api.entity.Duty(duty.getId(), duty.getName(), this.trailClient.getTrail(duty.getId()))).collect(Collectors.toList()));
        return new ApiOutput(apiDutys);
    }

    @RequestMapping(value = "/duty/{dutyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ApiOutput getDuty(@PathVariable("dutyId") String dutyId) {
        return new ApiOutput(this.dutyClient.duty(dutyId));
    }

    @RequestMapping(value = "/duty", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public ApiOutput postDuty(@RequestBody DutyInput dutyInput) {
        Long dutyId = this.dutyClient.postDuty(dutyInput.getName());
        String postedTrail = ". Posted initial trail for duty "+ dutyInput.getName() + " with id "+this.trailClient.postTrail(dutyInput.getUserId(), dutyId,"created");
        return new ApiOutput("Posted duty "+ dutyInput.getName() + " with id "+dutyId+postedTrail);
    }

    @RequestMapping(value = "/duty/{dutyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
    public ApiOutput deleteDuty(@PathVariable("dutyId") String dutyId) {
        return new ApiOutput("Deleted duty: " + this.dutyClient.deleteDuty(Long.valueOf(dutyId)));
    }

    @RequestMapping(value = "/duty/{dutyId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
    public ApiOutput putDuty(@PathVariable("dutyId") String taskId) {
        return new ApiOutput("Putting duty "+taskId);
    }

}