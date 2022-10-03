package uz.pdp.appsecurityfirst.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.appsecurityfirst.entity.Product;
import uz.pdp.appsecurityfirst.repository.ProductRepository;

import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    ProductRepository productRepository;

    //    @PreAuthorize("hasAnyRole('MANAGER', 'DIRECTOR')")
    @PreAuthorize("hasAuthority('READ_ALL_PRODUCT')")
    @GetMapping
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok(productRepository.findAll());
    }

    //    @PreAuthorize("hasAnyRole('MANAGER', 'DIRECTOR', 'WORKER')")
    @PreAuthorize("hasAuthority('READ_ONE_PRODUCT')")
    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        return ResponseEntity.status(optionalProduct.isPresent() ? 202 : 404).body(optionalProduct.orElse(null));
    }

    //    @PreAuthorize("hasRole('DIRECTOR')")
    @PreAuthorize("hasAuthority('ADD_PRODUCT')")
    @PostMapping
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productRepository.save(product));
    }

    //    @PreAuthorize("hasRole('DIRECTOR')")
    @PreAuthorize("hasAuthority('EDIT_PRODUCT')")
    @PutMapping("/{id}")
    public ResponseEntity<?> editProduct(@PathVariable Integer id, @RequestBody Product product) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) return ResponseEntity.notFound().build();
        Product editing = optionalProduct.get();
        editing.setName(product.getName());
        Product save = productRepository.save(editing);
        return ResponseEntity.ok(save);
    }

    //    @PreAuthorize("hasRole('DIRECTOR')")
    @PreAuthorize("hasAuthority('DELETE_PRODUCT')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        productRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
