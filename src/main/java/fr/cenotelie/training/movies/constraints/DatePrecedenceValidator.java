package fr.cenotelie.training.movies.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.springframework.beans.BeanWrapperImpl;

public class DatePrecedenceValidator implements ConstraintValidator<DatePrecedenceConstraint, Object> {
    private String before;
    private String after;
    private boolean strict = true;

    @Override
    public void initialize(final DatePrecedenceConstraint constraint) {
        before = constraint.before();
        after = constraint.after();
        strict = constraint.strict();
    }

    @Override
    public boolean isValid(final Object o, final ConstraintValidatorContext constraintValidatorContext) {

        final Object first = new BeanWrapperImpl(o).getPropertyValue(before);
        final Object second = new BeanWrapperImpl(o).getPropertyValue(after);
        final Instant fi;
        final Instant si;
        if (first instanceof LocalDate localDate)
            fi = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        else if (first instanceof LocalDateTime localDateTime)
            fi = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        else throw new RuntimeException(("expecting date or datetime for comparison"));
        if (second instanceof LocalDate secondLocalDate)
            si = secondLocalDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        else if (second instanceof LocalDateTime secondLocalDateTime)
            si = secondLocalDateTime.atZone(ZoneId.systemDefault()).toInstant();
        else throw new RuntimeException(("expecting date or datetime for comparison"));
        if (strict) return fi.isBefore(si);
        else return fi.isBefore(si) || fi == si;
    }

}
