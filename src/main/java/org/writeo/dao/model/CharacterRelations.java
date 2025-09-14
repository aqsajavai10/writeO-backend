package org.writeo.dao.model;

import org.writeo.utils.CommonConstants.CommonConstants;
import lombok.*;

import jakarta.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = CommonConstants.characterRelationsTable)
public class CharacterRelations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "relation_id")
    private Long relationId;

    @Column(name = "character_id")
    private Long characterId;

    @Column(name = "relation_to_character_id")
    private Long relationToCharacterId;

    @Column(name = "relation_description")
    private String relationDescription;

    @Transient
    private String key;

    @PostLoad
    @PostUpdate
    public void populateKey() {
        this.key = CommonConstants.characterRelationsTable + "_" + this.relationId;
    }
}