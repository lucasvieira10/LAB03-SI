package com.lab3.todolist.routes;

public class RoutesTask implements Routes {

    private static final String ROUTE = AGENDA_ROUTE + AGENDA_ROUTE_ID + TASK_ROUTE;

    public static final String GET_TASKS_ALL_AGENDAS_ROUTE = AGENDA_ROUTE + TASK_ROUTE;
    public static final String GET_ALL_ROUTE = ROUTE;
    public static final String POST_ROUTE = ROUTE;
    public static final String PUT_ROUTE = ROUTE + TASK_ROUTE_ID;
    public static final String DELETE_ROUTE = ROUTE + TASK_ROUTE_ID;
    public static final String DELETE_ALL_ROUTE = ROUTE;
}
