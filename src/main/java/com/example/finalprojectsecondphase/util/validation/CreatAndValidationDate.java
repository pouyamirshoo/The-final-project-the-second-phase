package com.example.finalprojectsecondphase.util.validation;

import com.example.finalprojectsecondphase.exception.InvalidInputInformationException;
import com.example.finalprojectsecondphase.exception.WrongDateInsertException;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class CreatAndValidationDate {
    public DateTime currentTime() {
        return new DateTime(new Date(System.currentTimeMillis()));
    }

    public boolean isValidStringInputDate(String input) {
        try {
            DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-MM-dd");
            dtf.parseDateTime(input);
            return true;
        } catch (IllegalArgumentException iae) {
            return false;
        }
    }

    public DateTime insertDate(String input) {
        if (isValidStringInputDate(input)) {
            return DateTime.parse(input);
        } else
            throw new InvalidInputInformationException("invalid date format entered");
    }
}
