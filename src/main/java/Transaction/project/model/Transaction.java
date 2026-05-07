package Transaction.project.model;

import lombok.Data;

@Data
public class Transfer {
    private String fromId;
    private String toId;
    private Double amount;
}
