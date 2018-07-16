package com.teamwork.mock;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import static com.teamwork.mock.MockResponses.*;

/**
 * Helper methods to read json's resource files for unit testing responses
 */
public class MockHelper<T> {

    public T getResponseFromJson(String request, Type type) {
        String jsonString = readJsonFile(MOCK_RESPONSE_PATH + request);
        T responseObject = new Gson().fromJson(jsonString, type);
        return responseObject;
    }

    public List<T> getResponseListFromJson(String request, Type listType) {
        String jsonString = readJsonFile(MOCK_RESPONSE_PATH + request);
        List<T> responseObject = new Gson().fromJson(jsonString, listType);
        return responseObject;
    }

    public String readJsonFile(String responseFile) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bufferedReader = null;

            bufferedReader = new BufferedReader(
                    new InputStreamReader(this.getClass().getResourceAsStream(responseFile)));

            String line = bufferedReader.readLine();
            while (line != null) {
                sb.append(line);
                line = bufferedReader.readLine();
            }

        } catch (IOException e) {

        }


        return sb.toString();
    }
}

