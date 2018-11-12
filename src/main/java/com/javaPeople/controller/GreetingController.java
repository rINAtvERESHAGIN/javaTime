package com.javaPeople.controller;


import com.javaPeople.domain.CircleResource;
import com.javaPeople.repository.CircleResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {

    @Autowired
    private CircleResourceRepository resourceRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "greeting";
    }

    @GetMapping(value = "/resources")
    public String main (Map<String,Object> model){
        Iterable<CircleResource> resources =  resourceRepository.findAll();
        model.put("resources", resources);
        return "resources-page";
    }

    @PostMapping(value = "/resources")
    public String add(@RequestParam String name, Map<String,Object> model){

        CircleResource resource =  CircleResource.builder()
                .name(name)
                .build();

        resourceRepository.save(resource);

        Iterable<CircleResource> resources = resourceRepository.findAll();
        model.put("resources",resources);

        return "resources-page";
    }

}
