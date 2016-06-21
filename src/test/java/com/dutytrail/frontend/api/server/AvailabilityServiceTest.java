package com.dutytrail.frontend.api.server;

import com.dutytrail.frontend.api.entity.Status;
import com.dutytrail.frontend.api.remote.DutyClient;
import com.dutytrail.frontend.api.remote.TrailClient;
import com.dutytrail.frontend.api.starter.Api;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Api.class, loader = SpringApplicationContextLoader.class)
@WebIntegrationTest({"eureka.client.enabled=false","spring.cloud.config.enabled=false", "spring.profiles.active=test"})
public class AvailabilityServiceTest {

    @Mock private DutyClient dutyClient;
    @Mock private TrailClient trailClient;
    private RestTemplate restTemplate = new TestRestTemplate();

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void statusTest(){
        ResponseEntity<Status> statusResponseEntity = restTemplate.getForEntity("http://localhost:9999/status", Status.class);
        Assert.assertNotNull(statusResponseEntity);
        Status status = statusResponseEntity.getBody();
        Assert.assertNotNull(status);
        Assert.assertEquals("Config properties loaded from HEROKU profile", status.getConfigPing());
        Assert.assertEquals("Duty Service Alive. Profile in use: Config properties loaded from HEROKU profile", status.getDutyServicePing());
        Assert.assertEquals("Trail Service Alive. Profile in use: Config properties loaded from HEROKU profile", status.getTrailServicePing());
    }
}
