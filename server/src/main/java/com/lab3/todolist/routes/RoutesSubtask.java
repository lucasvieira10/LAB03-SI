package com.lab3.todolist.routes;

public class RoutesSubtask implements Routes {

    private static final String ROUTE = AGENDA_ROUTE + AGENDA_ROUTE_ID + TASK_ROUTE
            + TASK_ROUTE_ID + SUBTASK_ROUTE;

    public static final String GET_ROUTE = ROUTE;
    public static final String POST_ROUTE = ROUTE;
    public static final String PUT_ROUTE = ROUTE + SUBTASK_ROUTE_ID;
    public static final String DELETE_ROUTE = ROUTE + SUBTASK_ROUTE_ID;
}
