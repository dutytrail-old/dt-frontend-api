package com.dutytrail.frontend.api.server;

import com.dutytrail.frontend.api.entity.ApiOutput;
import com.dutytrail.frontend.api.entity.Status;
import com.dutytrail.frontend.api.remote.DutyClient;
import com.dutytrail.frontend.api.remote.TrailClient;
import com.dutytrail.frontend.api.starter.Api;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Api.class, loader = SpringApplicationContextLoader.class)
@WebIntegrationTest({"eureka.client.enabled=false","spring.cloud.config.enabled=false", "spring.profiles.active=test"})
public class AvailabilityServiceTest {

    @Mock private DutyClient dutyClient;
    @Mock private TrailClient trailClient;
    @Autowired private AvailabilityService availabilityService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(availabilityService, "dutyClient", this.dutyClient);
        ReflectionTestUtils.setField(availabilityService, "trailClient", this.trailClient);
    }

    @Test
    public void pingTest(){
        ApiOutput apiOutput = this.availabilityService.ping();
        Assert.assertNotNull(apiOutput);
        String ping = (String) apiOutput.getContent();
        Assert.assertEquals("Api Front End Alive. Profile in use: Config properties loaded from LOCAL profile", ping);
    }

    @Test
    public void statusTest(){
        when(dutyClient.ping()).thenReturn("Duty Service Alive. Profile in use: Config properties loaded from LOCAL profile");
        when(trailClient.ping()).thenReturn("Trail Service Alive. Profile in use: Config properties loaded from LOCAL profile");
        ApiOutput apiOutput = this.availabilityService.status();
        Assert.assertNotNull(apiOutput);
        Status status = (Status) apiOutput.getContent();
        Assert.assertEquals("Config properties loaded from LOCAL profile", status.getConfigPing());
        Assert.assertEquals("Duty Service Alive. Profile in use: Config properties loaded from LOCAL profile", status.getDutyServicePing());
        Assert.assertEquals("Trail Service Alive. Profile in use: Config properties loaded from LOCAL profile", status.getTrailServicePing());
    }
}
