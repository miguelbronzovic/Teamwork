package com.teamwork.mock;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Protocol;
import okhttp3.Response;

/**
 * Mock Interceptor to use on MockTeamworkApi
 */
public class MockInterceptor implements Interceptor {

    private int code;

    @Override
    public Response intercept(Chain chain) throws IOException {

        return new Response.Builder()
                .code(code)
                .protocol(Protocol.HTTP_1_0)
                .addHeader("content-type", "application/json")
                .build();
    }

    public void setCode(int code) {
        this.code = code;
    }
}
