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
@Table(name = CommonConstants.userAccountTable)
public class UserAccount {

    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "fname")
    private String fname;

    @Column(name = "lname")
    private String lname;

    @Nullable
    @Column(name = "alias")
    private String alias;

    @Transient
    private String key;

    @PostLoad
    @PostUpdate
    public void populateKey() {
        this.key = CommonConstants.userAccountTable + "_" + this.userId;
    }
}