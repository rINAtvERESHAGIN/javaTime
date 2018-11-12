package com.javaPeople.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawEventDto {

    private Long id;
    private String eventDate;
    private String comment;
    private String name;
}
