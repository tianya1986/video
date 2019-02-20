package paging;

import java.util.List;

/**
 * 查询结果
 */
public class Result<T> {

    public Result(long count, List<T> items) {
        this.count = count;
        this.items = items;
    }

    private long count;

    private List<T> items;

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

}
