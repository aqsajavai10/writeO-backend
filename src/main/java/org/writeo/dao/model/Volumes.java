package org.writeo.dao.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.lang.Nullable;
import org.writeo.utils.CommonConstants.CommonConstants;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = CommonConstants.volumesTable)
public class Volumes {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "volume_id")
    private Long volumeId;

    @Column(name = "novel_id")
    private Long novelId;

    @Column(name = "volume_number")
    private Double volumeNumber;

    @Nullable
    @Column(name = "starting_chapter")
    private Double startingChapter;

    @Nullable
    @Column(name = "ending_chapter")
    private Double endingChapter;

    @Column(name = "volume_name")
    private String volumeName;

    @Transient
    private String key;

    @PostLoad
    @PostUpdate
    public void populateKey() {
        this.key = CommonConstants.volumesTable + "_" + this.volumeId;
    }
}