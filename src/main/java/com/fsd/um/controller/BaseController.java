package com.fsd.um.controller;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fsd.um.exception.CustomException;
import com.google.code.kaptcha.Constants;


public class BaseController {
	
    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    public void checkCaptcha(String captcha, HttpSession session) {
        String expect = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

        logger.debug("expected captcha: {}, actual: {}", expect, captcha);

        if (expect == null || !captcha.equalsIgnoreCase(expect)) {
            throw new CustomException("Invalid captcha code.");
        }
    }

}