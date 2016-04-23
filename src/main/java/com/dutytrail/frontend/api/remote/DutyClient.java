package com.dutytrail.frontend.api.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("dtserviceduty")
public interface DutyClient {

    @RequestMapping(method = RequestMethod.GET, value = "/pinga", produces = "application/json")
    String ping();

}
