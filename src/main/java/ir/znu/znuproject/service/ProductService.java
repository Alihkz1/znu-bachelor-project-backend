package ir.znu.znuproject.service;

import ir.znu.znuproject.command.ProductCreateCommand;
import ir.znu.znuproject.command.ProductEditCommand;

import ir.znu.znuproject.dto.ProductDTO;
import ir.znu.znuproject.dto.ProductDtoMapper;
import ir.znu.znuproject.dto.ProductListDto;
import ir.znu.znuproject.model.Product;
import ir.znu.znuproject.repository.ProductRepository;
import ir.znu.znuproject.shared.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductDtoMapper productDtoMapper;

    @Autowired
    public ProductService(ProductRepository productRepository, ProductDtoMapper productDtoMapper) {
        this.productRepository = productRepository;
        this.productDtoMapper = productDtoMapper;
    }

    public ResponseEntity<Response> create(ProductCreateCommand command) {
        Response response = new Response<>();
        try {
            productRepository.save(command.toEntity());
            response.setMessage("new record saved!");
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage(e.toString());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    public ResponseEntity<Response<ProductListDto>> getList() {
        Response response = new Response();
        List<ProductDTO> products = productRepository.findAll().stream().map(productDtoMapper)
                .collect(Collectors.toList());
        try {
            ProductListDto productListDto = ProductListDto.builder()
                    .products(products)
                    .rowCount(products.size())
                    .build();
            response.setData(productListDto);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("error occurred!");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    public ResponseEntity<Response> findById(Long id) {
        Response response = new Response();
        Map map = new HashMap<String, List<ProductDTO>>();
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            map.put("Product", product);
            response.setData(map);
            return ResponseEntity.ok().body(response);
        } else
            return ResponseEntity.notFound().build();
    }

    @Transactional
    public ResponseEntity<Response> editProduct(ProductEditCommand command) {
        Response response = new Response();
        Product product = productRepository.findById(command.getID())
                .orElseThrow(() -> new IllegalArgumentException("Not Found"));
        try {
            productRepository.save(command.toEntity());
            response.setMessage("product updated");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setMessage(e.getMessage());
            response.setSuccess(false);
            return ResponseEntity.internalServerError().body(response);
        }

    }

    public ResponseEntity<Response> deleteById(Long id) {
        Response response = new Response();
        try {
            productRepository.deleteById(id);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("error occurred!");
            return ResponseEntity.internalServerError().body(response);

        }
    }
}
