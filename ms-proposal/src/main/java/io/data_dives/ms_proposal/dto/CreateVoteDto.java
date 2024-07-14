package io.data_dives.ms_proposal.dto;

import io.data_dives.ms_proposal.model.Vote;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateVoteDto {
    private Long id;
    private String cpf;

    public CreateVoteDto(Vote vote){
        this.id = vote.getProposal().getId();
        this.cpf = vote.getCpf();
    }
}
