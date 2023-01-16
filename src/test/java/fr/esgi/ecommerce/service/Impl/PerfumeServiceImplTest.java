package fr.esgi.ecommerce.service.Impl;

import fr.esgi.ecommerce.domain.Product;
import fr.esgi.ecommerce.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static fr.esgi.ecommerce.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void findProductById() {
        Product product = new Product();
        product.setId(123L);

        when(productRepository.findById(123L)).thenReturn(java.util.Optional.of(product));
        productService.findProductById(123L);
        assertEquals(123L, product.getId());
        assertNotEquals(1L, product.getId());
        verify(productRepository, times(1)).findById(123L);
    }

    @Test
    public void findAllProducts() {
        List<Product> productList = new ArrayList<>();
        productList.add(new Product());
        productList.add(new Product());

        when(productRepository.findAllByOrderByIdAsc()).thenReturn(productList);
        productService.findAllProducts();
        assertEquals(2, productList.size());
        verify(productRepository, times(1)).findAllByOrderByIdAsc();
    }

    @Test
    public void filter() {
        Product productChanel = new Product();
        productChanel.setProductr(PERFUMER_CHANEL);
        productChanel.setProductGender(PERFUME_GENDER);
        productChanel.setPrice(101);
        Product productCreed = new Product();
        productCreed.setProductr(PERFUMER_CREED);
        productCreed.setProductGender(PERFUME_GENDER);
        productCreed.setPrice(102);

        List<Product> productList = new ArrayList<>();
        productList.add(productChanel);
        productList.add(productCreed);

        List<String> productrs = new ArrayList<>();
        productrs.add(PERFUMER_CHANEL);
        productrs.add(PERFUMER_CREED);

        List<String> genders = new ArrayList<>();
        genders.add(PERFUME_GENDER);

        when(productRepository.findByProductrIn(productrs)).thenReturn(productList);
        productService.filter(productrs, new ArrayList<>(), new ArrayList<>(), false);
        assertEquals(2, productList.size());
        assertEquals(productList.get(0).getProductr(), PERFUMER_CHANEL);
        verify(productRepository, times(1)).findByProductrIn(productrs);

        when(productRepository.findByProductGenderIn(genders)).thenReturn(productList);
        productService.filter(new ArrayList<>(), genders, new ArrayList<>(), false);
        assertEquals(2, productList.size());
        verify(productRepository, times(1)).findByProductGenderIn(genders);
    }

    @Test
    public void findByProductrOrderByPriceDesc() {
        Product productChanel = new Product();
        productChanel.setProductr(PERFUMER_CHANEL);
        Product productCreed = new Product();
        productCreed.setProductr(PERFUMER_CREED);
        List<Product> productList = new ArrayList<>();
        productList.add(productChanel);
        productList.add(productCreed);

        when(productRepository.findByProductrOrderByPriceDesc(PERFUMER_CHANEL)).thenReturn(productList);
        productService.findByProductrOrderByPriceDesc(PERFUMER_CHANEL);
        assertEquals(productList.get(0).getProductr(), PERFUMER_CHANEL);
        assertNotEquals(productList.get(0).getProductr(), PERFUMER_CREED);
        verify(productRepository, times(1)).findByProductrOrderByPriceDesc(PERFUMER_CHANEL);
    }

    @Test
    public void findByProductGenderOrderByPriceDesc() {
        Product productChanel = new Product();
        productChanel.setProductGender(PERFUME_GENDER);
        List<Product> productList = new ArrayList<>();
        productList.add(productChanel);

        when(productRepository.findByProductGenderOrderByPriceDesc(PERFUME_GENDER)).thenReturn(productList);
        productService.findByProductGenderOrderByPriceDesc(PERFUME_GENDER);
        assertEquals(productList.get(0).getProductGender(), PERFUME_GENDER);
        assertNotEquals(productList.get(0).getProductGender(), "male");
        verify(productRepository, times(1)).findByProductGenderOrderByPriceDesc(PERFUME_GENDER);
    }

    @Test
    public void saveProduct() {
        MultipartFile multipartFile = new MockMultipartFile(FILE_NAME, FILE_NAME, "multipart/form-data", FILE_PATH.getBytes());
        Product product = new Product();
        product.setId(1L);
        product.setProductr(PERFUMER_CHANEL);
        product.setFilename(multipartFile.getOriginalFilename());

        when(productRepository.save(product)).thenReturn(product);
        productService.saveProduct(product, multipartFile);
        verify(productRepository, times(1)).save(product);
    }
}