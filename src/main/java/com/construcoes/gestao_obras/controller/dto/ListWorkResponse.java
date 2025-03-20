package com.construcoes.gestao_obras.controller.dto;

import java.util.UUID;

public record ListWorkResponse(long workId, String name, String location, UUID userId) {
}
