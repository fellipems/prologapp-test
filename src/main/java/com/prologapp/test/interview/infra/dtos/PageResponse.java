package com.prologapp.test.interview.infra.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.data.domain.Page;

import java.util.List;

@Schema(description = "Resposta paginada padrão da API")
public record PageResponse<T>(
        @Schema(description = "Conteúdo da página") List<T> content,
        @Schema(description = "Número da página atual (começa em 0)", example = "0") int page,
        @Schema(description = "Tamanho da página", example = "10") int size,
        @Schema(description = "Total de elementos encontrados", example = "157") long totalElements,
        @Schema(description = "Total de páginas disponíveis", example = "16") int totalPages,
        @Schema(description = "É a primeira página?", example = "true") boolean first,
        @Schema(description = "É a última página?", example = "false") boolean last
) {
    public static <T> PageResponse<T> from(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }
}
