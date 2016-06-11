package com.dutytrail.frontend.api.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(serviceId = "${dtservicetrail.name}", url = "${dtservicetrail.url}")
public interface TrailClient {

    @RequestMapping(method = RequestMethod.GET, value = "/ping", produces = "application/json")
    String ping();

}
