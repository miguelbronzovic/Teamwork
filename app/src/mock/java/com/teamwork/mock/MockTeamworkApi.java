package com.teamwork.mock;

import com.teamwork.BuildConfig;
import com.teamwork.data.api.TeamworkApiService;
import com.teamwork.data.model.Projects;
import com.teamwork.data.model.Tasks;
import com.teamwork.mock.testsetup.ITestRequest;
import com.teamwork.mock.testsetup.MockTestData;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

import static com.teamwork.mock.MockResponses.*;

/**
 * Mocked Teamwork APi Service implementation for testing
 * @param <T>
 */
public class MockTeamworkApi<T> implements TeamworkApiService {

    private ITestRequest testRequest;

    private BehaviorDelegate<TeamworkApiService> delegate;
    private MockInterceptor interceptor;

    public MockTeamworkApi() {
        this(null);
    }

    public MockTeamworkApi(ITestRequest testRequest) {
        this.testRequest = testRequest;

        interceptor = new MockInterceptor();

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        // TODO: set up realistic tests and behavior when we have proper error handling
        NetworkBehavior behavior = NetworkBehavior.create();
        behavior.setDelay(200, TimeUnit.MILLISECONDS);
        behavior.setFailurePercent(0);
        behavior.setVariancePercent(0);

        MockRetrofit mockRetrofit = new MockRetrofit.Builder(retrofit)
                .networkBehavior(behavior).build();

        delegate = mockRetrofit.create(TeamworkApiService.class);
    }

    public T createResponse() {
        return (T) new MockHelper().getResponseFromJson(testRequest.getMockJsonFile(), testRequest.getMockDataType());
    }

    @NotNull
    @Override
    public Single<Projects> getProjects() {
        if (testRequest == null) {
            testRequest = new MockTestData(GOOD_PROJECT_LIST, PROJECTS_TYPE);
        }
        return delegate.returningResponse(createResponse()).getProjects();
    }

    @NotNull
    @Override
    public Single<Tasks> getTasks(@NotNull String projectId) {
        if (testRequest == null) {
            testRequest = new MockTestData(GOOD_TASK_LIST, TASKS_TYPE);
        }
        return delegate.returningResponse(createResponse()).getTasks(projectId);

    }
}
