package com.teamwork.mock.testsetup;

import java.lang.reflect.Type;


public class TestRequest implements ITestRequest {

    private String request;
    private Type type;
    private String request2;
    private Type type2;

    @Override
    public void setMockJsonFile(String request) { this.request = request; }

    @Override
    public String getMockJsonFile() {
        return request;
    }

    @Override
    public void setMockDataType(Type type) {
        this.type = type;
    }

    @Override
    public Type getMockDataType() {
        return type;
    }

    @Override
    public void setMockJsonFile2(String request) {
        this.request2 = request;
    }

    @Override
    public String getMockJsonFile2() {
        return request2;
    }

    @Override
    public void setMockDataType2(Type type) {
        this.type2 = type;
    }

    @Override
    public Type getMockDataType2() {
        return type2;
    }
}
