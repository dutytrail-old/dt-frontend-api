package com.dutytrail.frontend.api.remote;

import com.dutytrail.frontend.api.remote.entity.Duty;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@FeignClient(serviceId = "${dtserviceduty.name}", url = "${dtserviceduty.url}")
public interface DutyClient {

    @RequestMapping(method = RequestMethod.GET, value = "/ping", produces = MediaType.APPLICATION_JSON)
    String ping();

    @RequestMapping(method = RequestMethod.GET, value = "/duty/{dutyId}", produces = MediaType.APPLICATION_JSON)
    Duty duty(@PathVariable("dutyId") String dutyId);

    @RequestMapping(method = RequestMethod.GET, value = "/list/duty/{userId}", produces = MediaType.APPLICATION_JSON)
    ArrayList<Duty> listDuty(@PathVariable("userId") String userId);

    @RequestMapping(method = RequestMethod.POST, value = "/duty", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    Long postDuty(@RequestBody String name);

    @RequestMapping(method = RequestMethod.POST, value = "/subscribe/{userId}/{dutyId}", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    Long subscribe(@PathVariable("userId") Long userId, @PathVariable("dutyId") Long dutyId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/unsubscribe/{userId}/{dutyId}", produces = MediaType.APPLICATION_JSON, consumes = MediaType.APPLICATION_JSON)
    Long unsubscribe(@PathVariable("userId") Long userId, @PathVariable("dutyId") Long dutyId);

    @RequestMapping(method = RequestMethod.GET, value = "/is/subscribed/{userId}/{dutyId}", produces = MediaType.APPLICATION_JSON)
    Boolean isSubscribed(@PathVariable("userId") Long userId, @PathVariable("dutyId") Long dutyId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/duty/{dutyId}", produces = MediaType.APPLICATION_JSON)
    Long deleteDuty(@PathVariable("dutyId") Long dutyId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/duty/cascade/{dutyId}", produces = MediaType.APPLICATION_JSON)
    Long deleteCascade(@PathVariable("dutyId") Long dutyId);

}