package com.construcoes.gestao_obras.controller.dto;

import java.util.UUID;

public record CreateWorkResponse(long workId, String name, String location, UUID userId) {
}
