package labshoppubsub.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import labshoppubsub.config.kafka.KafkaProcessor;
import labshoppubsub.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    private InventoryRepository inventoryRepository; // ✅ 변수명 올바르게 선언

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderPlaced'"
    )
    public void wheneverOrderPlaced_DecreaseStock(@Payload OrderPlaced orderPlaced) { // ✅ 인자 위치 수정
        System.out.println("\n\n##### listener DecreaseStock : " + orderPlaced + "\n\n");

        // ✅ 올바르게 repository 사용
        inventoryRepository.findById(Long.valueOf(orderPlaced.getProductId()))
            .ifPresent(inventory -> {
                inventory.setStock(inventory.getStock() - orderPlaced.getQty());
                inventoryRepository.save(inventory);
            });

        // ✅ Inventory.decreaseStock 제거 (올바른 사용법을 알 수 없으므로 주석 처리)
        // Inventory.decreaseStock(orderPlaced);
    }
}