package com.ise.demo.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NumLetterValidator {
    private static final String pattern ="^[0-9a-zA-Z]+$";
    //使用正则表达式做数据格式验证
    public boolean judge(String line,int length){
        log.info("NumLetterValidator: {},{}",length,line);
        boolean result = line.length() < length && line.matches(pattern);
        log.info("Validate Result   : {}",result);
        return result;
    }

}
