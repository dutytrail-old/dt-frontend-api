package com.dutytrail.frontend.api.server;

import com.dutytrail.frontend.api.entity.ApiOutput;
import com.dutytrail.frontend.api.entity.DutyInput;
import com.dutytrail.frontend.api.exception.ApiException;
import com.dutytrail.frontend.api.remote.DutyClient;
import com.dutytrail.frontend.api.remote.TrailClient;
import com.dutytrail.frontend.api.remote.entity.Duty;
import com.dutytrail.frontend.api.remote.entity.Trail;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@Api(value = "/", description = "API for Dutytrail")
public class ApiService {

    @Autowired private DutyClient dutyClient;
    @Autowired private TrailClient trailClient;

    @ApiOperation(value = "Retrieve the list of duties", notes = "This operation returns all the duties on the system, without taking into account the users.", response = ApiOutput.class)
    @ApiResponses(value= {
            @ApiResponse(code = 200, message = "Duty List OK"),
            @ApiResponse(code = 404, message = "Resource not found")
    })
    @RequestMapping(value = "/duty/list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ApiOutput listDuty() {
        return new ApiOutput(this.getDuties(null));
    }

    @RequestMapping(value = "/duty/list/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ApiOutput listDutyByUser(@PathVariable("userId") String userId) {
        return new ApiOutput(this.getDuties(userId));
    }

    @RequestMapping(value = "/duty/{dutyId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON)
    public ApiOutput getDuty(@PathVariable("dutyId") String dutyId) {
        Duty duty = this.dutyClient.duty(dutyId);
        List<Trail> remoteTrail = this.trailClient.getTrail(duty.getId());
        return new ApiOutput(new com.dutytrail.frontend.api.entity.Duty(duty.getId(), duty.getName(), duty.getSubscriptions(), this.marshallRemoteTrail(remoteTrail)));
    }

    @RequestMapping(value = "/duty", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    public synchronized ApiOutput postDuty(@RequestBody DutyInput dutyInput) {
        Long dutyId = this.dutyClient.postDuty(dutyInput.getName());
        this.subscribeDuty(String.valueOf(dutyInput.getUserId()), String.valueOf(dutyId));
        String postedTrail = ". Posted initial trail for duty "+ dutyInput.getName() + " with id "+this.trailClient.postTrail(dutyInput.getUserId(), dutyId,"created");
        return new ApiOutput("Posted duty "+ dutyInput.getName() + " with id "+dutyId+postedTrail);
    }

    @RequestMapping(value = "/subscribe/{userId}/{dutyId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON)
    public synchronized ApiOutput postSubscribe(@PathVariable("userId") String userId, @PathVariable("dutyId") String dutyId) {
        return new ApiOutput("Subscribed duty "+ dutyId + " with response "+ this.subscribeDuty(userId, dutyId));
    }

    @RequestMapping(value = "/duty/{userId}/{dutyId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
    public synchronized ApiOutput deleteDuty(@PathVariable("userId") String userId, @PathVariable("dutyId") String dutyId) {
        return new ApiOutput("The user: "+userId+" is now un subscribed to the duty with id: "+dutyId+" with duty service response: " + this.dutyClient.unsubscribe(Long.valueOf(userId), Long.valueOf(dutyId)));
    }

    @RequestMapping(value = "/duty/{userId}/{dutyId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON)
    public synchronized ApiOutput putDuty(@PathVariable("userId") String userId, @PathVariable("dutyId") String dutyId) throws ApiException {
        if(this.dutyClient.isSubscribed(Long.valueOf(userId), Long.valueOf(dutyId))) {
            return new ApiOutput("Putting duty " + this.trailClient.postTrail(Long.valueOf(userId), Long.valueOf(dutyId), "done"));
        }
        throw new ApiException();
    }

    @RequestMapping(value = "/duty/{userId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON)
    public synchronized ApiOutput deleteDutyCascade(@PathVariable("userId") String userId) {
        List<Duty> dutiesToBeDeleted = this.dutyClient.listDuty(userId);
        for(Duty duty : dutiesToBeDeleted){
            this.dutyClient.deleteCascade(duty.getId());
            this.trailClient.delete(duty.getId());
        }
        return new ApiOutput("All duties deleted for user "+userId);
    }

    private List<com.dutytrail.frontend.api.entity.Trail> marshallRemoteTrail(List<Trail> remoteTrails){
        return remoteTrails.stream().map(remoteTrail -> new com.dutytrail.frontend.api.entity.Trail(remoteTrail.getUserId(), remoteTrail.getStatus(), remoteTrail.getTimestamp())).collect(Collectors.toList());
    }

    private Long subscribeDuty(String userId, String dutyId) {
        return this.dutyClient.subscribe(Long.valueOf(userId), Long.valueOf(dutyId));
    }

    private List<com.dutytrail.frontend.api.entity.Duty> getDuties(String userId) {
        List<com.dutytrail.frontend.api.entity.Duty> apiDutys = new ArrayList<>();
        apiDutys.addAll(this.dutyClient.listDuty(userId).stream().map(duty -> new com.dutytrail.frontend.api.entity.Duty(duty.getId(), duty.getName(), duty.getSubscriptions(), this.marshallRemoteTrail(this.trailClient.getTrail(duty.getId())))).collect(Collectors.toList()));
        return apiDutys;
    }

}