package hello.itemservice.domain.item;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    // 동시 여러 쓰레드 접근 시 ConcurrentMap을 사용해야함
    private static final Map<Long, Item> store = new HashMap<>(); // static
    // 여기도 마찬가지로 동시 접근 시 long이 아닌 AtomicLong 같은 것을 사용해야함.
    private static long sequence = 0L; // static

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }
    
    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);

        // 정석으로 한다면 itemParamDTO를 만들어서 하는게 좋다.
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }
}
