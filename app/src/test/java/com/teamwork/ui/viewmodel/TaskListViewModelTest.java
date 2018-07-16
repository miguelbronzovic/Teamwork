package com.teamwork.ui.viewmodel;

import com.teamwork.data.model.Task;
import com.teamwork.data.model.Tasks;
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

import static com.teamwork.mock.MockResponses.GOOD_TASK_LIST;
import static com.teamwork.mock.MockResponses.TASKS_TYPE;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Unit testing TaskListViewModel
 */
public class TaskListViewModelTest extends TestRequest {

    private final static String PROJECT_ID = "457090";

    MockTeamworkApi project1819Api;

    TaskListViewModel sut;

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
        setMockDataType(TASKS_TYPE);

        project1819Api = new MockTeamworkApi(this);

        sut = new TaskListViewModel(project1819Api);
    }

    @Test
    public void fetchProjectList() throws Exception {
        setMockJsonFile(GOOD_TASK_LIST);

        Tasks apiResponse = (Tasks) project1819Api.createResponse();
        List<Task> expectedResponse = apiResponse.getTaskItems();

        TestObserver<List<Task>> taskListSubjectObserver = sut.getTaskListSubject().test();

        sut.fetchTasks(PROJECT_ID);

        taskListSubjectObserver.assertNoErrors();
        taskListSubjectObserver.assertValueCount(1);

        List<Task> actualResponse = taskListSubjectObserver.values().get(0);

        assertEquals(expectedResponse.size(), actualResponse.size());

        assertNotNull(actualResponse.get(0).getId());
        assertNotNull(actualResponse.get(0).getDescription());
        assertNotNull(actualResponse.get(0).getAvatarUrl());
        assertNotNull(actualResponse.get(0).getContent());

        assertEquals("17549754", actualResponse.get(0).getId());
        assertEquals("", actualResponse.get(0).getDescription());
        assertEquals("https://s3.amazonaws.com/TWFiles/349705/userAvatar/tf_C21F3016-CD19-C60F-E82B23120C506FEE.Tac_the_Psychotic_Cat.jpg", actualResponse.get(0).getAvatarUrl());
        assertEquals("Be hired by Teamwork.com", actualResponse.get(0).getContent());

        assertNotNull(actualResponse.get(1).getId());
        assertNotNull(actualResponse.get(1).getDescription());
        assertNotNull(actualResponse.get(1).getAvatarUrl());
        assertNotNull(actualResponse.get(1).getContent());

        assertEquals("17549756", actualResponse.get(1).getId());
        assertEquals("", actualResponse.get(1).getDescription());
        assertEquals("https://s3.amazonaws.com/TWFiles/349705/userAvatar/tf_C21F3016-CD19-C60F-E82B23120C506FEE.Tac_the_Psychotic_Cat.jpg", actualResponse.get(1).getAvatarUrl());
        assertEquals("Move to Ireland", actualResponse.get(1).getContent());
    }
}
