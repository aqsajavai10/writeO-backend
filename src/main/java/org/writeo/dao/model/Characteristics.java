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
@Table(name = CommonConstants.characteristicsTable)
public class Characteristics {

    @Id
    @Column(name = "character_id")
    private Long characterId;

    @Column(name = "objective")
    private String objective;

    @Column(name = "level")
    private Integer level;

    @Column(name = "age")
    private Integer age;

    @Column(name = "race")
    private String race;

    @Column(name = "energy")
    private Integer energy;

    @Column(name = "type")
    private String type;

    @Column(name = "reputation")
    private String reputation;

    @Transient
    private String key;

    @PostLoad
    @PostUpdate
    public void populateKey() {
        this.key = CommonConstants.characteristicsTable + "_" + this.characterId;
    }
}