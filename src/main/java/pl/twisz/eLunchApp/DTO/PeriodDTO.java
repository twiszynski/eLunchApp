package pl.twisz.eLunchApp.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import net.karneim.pojobuilder.GeneratePojoBuilder;
import pl.twisz.eLunchApp.validator.PeriodConstraint;

import java.time.LocalDateTime;
@GeneratePojoBuilder
@Embeddable
@PeriodConstraint
public class PeriodDTO {

    public static class View {
        public interface Basic {}
    }

    @JsonView(View.Basic.class)
    @Nullable
    private LocalDateTime begin;

    @JsonView(View.Basic.class)
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
