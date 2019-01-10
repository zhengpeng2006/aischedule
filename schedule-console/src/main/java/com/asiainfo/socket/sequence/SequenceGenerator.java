/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asiainfo.socket.sequence;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 产生唯一序列号
 * @author grucee
 */
public class SequenceGenerator {
    
    private SequenceGenerator(){}
    
    private static AtomicLong sequence = new AtomicLong(100);
    public static long next() {
        return sequence.incrementAndGet();
    }
    
}
