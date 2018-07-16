package com.teamwork.ui.viewmodel;

import com.teamwork.data.model.Project;
import com.teamwork.data.model.Projects;
import com.teamwork.mock.MockTeamworkApi;
import com.teamwork.mock.testsetup.TestRequest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.functions.Function;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static com.teamwork.mock.MockResponses.GOOD_PROJECT_LIST;
import static com.teamwork.mock.MockResponses.PROJECTS_TYPE;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Unit testing ProjectListViewModel
 */
public class ProjectListViewModelTest extends TestRequest {

    MockTeamworkApi project1819Api;

    ProjectListViewModel sut;

    @BeforeClass
    public static void beforeClass() throws Exception {
        RxJavaPlugins.setIoSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });

        RxJavaPlugins.setComputationSchedulerHandler(new Function<Scheduler, Scheduler>() {
            @Override
            public Scheduler apply(Scheduler scheduler) throws Exception {
                return Schedulers.trampoline();
            }
        });

        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.trampoline();
            }
        });
    }

    @Before
    public void setUp() throws Exception {
        setMockDataType(PROJECTS_TYPE);

        project1819Api = new MockTeamworkApi(this);

        sut = new ProjectListViewModel(project1819Api);
    }

    @Test
    public void fetchProjectList() throws Exception {
        setMockJsonFile(GOOD_PROJECT_LIST);

        Projects apiResponse = (Projects) project1819Api.createResponse();
        List<Project> expectedResponse = apiResponse.getProjects();

        TestObserver<List<Project>> taskListSubjectObserver = sut.getProjectListSubject().test();

        sut.fetchProjects();

        taskListSubjectObserver.assertNoErrors();
        taskListSubjectObserver.assertValueCount(1);

        List<Project> actualResponse = taskListSubjectObserver.values().get(0);

        assertEquals(expectedResponse.size(), actualResponse.size());

        assertNotNull(actualResponse.get(0).getId());
        assertNotNull(actualResponse.get(0).getDescription());
        assertNotNull(actualResponse.get(0).getLogo());
        assertNotNull(actualResponse.get(0).getName());
        assertNotNull(actualResponse.get(0).getStatus());

        assertEquals("457090", actualResponse.get(0).getId());
        assertEquals("Brazil", actualResponse.get(0).getName());
        assertEquals("https://s3.amazonaws.com/TWFiles/349705/projectLogo/tf_929A76DB-AA0C-B885-B716E9094F4683F5.animated-brazil-flag-brazilian-national-banner_h2nf-ru_F0000.png", actualResponse.get(0).getLogo());
        assertEquals("active", actualResponse.get(0).getStatus());

        assertNotNull(actualResponse.get(1).getId());
        assertNotNull(actualResponse.get(1).getDescription());
        assertNotNull(actualResponse.get(1).getLogo());
        assertNotNull(actualResponse.get(1).getName());
        assertNotNull(actualResponse.get(1).getStatus());

        assertEquals("462923", actualResponse.get(1).getId());
        assertEquals("Time Machine R&D", actualResponse.get(1).getName());
        assertEquals("https://s3.amazonaws.com/TWFiles/349705/projectLogo/tf_2E2AD316-DAF2-C47C-BA4D4BCE9184E395.Fg204.jpg", actualResponse.get(1).getLogo());
        assertEquals("active", actualResponse.get(1).getStatus());
    }
}
