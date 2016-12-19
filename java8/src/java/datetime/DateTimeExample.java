package datetime;

import java.time.*;
import java.time.temporal.TemporalAdjusters;

/**
 * Created by moriatry on 12/10/2015.
 */
public class DateTimeExample {

    public static void main(String[] args) {

        //LocalDate instances - Current Date
        LocalDate currentDate = LocalDate.now();
        LocalDate tomorrow = LocalDate.now().plusDays(1);

        // before 5 hours and 30 minutes
        LocalDateTime fiveHours = LocalDateTime.now().minusHours(5).minusMinutes(30);


        //Months value start at 1 and not 0 as in Java 7 - 11/08/1989
        LocalDate birthday = LocalDate.of(1989, Month.AUGUST, 11);
        LocalDate birthday2 = LocalDate.of(1989, 8, 11);

        //Current Time
        LocalTime currentTime = LocalTime.now();

        //LocalDateTime
        LocalDateTime dateTime = LocalDateTime.of(1989, Month.AUGUST, 11, 12, 0);

        // current (local) time in Paris
        LocalTime currentTimeInParis = LocalTime.now(ZoneId.of("Europe/Paris"));

        // information about the month
        Month birthdayMonth = birthday.getMonth(); // FEBRUARY
        int monthValue = birthdayMonth.getValue(); // 2
        int minLength = birthdayMonth.minLength(); // 28

        //Temporal adjusters
        // last day of february 2014 (2014-02-28)
        LocalDate lastDayOfMonth = birthday.with(TemporalAdjusters.lastDayOfMonth());

        //Period
        Period period = Period.between(currentDate, tomorrow);

        System.out.println("currentDate " + currentDate);
        System.out.println("currentTime " + currentTime);
        System.out.println("birthday " + birthday);
        System.out.println("birthday2 " + birthday2);
        System.out.println("dateTime " + dateTime);
        System.out.println("currentTimeInParis " + currentTimeInParis);

        System.out.println("******Information about birthday month******");
        System.out.println("birthdayMonth " + birthdayMonth);
        System.out.println("monthValue " + monthValue);
        System.out.println("minLength " + minLength);

    }
}
