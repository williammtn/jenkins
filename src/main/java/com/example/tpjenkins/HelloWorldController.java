package com.example.tpjenkins;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/helloworld")
public class HelloWorldController {

    @Operation(summary = "Returns 'Hello World'")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping
    public String helloWorld() {
        return "Hello World";
    }
}
