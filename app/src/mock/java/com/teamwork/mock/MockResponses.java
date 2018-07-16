package com.teamwork.mock;

import com.google.gson.reflect.TypeToken;
import com.teamwork.data.model.Projects;
import com.teamwork.data.model.Tasks;

import java.lang.reflect.Type;

/**
 * Constants
 */
public class MockResponses {
    public static final String MOCK_RESPONSE_PATH = "/mockResponses/";

    public static final Type PROJECTS_TYPE = new TypeToken<Projects>() {}.getType();
    public static final Type TASKS_TYPE = new TypeToken<Tasks>() {}.getType();


    public static final String GOOD_PROJECT_LIST = "projects/good_project_list.json";
    public static final String GOOD_TASK_LIST = "tasks/good_task_list.json";
}