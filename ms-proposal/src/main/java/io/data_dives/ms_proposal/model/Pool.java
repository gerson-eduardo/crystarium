package io.data_dives.ms_proposal.model;

import jakarta.persistence.*;

import java.time.ZonedDateTime;

@Entity
@Table(name = "pools")
public class Pool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Proposal proposal;
    private ZonedDateTime start;
    private ZonedDateTime end;
    private ZonedDateTime created;
    private boolean open;
}
