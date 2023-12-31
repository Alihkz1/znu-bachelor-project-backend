package ir.znu.znuproject.dto.product;

import ir.znu.znuproject.dto.product.ProductDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ProductListDTO {
    private List<ProductDTO> products;
    private Integer rowCount;

}
