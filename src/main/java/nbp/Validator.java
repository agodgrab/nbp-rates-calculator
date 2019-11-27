package nbp;

import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class Validator {

    private String[] args;

    public Validator(String[] args) {
        this.args = args;
    }

    public void validate() {
        validateCurrency(args[0]);
        validateDates(args[1], args[2]);
    }

    private void validateCurrency(String input) {
        try {
            CurrencyCode code = CurrencyCode.valueOf(input);
            Request.INSTANCE.setCurrency(code.name());
        } catch (Exception e) {
            System.out.println("Wrong currency code. Codes available: " + Arrays.toString(CurrencyCode.values()));
            System.exit(1);
        }
    }

    private void validateDates(String startInput, String endInput) {
        try {
            singleDateValidator(startInput);
            singleDateValidator(endInput);
            startBeforeEndValidator(startInput, endInput);
            Request.INSTANCE.setStart(startInput);
            Request.INSTANCE.setEnd(endInput);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
        }
    }

    private void singleDateValidator(String input) throws WrongInputFormat {
        checkFormat(input);
        checkRange(input);
    }

    private void startBeforeEndValidator(String startInput, String endInput) throws WrongInputFormat {
        int start = Integer.parseInt(startInput.substring(0, 4));
        int end = Integer.parseInt(endInput.substring(0, 4));
        if (end < start) {
            throw new WrongInputFormat("Wrong date range: start date after end date!");
        }
    }

    private void checkFormat(String input) throws WrongInputFormat {
        try {
            LocalDate.parse(input, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (Exception e) {
            throw new WrongInputFormat("Wrong date format. The proper format is yyyyMMdd.");
        }
    }

    private void checkRange(String input) throws WrongInputFormat {
        int year = Integer.parseInt(input.substring(0, 4));
        if (year < 2002 || year > Year.now().getValue()) {
            throw new WrongInputFormat("Wrong date range. Data available from 2002 year till current year.");
        }
    }

    private enum CurrencyCode {
        USD, EUR, CHF, GBP
    }

    private class WrongInputFormat extends Exception {
        public WrongInputFormat(String message) {
            super(message);
        }
    }
}
