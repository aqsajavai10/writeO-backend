package org.writeo.dao.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.writeo.utils.CommonConstants.CommonConstants;
import lombok.*;
import org.springframework.lang.Nullable;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = CommonConstants.novelsTable)
public class Novels {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "novel_id")
    private Long novelId;

    @Column(name = "novel_name")
    private String novelName;

    @Nullable
    @Column(name = "novel_description", columnDefinition = "TEXT")
    private String novelDescription;

    @Nullable
    @Column(name = "total_volumes")
    private Double totalVolumes;

    @Nullable
    @Column(name = "title_pic", columnDefinition = "TEXT")
    private String titlePic;

    @Nullable
    @Column(name = "genre", columnDefinition = "TEXT")
    private String genre;

    @Nullable
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    private Date startDate;

    @Nullable
    @Column(name = "end_date")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Nullable
    @Column(name = "completion_status")
    private Character completionStatus;

    @Column(name = "author_id")
    private Long authorId;

    @Transient
    private String key;

    @PostLoad
    @PostUpdate
    public void populateKey() {
        this.key = CommonConstants.novelsTable + "_" + this.novelId;
    }
}