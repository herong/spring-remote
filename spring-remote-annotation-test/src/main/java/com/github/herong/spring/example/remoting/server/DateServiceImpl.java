package com.github.herong.spring.example.remoting.server;

import java.util.Date;

import org.springframework.stereotype.Service;

@Service
public class DateServiceImpl implements DateService {

    public Date getDate() {
        return new Date();
    }
}
