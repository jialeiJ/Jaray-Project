package com.vienna.jaray.controller;

import com.vienna.jaray.common.ResponseResultMsg;
import com.vienna.jaray.service.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/system")
public class SystemController {
    @Autowired
    private SystemService systemService;

    @PostMapping("/findLeftNav")
    public ResponseResultMsg findLeftNav(){
        return systemService.findLeftNav();
    }

}
