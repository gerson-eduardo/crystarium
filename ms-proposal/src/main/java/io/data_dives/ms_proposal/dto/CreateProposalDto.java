package io.data_dives.ms_proposal.dto;

import io.data_dives.ms_proposal.model.Proposal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateProposalDto {
    private String title;
    private String description;
    private String cpf;

    public CreateProposalDto(Proposal proposal){
        this.title = proposal.getTitle();
        this.description = proposal.getDescription();
        this.cpf = proposal.getCpf();
    }
}
