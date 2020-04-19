package com.eastnorth.example.controller;

import com.eastnorth.example.bean.ValidBean;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/val")
public class ValidateController {

    @RequestMapping(value = "/val", method= RequestMethod.POST)
    @ResponseBody
    public void val(@Valid @RequestBody ValidBean bean, BindingResult result) throws Exception {

        if(result.hasErrors()){
            //如果没有通过,跳转提示    
            Map<String, String> map = getErrors(result);
        }else{
            //继续业务逻辑    
        }
    }

    private Map<String, String> getErrors(BindingResult result) {
        Map<String, String> map = new HashMap<String, String>();
        List<FieldError> list = result.getFieldErrors();
        for (FieldError error : list) {
            System.out.println("error.getField():" + error.getField());
            System.out.println("error.getDefaultMessage():" + error.getDefaultMessage());

            map.put(error.getField(), error.getDefaultMessage());
        }
        return map;
    }
}
