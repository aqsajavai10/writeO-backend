package org.writeo.dao.model;

import jakarta.persistence.*;

@Entity
@Table(name = "mentions")
public class Mentions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mentions_id")
    private Long mentionsId;

    @ManyToOne //on character has may mentions
    //In the Mentions entity class a field of type character
    //character id column in mention is used to store a foreign key
    @JoinColumn(name = "character_id", nullable = false)
    private Characters character;

    // Constructors, getters, and setters
}
