package br.com.persistence.converter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.OffsetDateTime;

import static java.time.ZoneOffset.UTC;
import static java.util.Objects.nonNull;


@NoArgsConstructor (access = AccessLevel.PRIVATE)
public final class OffSetDateTimeConverter {

    public static OffSetDateTime toOffSetDateTime(final Timestamp value){
         return nonNull(value) ? OffsetDateTime.ofInstant(value.toInstant(), UTC) : null;

    }

    public static Timestamp toTimesTemp(final OffsetDateTime value){
        return nonNull(value) ? Timestamp.valueOf(value.atZoneSameInstant(UTC).toLocalDateTime()) : null;
    }
}