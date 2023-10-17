package com.minji.underground.slack;


public interface SlackService {
    void sendMessage(String message);
    String responseAnything(String text, String userId);
}
