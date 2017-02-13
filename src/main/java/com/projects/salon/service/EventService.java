package com.projects.salon.service;

public interface EventService {
    boolean checksEventIsFreeFor(String date, String time, int clientId);

    void save(int clientId, String title, String start);
}
