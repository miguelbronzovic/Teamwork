package com.teamwork.interceptor;


import com.teamwork.data.api.interceptor.AuthInterceptor;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Unit test to ensure the Authorization Header is working
 */
public class AuthInterceptorTest {
    private MockWebServer server;

    private AuthInterceptor sut;

    @Before
    public void setUp() throws IOException {
        MockitoAnnotations.initMocks(this);

        sut = new AuthInterceptor();

        server = new MockWebServer();

        server.start();
    }

    @Test
    public void verifyNetworkInterceptorAuthorizationHeader() throws Exception {
        server.enqueue(new MockResponse().setBody("asdfasdf").setResponseCode(200));

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addNetworkInterceptor(sut)
                .build();

        Request request = new Request.Builder()
                .url(server.url("/"))
                .build();

        okHttpClient.newCall(request).execute();

        RecordedRequest recordedRequest = server.takeRequest();

        // No extra headers in the application's request.
        assertNotNull(recordedRequest.getHeader("Authorization"));
        assertEquals("Basic dHdwX2s5ZWpQODhMY3VvakhqbUZrVUZ1WUlVTllhbGc6WA==", recordedRequest.getHeader("Authorization"));
    }
}
