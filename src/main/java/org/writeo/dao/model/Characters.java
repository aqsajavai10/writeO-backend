package org.writeo.dao.model;

import org.writeo.utils.CommonConstants.CommonConstants;
import lombok.*;
import org.springframework.lang.Nullable;

import jakarta.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = CommonConstants.charactersTable)
public class Characters {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "character_id")
    private Long characterId;

    @Column(name = "character_name")
    private String characterName;

    @Nullable
    @Column(name = "character_description", columnDefinition = "TEXT")
    private String characterDescription;

    @Column(name = "mentions")
    private Long mentions;

    @Column(name = "mentions_id")
    private Long mentionsId;

    @Transient
    private String key;

    @PostLoad
    @PostUpdate
    public void populateKey() {
        this.key = CommonConstants.charactersTable + "_" + this.characterId;
    }
}