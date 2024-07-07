package io.data_dives.ms_proposal.model;

import io.data_dives.ms_proposal.dto.CreateProposalDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "proposals")
public class Proposal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 32)
    private String title;
    @Column(columnDefinition = "TEXT")
    private String description;
    private boolean approved;
    @Column(length = 11)
    private String cpf;
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;
    @Column(name = "modified_at", nullable = false)
    private ZonedDateTime modifiedAt;

    public Proposal(CreateProposalDto dto){
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.cpf = dto.getCpf();
    }
}
