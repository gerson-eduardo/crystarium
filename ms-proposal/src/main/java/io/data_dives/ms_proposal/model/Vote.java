package io.data_dives.ms_proposal.model;

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
    private Boolean approved;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;
}
