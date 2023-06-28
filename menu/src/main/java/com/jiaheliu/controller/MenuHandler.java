package com.jiaheliu.controller;

import com.jiaheliu.repository.MenuRepository;
import com.jiaheliu.entity.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuHandler {
    @Value("${server.port}")
    private String port;

    @Autowired
    private MenuRepository menuRepository;

    @GetMapping("/index")
    public String index(){
        return this.port;
    }

    @GetMapping("/findAll/{index}/{limit}")
    public List<Menu> findAll(@PathVariable int index, @PathVariable int limit){
        return menuRepository.findAll(index, limit);
    }
}
