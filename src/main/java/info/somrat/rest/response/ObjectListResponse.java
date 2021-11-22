package info.somrat.rest.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObjectListResponse extends ApiResponse{

    private List<Object> objects;

    public ObjectListResponse(boolean success, String message, List<Object> objects) {
        super(success, message);
        this.objects = objects;
    }
}
