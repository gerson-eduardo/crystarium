package io.data_dives.ms_proposal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Proposal {
    private Long id;
    private String title;
    private String description;
    private boolean appoved;
    private String cpf;
    private ZonedDateTime createdAt;
    private ZonedDateTime modifiedAt;
}
