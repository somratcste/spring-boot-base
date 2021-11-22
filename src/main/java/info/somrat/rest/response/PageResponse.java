package info.somrat.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse extends ApiResponse {

    private long totalItems;
    private int totalPage;
    private int currentPage;
    private List<Object> data;

    public PageResponse(boolean success, String message, long totalItems, int totalPage, int currentPage, List<Object> data) {
        super(success, message);
        this.totalItems = totalItems;
        this.totalPage = totalPage;
        this.currentPage = currentPage;
        this.data = data;
    }
}
