package io.data_dives.ms_proposal.model;

import io.data_dives.ms_proposal.dto.CreateVoteDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "votes")
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 11)
    private String cpf;
    @ManyToOne
    private Proposal proposal;
    private boolean approved;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;

    public Vote(CreateVoteDto dto){
        this.cpf = dto.getCpf();
        this.approved = dto.isApproved();
    }

    public Vote removeId(){
        setId(null);
        return this;
    }
}
