package pro.kensait.berrybooks.service.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pro.kensait.berrybooks.dao.BookDao;
import pro.kensait.berrybooks.dao.OrderDetailDao;
import pro.kensait.berrybooks.dao.OrderTranDao;
import pro.kensait.berrybooks.dao.StockDao;
import pro.kensait.berrybooks.entity.Book;
import pro.kensait.berrybooks.entity.OrderDetail;
import pro.kensait.berrybooks.entity.OrderDetailPK;
import pro.kensait.berrybooks.entity.OrderTran;
import pro.kensait.berrybooks.entity.Stock;
import pro.kensait.berrybooks.web.cart.CartItem;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderTranDao orderTranDao;

    @Mock
    private OrderDetailDao orderDetailDao;

    @Mock
    private BookDao bookDao;

    @Mock
    private StockDao stockDao;

    @InjectMocks
    private OrderService orderService;

    private Integer testCustomerId;
    private Integer testOrderTranId;
    private OrderTran testOrderTran;
    private List<OrderTran> testOrderTranList;

    @BeforeEach
    void setUp() {
        testCustomerId = 1;
        testOrderTranId = 100;
        testOrderTran = new OrderTran();
        testOrderTran.setOrderTranId(testOrderTranId);
        testOrderTran.setCustomer(testCustomerId);
        
        testOrderTranList = new ArrayList<>();
        testOrderTranList.add(testOrderTran);
    }

    @Test
    @DisplayName("鬘ｧ螳｢ID縺ｧ豕ｨ譁・ｱ･豁ｴ繧貞叙蠕励〒縺阪ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testGetOrderHistory() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・
        when(orderTranDao.findByCustomerId(testCustomerId)).thenReturn(testOrderTranList);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        List<OrderTran> result = orderService.getOrderHistory(testCustomerId);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testOrderTranId, result.get(0).getOrderTranId());
        verify(orderTranDao, times(1)).findByCustomerId(testCustomerId);
    }

    @Test
    @DisplayName("豕ｨ譁・ｱ･豁ｴ繧探ransfer Object縺ｧ蜿門ｾ励〒縺阪ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testGetOrderHistory2() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・
        List<OrderHistoryTO> expectedList = new ArrayList<>();
        when(orderTranDao.findOrderHistoryByCustomerId(testCustomerId)).thenReturn(expectedList);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        List<OrderHistoryTO> result = orderService.getOrderHistory2(testCustomerId);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・
        assertNotNull(result);
        verify(orderTranDao, times(1)).findOrderHistoryByCustomerId(testCustomerId);
    }

    @Test
    @DisplayName("豕ｨ譁・ｱ･豁ｴ繧呈・邏ｰ縺ｨ蜈ｱ縺ｫ蜿門ｾ励〒縺阪ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testGetOrderHistory3() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・
        when(orderTranDao.findByCustomerIdWithDetails(testCustomerId)).thenReturn(testOrderTranList);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        List<OrderTran> result = orderService.getOrderHistory3(testCustomerId);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderTranDao, times(1)).findByCustomerIdWithDetails(testCustomerId);
    }

    @Test
    @DisplayName("豕ｨ譁⑩D縺ｧ豕ｨ譁・ュ蝣ｱ繧貞叙蠕励〒縺阪ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testGetOrderTran() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・
        when(orderTranDao.findById(testOrderTranId)).thenReturn(testOrderTran);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        OrderTran result = orderService.getOrderTran(testOrderTranId);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・
        assertNotNull(result);
        assertEquals(testOrderTranId, result.getOrderTranId());
        verify(orderTranDao, times(1)).findById(testOrderTranId);
    }

    @Test
    @DisplayName("蟄伜惠縺励↑縺・ｳｨ譁⑩D縺ｧ萓句､悶′繧ｹ繝ｭ繝ｼ縺輔ｌ繧九％縺ｨ繧偵ユ繧ｹ繝医☆繧・)
    void testGetOrderTranNotFound() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・
        when(orderTranDao.findById(testOrderTranId)).thenReturn(null);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ縺ｨ讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ・・
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderTran(testOrderTranId);
        });
        assertTrue(exception.getMessage().contains("OrderTran not found"));
    }

    @Test
    @DisplayName("豕ｨ譁・ュ蝣ｱ繧呈・邏ｰ縺ｨ蜈ｱ縺ｫ蜿門ｾ励〒縺阪ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testGetOrderTranWithDetails() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・
        when(orderTranDao.findByIdWithDetails(testOrderTranId)).thenReturn(testOrderTran);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        OrderTran result = orderService.getOrderTranWithDetails(testOrderTranId);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・
        assertNotNull(result);
        assertEquals(testOrderTranId, result.getOrderTranId());
        verify(orderTranDao, times(1)).findByIdWithDetails(testOrderTranId);
    }

    @Test
    @DisplayName("蟄伜惠縺励↑縺・ｳｨ譁⑩D縺ｧ譏守ｴｰ蜿門ｾ玲凾縺ｫ萓句､悶′繧ｹ繝ｭ繝ｼ縺輔ｌ繧九％縺ｨ繧偵ユ繧ｹ繝医☆繧・)
    void testGetOrderTranWithDetailsNotFound() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・
        when(orderTranDao.findByIdWithDetails(testOrderTranId)).thenReturn(null);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ縺ｨ讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ・・
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderTranWithDetails(testOrderTranId);
        });
        assertTrue(exception.getMessage().contains("OrderTran not found"));
    }

    @Test
    @DisplayName("隍・粋荳ｻ繧ｭ繝ｼ縺ｧ豕ｨ譁・・邏ｰ繧貞叙蠕励〒縺阪ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testGetOrderDetailByPK() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・
        OrderDetailPK pk = new OrderDetailPK(testOrderTranId, 1);
        OrderDetail expectedDetail = new OrderDetail();
        expectedDetail.setOrderDetailId(1);
        when(orderDetailDao.findById(pk)).thenReturn(expectedDetail);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        OrderDetail result = orderService.getOrderDetail(pk);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・
        assertNotNull(result);
        assertEquals(1, result.getOrderDetailId());
        verify(orderDetailDao, times(1)).findById(pk);
    }

    @Test
    @DisplayName("蟄伜惠縺励↑縺・ｳｨ譁・・邏ｰID縺ｧ萓句､悶′繧ｹ繝ｭ繝ｼ縺輔ｌ繧九％縺ｨ繧偵ユ繧ｹ繝医☆繧・)
    void testGetOrderDetailByPKNotFound() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・
        OrderDetailPK pk = new OrderDetailPK(testOrderTranId, 1);
        when(orderDetailDao.findById(pk)).thenReturn(null);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ縺ｨ讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ・・
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderDetail(pk);
        });
        assertTrue(exception.getMessage().contains("OrderDetail not found"));
    }

    @Test
    @DisplayName("豕ｨ譁⑩D縺ｨ譏守ｴｰID縺ｧ豕ｨ譁・・邏ｰ繧貞叙蠕励〒縺阪ｋ縺薙→繧偵ユ繧ｹ繝医☆繧具ｼ医が繝ｼ繝舌・繝ｭ繝ｼ繝会ｼ・)
    void testGetOrderDetailByIds() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・
        Integer detailId = 1;
        OrderDetail expectedDetail = new OrderDetail();
        expectedDetail.setOrderDetailId(detailId);
        when(orderDetailDao.findById(any(OrderDetailPK.class))).thenReturn(expectedDetail);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        OrderDetail result = orderService.getOrderDetail(testOrderTranId, detailId);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・
        assertNotNull(result);
        assertEquals(detailId, result.getOrderDetailId());
        verify(orderDetailDao, times(1)).findById(any(OrderDetailPK.class));
    }

    @Test
    @DisplayName("豕ｨ譁⑩D縺ｧ豕ｨ譁・・邏ｰ縺ｮ繝ｪ繧ｹ繝医ｒ蜿門ｾ励〒縺阪ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testGetOrderDetails() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・
        List<OrderDetail> expectedList = new ArrayList<>();
        OrderDetail detail1 = new OrderDetail();
        detail1.setOrderDetailId(1);
        expectedList.add(detail1);
        when(orderDetailDao.findByOrderTranId(testOrderTranId)).thenReturn(expectedList);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        List<OrderDetail> result = orderService.getOrderDetails(testOrderTranId);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(orderDetailDao, times(1)).findByOrderTranId(testOrderTranId);
    }

    @Test
    @DisplayName("豕ｨ譁・′豁｣蟶ｸ縺ｫ螳御ｺ・＠縲∝惠蠎ｫ縺梧ｸ帛ｰ代☆繧九％縺ｨ繧偵ユ繧ｹ繝医☆繧・)
    void testOrderBooksSuccess() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・
        Integer bookId = 1;
        Integer quantity = 2;
        Integer stockQuantity = 10;
        
        CartItem cartItem = new CartItem();
        cartItem.setBookId(bookId);
        cartItem.setBookName("Test Book");
        cartItem.setCount(quantity);
        
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        
        OrderTO orderTO = new OrderTO(
            testCustomerId,
            LocalDate.now(),
            cartItems,
            new BigDecimal("2000"),
            new BigDecimal("800"),
            "譚ｱ莠ｬ驛ｽ貂玖ｰｷ蛹ｺ",
            1 // 繧ｯ繝ｬ繧ｸ繝・ヨ繧ｫ繝ｼ繝・
        );
        
        Stock stock = new Stock();
        stock.setBookId(bookId);
        stock.setQuantity(stockQuantity);
        
        Book book = new Book();
        book.setBookId(bookId);
        book.setBookName("Test Book");
        
        OrderTran savedOrderTran = new OrderTran();
        savedOrderTran.setOrderTranId(testOrderTranId);
        
        when(stockDao.findByIdWithLock(bookId)).thenReturn(stock);
        when(bookDao.findById(bookId)).thenReturn(book);
        doAnswer(invocation -> {
            OrderTran ot = invocation.getArgument(0);
            ot.setOrderTranId(testOrderTranId);
            return null;
        }).when(orderTranDao).persist(any(OrderTran.class));
        when(orderTranDao.findByIdWithDetails(testOrderTranId)).thenReturn(savedOrderTran);
        doNothing().when(orderDetailDao).persist(any(OrderDetail.class));

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        OrderTran result = orderService.orderBooks(orderTO);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲∫憾諷九・繝ｼ繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・
        assertNotNull(result);
        assertEquals(stockQuantity - quantity, stock.getQuantity());
        verify(stockDao, times(1)).findByIdWithLock(bookId);
        verify(orderTranDao, times(1)).persist(any(OrderTran.class));
        verify(orderDetailDao, times(1)).persist(any(OrderDetail.class));
        verify(orderTranDao, times(1)).findByIdWithDetails(testOrderTranId);
    }

    @Test
    @DisplayName("蝨ｨ蠎ｫ荳崎ｶｳ縺ｮ蝣ｴ蜷医↓OutOfStockException縺後せ繝ｭ繝ｼ縺輔ｌ繧九％縺ｨ繧偵ユ繧ｹ繝医☆繧・)
    void testOrderBooksOutOfStock() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・
        Integer bookId = 1;
        Integer quantity = 10;
        Integer stockQuantity = 5;
        
        CartItem cartItem = new CartItem();
        cartItem.setBookId(bookId);
        cartItem.setBookName("Test Book");
        cartItem.setCount(quantity);
        
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem);
        
        OrderTO orderTO = new OrderTO(
            testCustomerId,
            LocalDate.now(),
            cartItems,
            new BigDecimal("2000"),
            new BigDecimal("800"),
            "譚ｱ莠ｬ驛ｽ貂玖ｰｷ蛹ｺ",
            1 // 繧ｯ繝ｬ繧ｸ繝・ヨ繧ｫ繝ｼ繝・
        );
        
        Stock stock = new Stock();
        stock.setBookId(bookId);
        stock.setQuantity(stockQuantity);
        
        when(stockDao.findByIdWithLock(bookId)).thenReturn(stock);

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ縺ｨ讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・
        OutOfStockException exception = assertThrows(OutOfStockException.class, () -> {
            orderService.orderBooks(orderTO);
        });
        assertEquals(bookId, exception.getBookId());
        assertEquals("Test Book", exception.getBookName());
        verify(stockDao, times(1)).findByIdWithLock(bookId);
        verify(orderTranDao, never()).persist(any(OrderTran.class));
    }

    @Test
    @DisplayName("隍・焚縺ｮ譖ｸ邀阪ｒ蜷ｫ繧豕ｨ譁・′豁｣蟶ｸ縺ｫ蜃ｦ逅・＆繧後ｋ縺薙→繧偵ユ繧ｹ繝医☆繧・)
    void testOrderBooksMultipleItems() {
        // 貅門ｙ繝輔ぉ繝ｼ繧ｺ・医ユ繧ｹ繝医ヵ繧｣繧ｯ繧ｹ繝√Ε縺ｮ繧ｻ繝・ヨ繧｢繝・・・・
        Integer bookId1 = 1;
        Integer bookId2 = 2;
        
        CartItem cartItem1 = new CartItem();
        cartItem1.setBookId(bookId1);
        cartItem1.setBookName("Test Book 1");
        cartItem1.setCount(2);
        
        CartItem cartItem2 = new CartItem();
        cartItem2.setBookId(bookId2);
        cartItem2.setBookName("Test Book 2");
        cartItem2.setCount(3);
        
        List<CartItem> cartItems = new ArrayList<>();
        cartItems.add(cartItem1);
        cartItems.add(cartItem2);
        
        OrderTO orderTO = new OrderTO(
            testCustomerId,
            LocalDate.now(),
            cartItems,
            new BigDecimal("5000"),
            new BigDecimal("800"),
            "譚ｱ莠ｬ驛ｽ貂玖ｰｷ蛹ｺ",
            1 // 繧ｯ繝ｬ繧ｸ繝・ヨ繧ｫ繝ｼ繝・
        );
        
        Stock stock1 = new Stock();
        stock1.setBookId(bookId1);
        stock1.setQuantity(10);
        
        Stock stock2 = new Stock();
        stock2.setBookId(bookId2);
        stock2.setQuantity(20);
        
        Book book1 = new Book();
        book1.setBookId(bookId1);
        book1.setBookName("Test Book 1");
        
        Book book2 = new Book();
        book2.setBookId(bookId2);
        book2.setBookName("Test Book 2");
        
        OrderTran savedOrderTran = new OrderTran();
        savedOrderTran.setOrderTranId(testOrderTranId);
        
        when(stockDao.findByIdWithLock(bookId1)).thenReturn(stock1);
        when(stockDao.findByIdWithLock(bookId2)).thenReturn(stock2);
        when(bookDao.findById(bookId1)).thenReturn(book1);
        when(bookDao.findById(bookId2)).thenReturn(book2);
        doAnswer(invocation -> {
            OrderTran ot = invocation.getArgument(0);
            ot.setOrderTranId(testOrderTranId);
            return null;
        }).when(orderTranDao).persist(any(OrderTran.class));
        when(orderTranDao.findByIdWithDetails(testOrderTranId)).thenReturn(savedOrderTran);
        doNothing().when(orderDetailDao).persist(any(OrderDetail.class));

        // 螳溯｡後ヵ繧ｧ繝ｼ繧ｺ
        OrderTran result = orderService.orderBooks(orderTO);

        // 讀懆ｨｼ繝輔ぉ繝ｼ繧ｺ・亥・蜉帛､繝吶・繧ｹ縲∫憾諷九・繝ｼ繧ｹ縲√さ繝溘Η繝九こ繝ｼ繧ｷ繝ｧ繝ｳ繝吶・繧ｹ・・
        assertNotNull(result);
        assertEquals(8, stock1.getQuantity()); // 10 - 2
        assertEquals(17, stock2.getQuantity()); // 20 - 3
        verify(orderDetailDao, times(2)).persist(any(OrderDetail.class));
    }
}

