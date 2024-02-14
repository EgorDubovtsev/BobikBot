package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.commands.Command;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Stage {
    private Command command;
    private Integer stageId;
}
