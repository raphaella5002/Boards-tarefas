package br.com.dto;

import java.time.OffsetDateTime;

public record CardDetailsDTO(Long id,
                             String title,
                             String description,
                             boolean blocked,
                             OffsetDateTime blockedAt,
                             String blocksReason,
                             int blocksAmount,
                             Long columnId,
                             String columnName) {
}
