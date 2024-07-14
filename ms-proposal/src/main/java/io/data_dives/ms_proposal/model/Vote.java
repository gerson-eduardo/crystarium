package io.data_dives.ms_proposal.model;

import io.data_dives.ms_proposal.dto.CreateVoteDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Vote {
    private Long id;
    private String cpf;
    private Proposal proposal;
    private boolean approved;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;

    public Vote(CreateVoteDto dto){
        this.cpf = dto.getCpf();
        this.approved = dto.getApproved();
    }

    public Vote removeId(){
        setId(null);
        return this;
    }
}
