package com.example.onesignaldemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/second")
    public String getChat(){
        return "second.html";
    }

    @GetMapping("/OneSignalSDKWorker.js")
    public String getOneSignalSDKWorker(){
        return "OneSignalSDKWorker.js";
    }

    @GetMapping("/OneSignalSDKUpdaterWorker.js")
    public String getOneSignalUpdater(){
        return "OneSignalSDKUpdaterWorker.js";
    }
}
