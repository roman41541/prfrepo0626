package com.perftask.stub.dto;

import java.util.UUID;

public record OutboundMessage(
        UUID id,
        String time
) {
}
