package test;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class TestMain {
    public static void main(String[] args) {
        String birthdateString = "1990-06-18";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthdate = LocalDate.parse(birthdateString, formatter);
        LocalDate now = LocalDate.now();
        int age = Period.between(now, birthdate).getYears();
        System.out.println(age);
    }
}
