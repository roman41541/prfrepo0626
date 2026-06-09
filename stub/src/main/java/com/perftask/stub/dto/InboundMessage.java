package com.perftask.stub.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record InboundMessage(
        @JsonProperty("msg_id") String msgId,
        @JsonProperty("full_name") String fullName,
        String inn
) {
}
