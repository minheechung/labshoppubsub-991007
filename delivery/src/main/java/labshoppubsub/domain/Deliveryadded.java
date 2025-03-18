package labshoppubsub.domain;

import java.time.LocalDate;
import java.util.*;
import labshoppubsub.domain.*;
import labshoppubsub.infra.AbstractEvent;
import lombok.*;

//<<< DDD / Domain Event
@Data
@ToString
public class Deliveryadded extends AbstractEvent {

    private Long id;
    private String orderId;
    private String productId;
    private String qty;
    private String amount;

    public Deliveryadded(Delivery aggregate) {
        super(aggregate);
    }

    public Deliveryadded() {
        super();
    }
}
//>>> DDD / Domain Event
