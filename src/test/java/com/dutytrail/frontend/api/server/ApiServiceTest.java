package com.dutytrail.frontend.api.server;

import com.dutytrail.frontend.api.entity.ApiOutput;
import com.dutytrail.frontend.api.entity.DutyInput;
import com.dutytrail.frontend.api.exception.ApiException;
import com.dutytrail.frontend.api.remote.DutyClient;
import com.dutytrail.frontend.api.remote.TrailClient;
import com.dutytrail.frontend.api.remote.entity.Duty;
import com.dutytrail.frontend.api.remote.entity.Trail;
import com.dutytrail.frontend.api.remote.entity.User;
import com.dutytrail.frontend.api.starter.Api;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationContextLoader;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Api.class, loader = SpringApplicationContextLoader.class)
@WebIntegrationTest({"eureka.client.enabled=false","spring.cloud.config.enabled=false", "spring.profiles.active=test"})
public class ApiServiceTest {

    @Mock private DutyClient dutyClient;
    @Mock private TrailClient trailClient;
    @Autowired private ApiService apiService;
    private ArrayList<User> fakeUsers;
    private ArrayList<Duty> fakeDuties;
    private ArrayList<Trail> fakeTrails;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        ReflectionTestUtils.setField(apiService, "dutyClient", this.dutyClient);
        ReflectionTestUtils.setField(apiService, "trailClient", this.trailClient);
        this.fakeUsers = new ArrayList<>();
        this.fakeUsers.add(new User(1L, "firstName", "lastName", "email", "passphrase", "gender"));
        new User();
        this.fakeDuties = new ArrayList<>();
        this.fakeDuties.add(new Duty(1L, "name", this.fakeUsers));
        new Duty();
        this.fakeTrails = new ArrayList<>();
        this.fakeTrails.add(new Trail(1L, "done", new Timestamp(Calendar.getInstance().getTime().getTime())));
        new Trail();

        when(this.dutyClient.duty(Mockito.anyString())).thenReturn(new Duty(1L, "SomeDutyName", fakeUsers));
        when(this.dutyClient.postDuty(Mockito.anyString())).thenReturn(1L);
        when(this.dutyClient.isSubscribed(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Boolean.TRUE);
        when(this.dutyClient.listDuty(Mockito.anyString())).thenReturn(this.fakeDuties);
        when(this.dutyClient.subscribe(Mockito.anyLong(), Mockito.anyLong())).thenReturn(1L);
        when(this.dutyClient.deleteCascade(Mockito.anyLong())).thenReturn(1L);
        when(this.dutyClient.deleteCascade(Mockito.anyLong())).thenReturn(1L);

        when(this.trailClient.getTrail(Mockito.anyLong())).thenReturn(this.fakeTrails);
        when(this.trailClient.postTrail(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString())).thenReturn(1L);
        when(this.trailClient.delete(Mockito.anyLong())).thenReturn(1L);
    }

    @Test
    public void listDutyTest(){
        ApiOutput apiOutput = this.apiService.listDuty();
        Assert.assertNotNull(apiOutput);
        ArrayList<com.dutytrail.frontend.api.entity.Duty> duties = (ArrayList<com.dutytrail.frontend.api.entity.Duty>) apiOutput.getContent();
        Assert.assertNotNull(duties);
        com.dutytrail.frontend.api.entity.Duty duty = duties.get(0);
        Assert.assertNotNull(duty);
        Assert.assertTrue(1L == duty.getId());
        Assert.assertEquals("name", duty.getName());
        ArrayList<com.dutytrail.frontend.api.entity.Trail> trails = (ArrayList<com.dutytrail.frontend.api.entity.Trail>) duty.getTrails();
        Assert.assertNotNull(trails);
        com.dutytrail.frontend.api.entity.Trail trail = trails.get(0);
        Assert.assertTrue(1L == trail.getUserId());
        Assert.assertEquals("done", trail.getStatus());
        Assert.assertNotNull(trail.getDate());
        ArrayList<User> users = (ArrayList<User>) duty.getSubscriptions();
        Assert.assertNotNull(users);
        User user = users.get(0);
        Assert.assertNotNull(user);
        Assert.assertTrue(1L == user.getId());
        Assert.assertEquals("firstName", user.getFirstName());
        Assert.assertEquals("lastName", user.getLastName());
        Assert.assertEquals("email", user.getEmail());
        Assert.assertEquals("passphrase", user.getPassphrase());
        Assert.assertEquals("gender", user.getGender());
    }

    @Test
    public void listDutyByUserTest(){
        ApiOutput apiOutput = this.apiService.listDutyByUser("1");
        Assert.assertNotNull(apiOutput);
    }

    @Test
    public void getDutyListTest(){
        ApiOutput apiOutput = this.apiService.getDuty("1");
        Assert.assertNotNull(apiOutput);
    }

    @Test
    public void postDutyTest(){
        new DutyInput();
        ApiOutput apiOutput = this.apiService.postDuty(new DutyInput(1L, "Some duty"));
        Assert.assertNotNull(apiOutput);
    }

    @Test
    public void postSubscribeTest(){
        ApiOutput apiOutput = this.apiService.postSubscribe("1", "1");
        Assert.assertNotNull(apiOutput);
    }

    @Test
    public void deleteDutyTest(){
        ApiOutput apiOutput = this.apiService.deleteDuty("1", "1");
        Assert.assertNotNull(apiOutput);
    }

    @Test
    public void putDutyTest(){
        ApiOutput apiOutput = this.apiService.putDuty("1", "1");
        Assert.assertNotNull(apiOutput);
    }

    @Test(expected = ApiException.class)
    public void putDutyUserNotSubscribedTest(){
        when(this.dutyClient.isSubscribed(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Boolean.FALSE);
        this.apiService.putDuty("1", "1");
    }

    @Test(expected = ApiException.class)
    public void putDutyExceptionTest(){
        when(this.dutyClient.isSubscribed(Mockito.anyLong(), Mockito.anyLong())).thenThrow(new ApiException());
        this.apiService.putDuty("1", "1");
    }

    @Test
    public void deleteDutyCascadeTest(){
        ApiOutput apiOutput = this.apiService.deleteDutyCascade("1");
        Assert.assertNotNull(apiOutput);
    }

}
