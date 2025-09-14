package org.writeo.dao.model;

import org.writeo.utils.CommonConstants.CommonConstants;
import lombok.*;

import jakarta.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = CommonConstants.writoNewsTable)
public class WritoNews {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "writonews_id")
    private Long writonewsId;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Transient
    private String key;

    @PostLoad
    @PostUpdate
    public void populateKey() {
        this.key = CommonConstants.writoNewsTable + "_" + this.writonewsId;
    }
}