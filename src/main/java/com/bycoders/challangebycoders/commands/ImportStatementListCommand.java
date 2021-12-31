package com.bycoders.challangebycoders.commands;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImportStatementListCommand {

    @NotEmpty
    List<String> statements;
}
