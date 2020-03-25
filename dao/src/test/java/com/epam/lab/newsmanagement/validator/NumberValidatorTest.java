package com.epam.lab.newsmanagement.validator;

import com.epam.lab.newsmanagement.config.DaoTestConfig;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {DaoTestConfig.class})
public class NumberValidatorTest {
    @Autowired
    private NumberValidator validator;

    @Test
    @Ignore
    public void validateRightNumber() {
        String number = "2345";
        boolean result = validator.validate(number);
        Assert.assertTrue(result);
    }

    @Test
    @Ignore
    public void validateWrongNumber() {
        String number = "2345a";
        boolean result = validator.validate(number);
        Assert.assertFalse(result);
    }
}