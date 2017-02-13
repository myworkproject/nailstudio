package com.projects.salon.service;

public interface EventService {
    boolean checksEventIsFreeFor(String date, String time, int employeeId);

    void save(int clientId, String title, String start);
}
