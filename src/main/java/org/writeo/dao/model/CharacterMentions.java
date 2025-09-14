package org.writeo.dao.model;

import org.writeo.utils.CommonConstants.CommonConstants;
import lombok.*;

import jakarta.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = CommonConstants.characterMentionsTable)
public class CharacterMentions {

    @Id
    @Column(name = "character_id")
    private Long characterId;

    @Column(name = "mention_id")
    private Long mentionId;

    @Column(name = "chapter_id")
    private Long chapterId;

    @Transient
    private String key;

    @PostLoad
    @PostUpdate
    public void populateKey() {
        this.key = CommonConstants.characterMentionsTable + "_" + this.characterId + "_" + this.mentionId + "_" + this.chapterId;
    }
}