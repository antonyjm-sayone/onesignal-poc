package com.example.onesignaldemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@RestController
public class ApiController {

    public static final String REST_API_KEY = "NGUyYjdhYjUtNjk5Zi00YTE0LWE2MTUtZTI5N2MzZTZhYWJm";
    public static final String APP_ID = "afd85f19-fa3a-4ced-a81d-dc715e62cd03";

    @PostMapping("/send")
    public ResponseEntity sendMessageToAllUsersApi(@RequestBody MessageParams message)
    {
        sendMessageToAllUsers(message.getMessage());
        return new ResponseEntity(HttpStatus.OK);
    }

    public static void sendMessageToAllUsers(String message) {
        try {
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization",
                    "Basic "+REST_API_KEY);//REST API
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    +   "\"app_id\": \""+ APP_ID +"\","
                    +   "\"included_segments\": [\"All\"],"
                    +   "\"data\": {\"foo\": \"bar\"},"
                    +   "\"contents\": {\"en\": \""+ message +"\"}"
                    + "}";


            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            jsonResponse = mountResponseRequest(con, httpResponse);
            System.out.println("jsonResponse:\n" + jsonResponse);

        } catch(Throwable t) {
            t.printStackTrace();
        }
    }

    private static String mountResponseRequest(HttpURLConnection con, int httpResponse) throws IOException {
        String jsonResponse;
        if (  httpResponse >= HttpURLConnection.HTTP_OK
                && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
            Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        else {
            Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
            jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
            scanner.close();
        }
        return jsonResponse;
    }
}
