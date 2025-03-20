package com.construcoes.gestao_obras.controller.dto;

import java.util.UUID;

public record WorkResponse(long workId, String name, String location, UUID userId) {
}
