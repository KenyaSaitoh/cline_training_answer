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
        testCustomer.setCustomerName("チE��ト太郁E);
        testCustomer.setAddress("東京都渋谷区神宮剁E-1-1");
    }

    // registerCustomerのチE��チE
    @Test
    @DisplayName("新規顧客の登録が正常に完亁E��ることをテストすめE)
    void testRegisterCustomerSuccess() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Customer newCustomer = new Customer();
        newCustomer.setEmail("new@example.com");
        newCustomer.setPassword("newpass123");
        newCustomer.setCustomerName("新規太郁E);
        
        when(customerDao.findByEmail("new@example.com")).thenReturn(null);
        doNothing().when(customerDao).register(newCustomer);

        // 実行フェーズ
        Customer result = customerService.registerCustomer(newCustomer);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals("new@example.com", result.getEmail());
        verify(customerDao, times(1)).findByEmail("new@example.com");
        verify(customerDao, times(1)).register(newCustomer);
    }

    @Test
    @DisplayName("重褁E��たメールアドレスで登録時に例外がスローされることをテストすめE)
    void testRegisterCustomerDuplicateEmail() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Customer newCustomer = new Customer();
        newCustomer.setEmail(testEmail);
        newCustomer.setPassword("newpass123");
        
        when(customerDao.findByEmail(testEmail)).thenReturn(testCustomer);

        // 実行フェーズと検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            customerService.registerCustomer(newCustomer);
        });
        assertEquals("こ�Eメールアドレスは既に登録されてぁE��ぁE, exception.getMessage());
        verify(customerDao, times(1)).findByEmail(testEmail);
        verify(customerDao, never()).register(any(Customer.class));
    }

    @Test
    @DisplayName("メールアドレスがnullの場合でも登録処琁E��実行されることをテストすめE)
    void testRegisterCustomerNullEmail() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Customer newCustomer = new Customer();
        newCustomer.setEmail(null);
        newCustomer.setPassword("password123");
        
        when(customerDao.findByEmail(null)).thenReturn(null);
        doNothing().when(customerDao).register(newCustomer);

        // 実行フェーズ
        Customer result = customerService.registerCustomer(newCustomer);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        verify(customerDao, times(1)).findByEmail(null);
        verify(customerDao, times(1)).register(newCustomer);
    }

    // authenticateのチE��チE
    @Test
    @DisplayName("正しいメールアドレスとパスワードで認証が�E功することをテストすめE)
    void testAuthenticateSuccess() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        when(customerDao.findByEmail(testEmail)).thenReturn(testCustomer);

        // 実行フェーズ
        Customer result = customerService.authenticate(testEmail, testPassword);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(testEmail, result.getEmail());
        assertEquals(testCustomer.getCustomerId(), result.getCustomerId());
        verify(customerDao, times(1)).findByEmail(testEmail);
    }

    @Test
    @DisplayName("誤ったパスワードで認証が失敗することをテストすめE)
    void testAuthenticateWrongPassword() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        when(customerDao.findByEmail(testEmail)).thenReturn(testCustomer);

        // 実行フェーズ
        Customer result = customerService.authenticate(testEmail, "wrongpassword");

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNull(result);
        verify(customerDao, times(1)).findByEmail(testEmail);
    }

    @Test
    @DisplayName("存在しなぁE��ールアドレスで認証が失敗することをテストすめE)
    void testAuthenticateUserNotFound() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        when(customerDao.findByEmail("notfound@example.com")).thenReturn(null);

        // 実行フェーズ
        Customer result = customerService.authenticate("notfound@example.com", testPassword);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNull(result);
        verify(customerDao, times(1)).findByEmail("notfound@example.com");
    }

    @Test
    @DisplayName("メールアドレスがnullの場合に認証が失敗することをテストすめE)
    void testAuthenticateNullEmail() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        when(customerDao.findByEmail(null)).thenReturn(null);

        // 実行フェーズ
        Customer result = customerService.authenticate(null, testPassword);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNull(result);
        verify(customerDao, times(1)).findByEmail(null);
    }

    @Test
    @DisplayName("パスワードがnullの場合に認証が失敗することをテストすめE)
    void testAuthenticateNullPassword() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        when(customerDao.findByEmail(testEmail)).thenReturn(testCustomer);

        // 実行フェーズ
        Customer result = customerService.authenticate(testEmail, null);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNull(result);
        verify(customerDao, times(1)).findByEmail(testEmail);
    }

    @Test
    @DisplayName("空斁E���Eのパスワードで認証が�E功することをテストする（一致する場合！E)
    void testAuthenticateEmptyPassword() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Customer customerWithEmptyPassword = new Customer();
        customerWithEmptyPassword.setCustomerId(1);
        customerWithEmptyPassword.setEmail(testEmail);
        customerWithEmptyPassword.setPassword("");
        
        when(customerDao.findByEmail(testEmail)).thenReturn(customerWithEmptyPassword);

        // 実行フェーズ
        Customer result = customerService.authenticate(testEmail, "");

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(testEmail, result.getEmail());
        verify(customerDao, times(1)).findByEmail(testEmail);
    }

    @Test
    @DisplayName("パスワードが大斁E��小文字を区別することをテストすめE)
    void testAuthenticateCaseSensitivePassword() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        when(customerDao.findByEmail(testEmail)).thenReturn(testCustomer);

        // 実行フェーズ
        Customer result = customerService.authenticate(testEmail, "PASSWORD123");

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        // パスワード�E大斁E��小文字を区別する
        assertNull(result);
        verify(customerDao, times(1)).findByEmail(testEmail);
    }

    // getCustomerのチE��チE
    @Test
    @DisplayName("顧客IDで顧客惁E��を取得できることをテストすめE)
    void testGetCustomerSuccess() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Integer customerId = 1;
        when(customerDao.findById(customerId)).thenReturn(testCustomer);

        // 実行フェーズ
        Customer result = customerService.getCustomer(customerId);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        assertEquals(testEmail, result.getEmail());
        verify(customerDao, times(1)).findById(customerId);
    }

    @Test
    @DisplayName("存在しなぁE��客IDで取得時にnullが返されることをテストすめE)
    void testGetCustomerNotFound() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Integer customerId = 999;
        when(customerDao.findById(customerId)).thenReturn(null);

        // 実行フェーズ
        Customer result = customerService.getCustomer(customerId);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNull(result);
        verify(customerDao, times(1)).findById(customerId);
    }

    @Test
    @DisplayName("顧客IDがnullの場合にnullが返されることをテストすめE)
    void testGetCustomerNullId() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        when(customerDao.findById(null)).thenReturn(null);

        // 実行フェーズ
        Customer result = customerService.getCustomer(null);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNull(result);
        verify(customerDao, times(1)).findById(null);
    }

    @Test
    @DisplayName("すべてのフィールドを持つ顧客惁E��を取得できることをテストすめE)
    void testGetCustomerWithAllFields() {
        // 準備フェーズ�E�テストフィクスチャのセチE��アチE�E�E�E        Integer customerId = 1;
        testCustomer.setAddress("東京都新宿区西新宿2-2-2");
        
        when(customerDao.findById(customerId)).thenReturn(testCustomer);

        // 実行フェーズ
        Customer result = customerService.getCustomer(customerId);

        // 検証フェーズ�E��E力値ベ�Eス、コミュニケーションベ�Eス�E�E        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        assertEquals(testEmail, result.getEmail());
        assertEquals("チE��ト太郁E, result.getCustomerName());
        assertEquals("東京都新宿区西新宿2-2-2", result.getAddress());
        verify(customerDao, times(1)).findById(customerId);
    }
}
