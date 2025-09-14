package org.writeo.dao.model;

import org.writeo.utils.CommonConstants.CommonConstants;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = CommonConstants.notesTable)
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "note_id")
    private Long noteId;

    @Column(name = "chapter_id")
    private Long chapterId;

    @Nullable
    @Column(name = "data", columnDefinition = "TEXT")
    private String data;

    @Column(name = "type")
    private String type;

    @Column(name = "creation_date")
    private String creationDate;

    @Column(name = "author_id")
    private String authorId;

    @Column(name = "last_updated_at")
    @Temporal(TemporalType.DATE)
    private Date lastUpdatedAt;

    @Transient
    private String key;

    @PostLoad
    @PostUpdate
    public void populateKey() {
        this.key = CommonConstants.notesTable + "_" + this.noteId;
    }
}