package com.doc.ocr.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/friends")
public class TestController {

    public List<String> friend = new ArrayList<>();

    @GetMapping
    Iterable<String> list() {
        return friend;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    String create(@RequestParam final String name) {
        friend.add(name);
        return name;
    }
}
