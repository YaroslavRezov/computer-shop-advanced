package com.example.computershop;

import com.example.computershop.repository.CartRepository;
import com.example.computershop.repository.DeviceRepository;
import com.example.computershop.repository.LaptopRepository;
import com.example.computershop.repository.PcRepository;
import com.example.computershop.repository.PrinterRepository;
import com.example.computershop.repository.ProductRepository;
import com.example.computershop.repository.UsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(
        webEnvironment = NONE,
        classes = ComputerShopApplication.class
)
@EnableAutoConfiguration(exclude = {
        org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class,
        org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration.class
})
class ComputerShopApplicationTests {

    @MockBean
    private CartRepository cartRepository;
    @MockBean
    private DeviceRepository deviceRepository;
    @MockBean
    private LaptopRepository laptopRepository;
    @MockBean
    private PcRepository pcRepository;
    @MockBean
    private PrinterRepository printerRepository;
    @MockBean
    private ProductRepository productRepository;
    @MockBean
    private UsersRepository usersRepository;

    @Test
    void contextLoads() {
    }

}
