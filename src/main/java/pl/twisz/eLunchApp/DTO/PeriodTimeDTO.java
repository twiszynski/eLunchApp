package pl.twisz.eLunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.twisz.eLunchApp.validator.PeriodTimeConstraint;

import java.time.LocalTime;
@GeneratePojoBuilder
@Embeddable
@PeriodTimeConstraint
public class PeriodTimeDTO {

    public static class View {
        public interface Basic {}
    }

    @JsonView(View.Basic.class)
    @Nullable
    private LocalTime begin;

    @JsonView(View.Basic.class)
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
