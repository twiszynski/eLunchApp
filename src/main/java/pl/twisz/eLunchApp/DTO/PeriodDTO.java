package pl.twisz.eLunchApp.DTO;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import pl.twisz.eLunchApp.validator.PeriodConstraint;

import java.time.LocalDateTime;

@Embeddable
@PeriodConstraint
public class PeriodDTO {

    @Nullable
    private LocalDateTime begin;

    @Nullable
    private LocalDateTime end;

    @Nullable
    public LocalDateTime getBegin() {
        return begin;
    }

    public void setBegin(@Nullable LocalDateTime begin) {
        this.begin = begin;
    }

    @Nullable
    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(@Nullable LocalDateTime end) {
        this.end = end;
    }
}
