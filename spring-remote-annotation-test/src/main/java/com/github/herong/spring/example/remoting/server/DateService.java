package com.github.herong.spring.example.remoting.server;

import java.util.Date;

import com.github.herong.spring.remoting.Remote;

@Remote
public interface DateService {
    public Date getDate();
}
