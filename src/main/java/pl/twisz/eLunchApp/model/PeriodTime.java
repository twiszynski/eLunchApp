package pl.twisz.eLunchApp.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import pl.twisz.eLunchApp.validator.PeriodTimeConstraint;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Embeddable
@PeriodTimeConstraint
public class PeriodTime {

    @Nullable
    private LocalTime begin;

    @Nullable
    private LocalTime end;

    @Nullable
    public LocalTime getBegin() {
        return begin;
    }

    public void setBegin(@Nullable LocalTime begin) {
        this.begin = begin;
    }

    @Nullable
    public LocalTime getEnd() {
        return end;
    }

    public void setEnd(@Nullable LocalTime end) {
        this.end = end;
    }
}
