package com.dutytrail.frontend.api.remote;

import com.dutytrail.frontend.api.remote.entity.Trail;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

@FeignClient(serviceId = "${dtservicetrail.name}", url = "${dtservicetrail.url}")
public interface TrailClient {

    @RequestMapping(method = RequestMethod.GET, value = "/ping", produces = "application/json")
    String ping();

    @RequestMapping(method = RequestMethod.PUT, value = "/trail/{userId}/{dutyId}/{status}", produces = MediaType.APPLICATION_JSON)
    Long postTrail(@PathVariable("userId") Long userId, @PathVariable("dutyId") Long dutyId, @PathVariable("status") String status);

    @RequestMapping(method = RequestMethod.GET, value = "/trail/{dutyId}", produces = MediaType.APPLICATION_JSON)
    ArrayList<Trail> getTrail(@PathVariable("dutyId") Long dutyId);

    @RequestMapping(method = RequestMethod.DELETE, value = "/trail/{dutyId}", produces = MediaType.APPLICATION_JSON)
    Long delete(@PathVariable("dutyId") Long dutyId);

}
