package com.teamwork.mock.testsetup;

import java.lang.reflect.Type;


public interface ITestRequest {

    void setMockJsonFile(String request);

    String getMockJsonFile();

    void setMockDataType(Type type);

    Type getMockDataType();

    void setMockJsonFile2(String request);

    String getMockJsonFile2();

    void setMockDataType2(Type type);

    Type getMockDataType2();
}
