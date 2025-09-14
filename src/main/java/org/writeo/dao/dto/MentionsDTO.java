package org.writeo.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MentionsDTO {
    private Long mentionsId;
    private Long characterId;
    private String name;
    private String description;
}
