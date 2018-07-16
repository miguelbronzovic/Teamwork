package com.teamwork.mock.testsetup;

import java.lang.reflect.Type;


public class MockTestData extends TestRequest {

    public MockTestData(String request, Type type) {
        setMockJsonFile(request);
        setMockDataType(type);
    }
}
