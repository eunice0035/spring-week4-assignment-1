package com.codesoom.assignment;

import com.codesoom.assignment.controller.NotFoundErrorAdvice;
import com.codesoom.assignment.controller.ProductController;
import com.codesoom.assignment.dto.ProductDTO;
import com.codesoom.assignment.model.Product;
import com.codesoom.assignment.repository.ProductRepository;
import com.codesoom.assignment.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureWebMvc
public class ProductControllerWebTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        ProductService productService = new ProductService(productRepository);
        ProductController productController = new ProductController(productService);
        NotFoundErrorAdvice notFoundErrorAdvice = new NotFoundErrorAdvice();
        mockMvc = MockMvcBuilders.standaloneSetup(productController).setControllerAdvice(notFoundErrorAdvice).build();
    }

    @Nested
    @DisplayName("POST /tasks URL 은")
    class createProduct {
        @Test
        @DisplayName("Product 를 생성하고 상태 코드 201 을 반환한다")
        void createProduct() throws Exception {
            String source = objectMapper.writeValueAsString(new ProductDTO.CreateProduct("test name",
                    "test maker", 1000, "test imageUrl"));

            mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(source))
                    .andExpect(status().isCreated());
        }
    }

    @Nested
    @DisplayName("GET /products URL 은")
    class getProducts {
        @Test
        @DisplayName("Product List 를 반환하고 상태 코드 200 을 반환한다")
        void getProducts() throws Exception {
            String source = objectMapper.writeValueAsString(new ProductDTO.CreateProduct("test name",
                    "test maker", 1000, "test imageUrl"));

            mockMvc.perform(get("/products")).andExpect(status().isOk());
        }
    }

    @Nested
    @DisplayName("PATCH /products{id} URL 은")
    class PatchProductsWebTest {

        @Nested
        @DisplayName("ProductDTO.UpdateProduct 와 유효한 id 를 넘겨주면")
        class withValidIdTest {
            @Test
            @DisplayName("Product List 를 반환하고 상태 코드 200 을 응답한다")
            void patchProductTest() throws Exception {

                String source = objectMapper.writeValueAsString(new ProductDTO.UpdateProduct("test update name",
                        "test update maker", 1000, "test update imageUrl"));

                mockMvc.perform(patch("/products/{id}", 1).contentType(MediaType.APPLICATION_JSON)
                        .content(source)).andExpect(status().isOk()).andDo(print());
            }
        }

        @Nested
        @DisplayName("ProductDTO.UpdateProduct 와 유효하지 않은 id 를 넘겨주면")
        class withInValidIdTest {
            @Test
            @DisplayName("상태 코드 NotFound 를 응답한다.")
            void patchProductTest() throws Exception {
                String source = objectMapper.writeValueAsString(new ProductDTO.UpdateProduct("test update name",
                        "test update maker", 1000, "test update imageUrl"));

                mockMvc.perform(patch("/products/{id}", 1000).contentType(MediaType.APPLICATION_JSON)
                        .content(source)).andExpect(status().isNotFound()).andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("PATCH /products{id} URL 은")
    class putProductsWebTest {
        @Nested
        @DisplayName("ProductDTO.UpdateProduct 와 유효한 id 를 넘겨주면")
        class withValidId {
            @Test
            @DisplayName("Product List 를 반환하고 상태 코드 200 을 응답한다.")
            void putProductWithValidIdTest() throws Exception {

                String source = objectMapper.writeValueAsString(new ProductDTO.UpdateProduct("test update name",
                        "test update maker", 1000, "test update imageUrl"));

                mockMvc.perform(put("/products/{id}", 1).contentType(MediaType.APPLICATION_JSON)
                        .content(source)).andExpect(status().isOk()).andDo(print());
            }
        }

        @Nested
        @DisplayName("ProductDTO.UpdateProduct 와 유효하지 않은 id 를 넘겨주면")
        class withInValidId {
            @Test
            @DisplayName("상태 코드 NotFound 를 응답한다.")
            void putProductWithInValidIdTest() throws Exception {

                String source = objectMapper.writeValueAsString(new ProductDTO.UpdateProduct("test update name",
                        "test update maker", 1000, "test update imageUrl"));

                mockMvc.perform(put("/products/{id}", 1000).contentType(MediaType.APPLICATION_JSON)
                        .content(source)).andExpect(status().isNotFound()).andDo(print());
            }
        }
    }

    @Nested
    @DisplayName("GET /products{id} URL 은")
    class getProductsWebTest {
        @Nested
        @DisplayName("ProductDTO.UpdateProduct 와 유효한 id 를 넘겨주면")
        class WithValidIdTest {
            @Test
            @DisplayName("Product List 를 반환하고 상태 코드 200 을 응답한다.")
            void getProductWithValidIdTestTest() throws Exception {
                Product product = new Product("get test name", 1000, "get test imageUrl",
                        "get test maker");
                productRepository.save(product);

                mockMvc.perform(get("/products/{id}", 1)).andExpect(status().isOk());
            }
        }

        @Nested
        @DisplayName("ProductDTO.UpdateProduct 와 유효하지 않은 id 를 넘겨주면")
        class WithInValidIdTest {
            @Test
            @DisplayName("상태 코드 404 를 응답한다.")
            void getProductWithInValidIdTestTest() throws Exception {
                Product product = new Product("get test name", 1000, "get test imageUrl",
                        "get test maker");
                productRepository.save(product);

                mockMvc.perform(get("/products/{id}", 1000)).andExpect(status().isNotFound());
            }
        }
    }
}
