package az.company.gateway.mapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public enum DateMapper {
    DATE_MAPPER;

    public LocalDateTime toLocalDateTime(Instant instant) {
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zone);
        return zonedDateTime.toLocalDateTime();
    }
}
