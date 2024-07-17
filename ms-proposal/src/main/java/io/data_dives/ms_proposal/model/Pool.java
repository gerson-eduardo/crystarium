package io.data_dives.ms_proposal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "pools")
public class Pool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Proposal proposal;
    @Column(name = "start_at", nullable = false)
    private ZonedDateTime start;
    @Column(name = "end_at", nullable = false)
    private ZonedDateTime end;
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime created;
    @Column(name = "modified_at", nullable = false)
    private ZonedDateTime modified;
    @Column(name = "is_open")
    private boolean open;

    public Pool removeId(){
        this.setId(null);
        return this;
    }
}
