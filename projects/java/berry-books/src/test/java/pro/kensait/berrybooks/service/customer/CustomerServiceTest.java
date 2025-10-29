package pro.kensait.berrybooks.service.customer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pro.kensait.berrybooks.dao.CustomerDao;
import pro.kensait.berrybooks.entity.Customer;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;
    private String testEmail;
    private String testPassword;

    @BeforeEach
    void setUp() {
        testEmail = "test@example.com";
        testPassword = "password123";
        
        testCustomer = new Customer();
        testCustomer.setCustomerId(1);
        testCustomer.setEmail(testEmail);
        testCustomer.setPassword(testPassword);
        testCustomer.setCustomerName("繝・せ繝亥､ｪ驛・);
        testCustomer.setAddress("譚ｱ莠ｬ驛ｽ貂玖ｰｷ蛹ｺ逾槫ｮｮ蜑・-1-1");
    }

    // registerCustomer縺ｮ繝・せ繝・
    @Test
    @DisplayName("譁ｰ隕城｡ｧ螳｢縺ｮ逋ｻ骭ｲ縺梧ｭ｣蟶ｸ縺ｫ螳御ｺ・☆繧九％縺ｨ繧偵ユ繧ｹ繝医☆繧・)
    void testRegisterCustomerSuccess() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        Customer newCustomer = new Customer();
        newCustomer.setEmail("new@example.com");
        newCustomer.setPassword("newpass123");
        newCustomer.setCustomerName("譁ｰ隕丞､ｪ驛・);
        
        when(customerDao.findByEmail("new@example.com")).thenReturn(null);
        doNothing().when(customerDao).register(newCustomer);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Customer result = customerService.registerCustomer(newCustomer);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNotNull(result);
        assertEquals("new@example.com", result.getEmail());
        verify(customerDao, times(1)).findByEmail("new@example.com");
        verify(customerDao, times(1)).register(newCustomer);
    }

    @Test
    @DisplayName("驥崎､・＠縺溘Γ繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺ｧ逋ｻ骭ｲ譎ゅ↓萓句､悶′繧ｹ繝ｭ繝ｼ縺輔ｌ繧九％縺ｨ繧偵ユ繧ｹ繝医☆繧・)
    void testRegisterCustomerDuplicateEmail() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        Customer newCustomer = new Customer();
        newCustomer.setEmail(testEmail);
        newCustomer.setPassword("newpass123");
        
        when(customerDao.findByEmail(testEmail)).thenReturn(testCustomer);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ縺ｨ讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.registerCustomer(newCustomer);
        });
        assertEquals("縺薙・繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺ｯ譌｢縺ｫ逋ｻ骭ｲ縺輔ｌ縺ｦ縺・∪縺・, exception.getMessage());
        verify(customerDao, times(1)).findByEmail(testEmail);
        verify(customerDao, never()).register(any(Customer.class));
    }

    @Test
    @DisplayName("繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺系ull縺ｮ蝣ｴ蜷医〒繧ら匳骭ｲ蜃ｦ逅・′螳溯｡後＆繧後ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testRegisterCustomerNullEmail() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        Customer newCustomer = new Customer();
        newCustomer.setEmail(null);
        newCustomer.setPassword("password123");
        
        when(customerDao.findByEmail(null)).thenReturn(null);
        doNothing().when(customerDao).register(newCustomer);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Customer result = customerService.registerCustomer(newCustomer);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNotNull(result);
        verify(customerDao, times(1)).findByEmail(null);
        verify(customerDao, times(1)).register(newCustomer);
    }

    // authenticate縺ｮ繝・せ繝・
    @Test
    @DisplayName("豁｣縺励＞繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺ｨ繝代せ繝ｯ繝ｼ繝峨〒隱崎ｨｼ縺梧・蜉溘☆繧九％縺ｨ繧偵ユ繧ｹ繝医☆繧・)
    void testAuthenticateSuccess() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        when(customerDao.findByEmail(testEmail)).thenReturn(testCustomer);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Customer result = customerService.authenticate(testEmail, testPassword);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNotNull(result);
        assertEquals(testEmail, result.getEmail());
        assertEquals(testCustomer.getCustomerId(), result.getCustomerId());
        verify(customerDao, times(1)).findByEmail(testEmail);
    }

    @Test
    @DisplayName("隱､縺｣縺溘ヱ繧ｹ繝ｯ繝ｼ繝峨〒隱崎ｨｼ縺悟､ｱ謨励☆繧九％縺ｨ繧偵ユ繧ｹ繝医☆繧・)
    void testAuthenticateWrongPassword() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        when(customerDao.findByEmail(testEmail)).thenReturn(testCustomer);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Customer result = customerService.authenticate(testEmail, "wrongpassword");

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNull(result);
        verify(customerDao, times(1)).findByEmail(testEmail);
    }

    @Test
    @DisplayName("蟄伜惠縺励↑縺・Γ繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺ｧ隱崎ｨｼ縺悟､ｱ謨励☆繧九％縺ｨ繧偵ユ繧ｹ繝医☆繧・)
    void testAuthenticateUserNotFound() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        when(customerDao.findByEmail("notfound@example.com")).thenReturn(null);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Customer result = customerService.authenticate("notfound@example.com", testPassword);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNull(result);
        verify(customerDao, times(1)).findByEmail("notfound@example.com");
    }

    @Test
    @DisplayName("繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺系ull縺ｮ蝣ｴ蜷医↓隱崎ｨｼ縺悟､ｱ謨励☆繧九％縺ｨ繧偵ユ繧ｹ繝医☆繧・)
    void testAuthenticateNullEmail() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        when(customerDao.findByEmail(null)).thenReturn(null);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Customer result = customerService.authenticate(null, testPassword);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNull(result);
        verify(customerDao, times(1)).findByEmail(null);
    }

    @Test
    @DisplayName("繝代せ繝ｯ繝ｼ繝峨′null縺ｮ蝣ｴ蜷医↓隱崎ｨｼ縺悟､ｱ謨励☆繧九％縺ｨ繧偵ユ繧ｹ繝医☆繧・)
    void testAuthenticateNullPassword() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        when(customerDao.findByEmail(testEmail)).thenReturn(testCustomer);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Customer result = customerService.authenticate(testEmail, null);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNull(result);
        verify(customerDao, times(1)).findByEmail(testEmail);
    }

    @Test
    @DisplayName("遨ｺ譁・ｭ怜・縺ｮ繝代せ繝ｯ繝ｼ繝峨〒隱崎ｨｼ縺梧・蜉溘☆繧九％縺ｨ繧偵ユ繧ｹ繝医☆繧具ｼ井ｸ閾ｴ縺吶ｋ蝣ｴ蜷茨ｼ・)
    void testAuthenticateEmptyPassword() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        Customer customerWithEmptyPassword = new Customer();
        customerWithEmptyPassword.setCustomerId(1);
        customerWithEmptyPassword.setEmail(testEmail);
        customerWithEmptyPassword.setPassword("");
        
        when(customerDao.findByEmail(testEmail)).thenReturn(customerWithEmptyPassword);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Customer result = customerService.authenticate(testEmail, "");

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNotNull(result);
        assertEquals(testEmail, result.getEmail());
        verify(customerDao, times(1)).findByEmail(testEmail);
    }

    @Test
    @DisplayName("繝代せ繝ｯ繝ｼ繝峨′螟ｧ譁・ｭ怜ｰ乗枚蟄励ｒ蛹ｺ蛻･縺吶ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testAuthenticateCaseSensitivePassword() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        when(customerDao.findByEmail(testEmail)).thenReturn(testCustomer);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Customer result = customerService.authenticate(testEmail, "PASSWORD123");

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        // 繝代せ繝ｯ繝ｼ繝峨・螟ｧ譁・ｭ怜ｰ乗枚蟄励ｒ蛹ｺ蛻･縺吶ｋ
        assertNull(result);
        verify(customerDao, times(1)).findByEmail(testEmail);
    }

    // getCustomer縺ｮ繝・せ繝・
    @Test
    @DisplayName("鬘ｧ螳｢ID縺ｧ鬘ｧ螳｢諠・ｱ繧貞叙蠕励〒縺阪ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testGetCustomerSuccess() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        Integer customerId = 1;
        when(customerDao.findById(customerId)).thenReturn(testCustomer);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Customer result = customerService.getCustomer(customerId);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        assertEquals(testEmail, result.getEmail());
        verify(customerDao, times(1)).findById(customerId);
    }

    @Test
    @DisplayName("蟄伜惠縺励↑縺・｡ｧ螳｢ID縺ｧ蜿門ｾ玲凾縺ｫnull縺瑚ｿ斐＆繧後ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testGetCustomerNotFound() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        Integer customerId = 999;
        when(customerDao.findById(customerId)).thenReturn(null);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Customer result = customerService.getCustomer(customerId);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNull(result);
        verify(customerDao, times(1)).findById(customerId);
    }

    @Test
    @DisplayName("鬘ｧ螳｢ID縺系ull縺ｮ蝣ｴ蜷医↓null縺瑚ｿ斐＆繧後ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testGetCustomerNullId() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        when(customerDao.findById(null)).thenReturn(null);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Customer result = customerService.getCustomer(null);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNull(result);
        verify(customerDao, times(1)).findById(null);
    }

    @Test
    @DisplayName("縺吶∋縺ｦ縺ｮ繝輔ぅ繝ｼ繝ｫ繝峨ｒ謖√▽鬘ｧ螳｢諠・ｱ繧貞叙蠕励〒縺阪ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testGetCustomerWithAllFields() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・        Integer customerId = 1;
        testCustomer.setAddress("譚ｱ莠ｬ驛ｽ譁ｰ螳ｿ蛹ｺ隘ｿ譁ｰ螳ｿ2-2-2");
        
        when(customerDao.findById(customerId)).thenReturn(testCustomer);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        Customer result = customerService.getCustomer(customerId);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        assertEquals(testEmail, result.getEmail());
        assertEquals("繝・せ繝亥､ｪ驛・, result.getCustomerName());
        assertEquals("譚ｱ莠ｬ驛ｽ譁ｰ螳ｿ蛹ｺ隘ｿ譁ｰ螳ｿ2-2-2", result.getAddress());
        verify(customerDao, times(1)).findById(customerId);
    }
}
