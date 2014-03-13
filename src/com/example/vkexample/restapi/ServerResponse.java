package com.example.vkexample;

public class ServerResponse {
    public int httpCode;
    public String response;

    public ServerResponse(int code, String res) {
	httpCode = code;
	response = res;
    }
}
