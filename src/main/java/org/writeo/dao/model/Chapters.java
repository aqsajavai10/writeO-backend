package org.writeo.dao.model;

import org.writeo.utils.CommonConstants.CommonConstants;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = CommonConstants.chaptersTable)
public class Chapters {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ch_id")
    private Long chId;

    @Column(name = "novel_id")
    private Long novelId;

    @Column(name = "chapter_name")
    private String chapterName;

    @Nullable
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "word_count")
    private Integer wordCount;

    @Transient
    private String key;

    @PostLoad
    @PostUpdate
    public void populateKey() {
        this.key = CommonConstants.chaptersTable + "_" + this.chId;
    }

    @Column(name = "word_count")
    public void updateWordCount() {
        if (this.content != null && !this.content.trim().isEmpty()) {
            String[] words = this.content.trim().split("\\s+");
            this.wordCount = words.length;
        } else {
            this.wordCount = 0;
        }
    }
}
