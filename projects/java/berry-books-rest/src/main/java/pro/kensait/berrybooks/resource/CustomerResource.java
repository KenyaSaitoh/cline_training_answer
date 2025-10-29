package pro.kensait.berrybooks.resource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pro.kensait.berrybooks.dto.CustomerStatsTO;
import pro.kensait.berrybooks.dto.CustomerTO;
import pro.kensait.berrybooks.dto.OrderHistoryTO;
import pro.kensait.berrybooks.dto.OrderItemTO;
import pro.kensait.berrybooks.entity.Customer;
import pro.kensait.berrybooks.entity.OrderDetail;
import pro.kensait.berrybooks.entity.OrderTran;
import pro.kensait.berrybooks.service.CustomerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

// 顧客惁E��を提供するREST APIリソースクラス
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
    private static final Logger logger = LoggerFactory.getLogger(
            CustomerResource.class);

    @Inject
    private CustomerService customerService;

    // APIメソチE���E��E顧客と統計情報を取得すめE
    @GET
    @Path("/")
    public Response getAllWithStats() {
        logger.info("[ CustomerResource#getAllWithStats ]");

        // 全顧客を取征E
        List<Customer> customers = customerService.getAllCustomers();

        // 顧客ごとに統計情報を追加
        List<CustomerStatsTO> responseCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            Long orderCount = customerService.getOrderCount(customer.getCustomerId());
            Long totalBooks = customerService.getTotalBookCount(customer.getCustomerId());
            
            responseCustomers.add(new CustomerStatsTO(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getEmail(),
                customer.getBirthday(),
                customer.getAddress(),
                orderCount,
                totalBooks
            ));
        }

        // 顧客統計リスト（�EチE���E�とHTTPスチE�EタスOKを持つResponseを返す
        return Response.ok(responseCustomers).build();
    }

    // APIメソチE���E�顧客を取得する（主キー検索�E�E
    @GET
    @Path("/{customerId}")
    public Response getById(@PathParam("customerId") Integer customerId) {
        logger.info("[ CustomerResource#getById ]");

        // メールアドレスから顧客エンチE��チE��を検索する
        Customer customer = customerService.getCustomerById(customerId);

        // 顧客エンチE��チE��から、HTTPレスポンス返却用の顧客TOを生成すめE
        CustomerTO responseCustomer = toCustomerTO(customer);
 
        // 顧客TO�E��EチE���E�とHTTPスチE�EタスOKを持つResponseを返す
        return Response.ok(responseCustomer).build();
    }

    // APIメソチE���E�顧客の注斁E��歴を取得すめE
    @GET
    @Path("/{customerId}/orders")
    public Response getOrderHistory(@PathParam("customerId") Integer customerId) {
        logger.info("[ CustomerResource#getOrderHistory ]");

        // 顧客の注斁E��歴を取得すめE
        List<OrderTran> orderTrans = customerService.getOrderHistory(customerId);

        // OrderTranエンチE��チE��から、HTTPレスポンス返却用のOrderHistoryTOリストを生�Eする
        List<OrderHistoryTO> responseOrders = new ArrayList<>();
        for (OrderTran orderTran : orderTrans) {
            responseOrders.add(toOrderHistoryTO(orderTran));
        }
 
        // 注斁E��歴リスト（�EチE���E�とHTTPスチE�EタスOKを持つResponseを返す
        return Response.ok(responseOrders).build();
    }

    // APIメソチE���E�顧客を取得する（一意キーからの条件検索�E�E
    @GET
    @Path("/query_email")
    public Response queryByEmail(@QueryParam("email") String email) {
        logger.info("[ CustomerResource#queryByEmail ]");

        // メールアドレスから顧客エンチE��チE��を検索する
        Customer customer = customerService.getCustomerByEmail(email);

        // 顧客エンチE��チE��から、HTTPレスポンス返却用の顧客TOを生成すめE
        CustomerTO responseCustomer = toCustomerTO(customer);

        // 顧客エンチE��チE���E��EチE���E�とHTTPスチE�EタスOKを持つResponseを返す
        return Response.ok(responseCustomer).build();
    }

    // APIメソチE���E�顧客リストを取得する（誕生日からの条件検索�E�E
    @GET
    @Path("/query_birthday")
    public Response queryFromBirthday(@QueryParam("birthday") String birthdayStr) {
        logger.info("[ CustomerResource#queryFromBirthday ]");

        // 斁E���EをLocalDateに変換
        LocalDate birthday = LocalDate.parse(birthdayStr);

        // 誕生日開始日から顧客エンチE��チE��のリストを取得すめE
        List<Customer> customers = customerService.searchCustomersFromBirthday(birthday);

        // 顧客エンチE��チE��のリストから、HTTPレスポンス返却用の顧客TOリストを生�Eする
        List<CustomerTO> responseCustomerList = new ArrayList<>();
        for (Customer customer : customers) {
            responseCustomerList.add(toCustomerTO(customer));
        }

        // 顧客リスト（�EチE���E�とHTTPスチE�EタスOKを持つResponseを返す
        return Response.ok(responseCustomerList).build();
    }
    
    // APIメソチE���E�顧客を新規登録する
    @POST
    @Path("/")
    public Response create(CustomerTO requestCustomer) {
        logger.info("[ CustomerResource#create ]");

        // 受け取った顧客TOから、E��客エンチE��チE��を生成すめE
        Customer customer = toCustomer(requestCustomer);

        // 受け取った顧客エンチE��チE��を保存すめE
        customerService.registerCustomer(customer);

        // 顧客エンチE��チE��から、HTTPレスポンス返却用の顧客TOを生成すめE
        CustomerTO responseCustomerTO = toCustomerTO(customer);

        // ボディが空で、HTTPスチE�EタスOKを持つResponseを返す
        return Response.ok(responseCustomerTO).build();
    }

    // APIメソチE���E�顧客を置換すめE
    @PUT
    @Path("/{customerId}")
    public Response replace(
            @PathParam("customerId") Integer customerId,
            CustomerTO requestCustomer) {
        logger.info("[ CustomerResource#replace ]");

        // 受け取った顧客TOから、E��客エンチE��チE��を生成すめE
        Customer customer = toCustomer(requestCustomer);

        // 受け取った顧客エンチE��チE��を置換すめE
        customer.setCustomerId(customerId);
        customerService.replaceCustomer(customer);

        // ボディが空で、HTTPスチE�EタスOKを持つResponseを返す
        return Response.ok().build();
    }

    // APIメソチE���E�顧客を削除する
    @DELETE
    @Path("/{customerId}")
    public Response delete(@PathParam("customerId") Integer customerId) {
        logger.info("[ CustomerResource#delete ]");

        // 受け取った顧客IDを持つエンチE��チE��を削除する
        customerService.deleteCustomer(customerId);

        // ボディが空で、HTTPスチE�EタスOKを持つResponseを返す
        return Response.ok().build();
    }

    // 詰め替え�E琁E��Eustomer→CustomerTO�E�E
    private CustomerTO toCustomerTO(Customer customer) {
        return new CustomerTO(customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getEmail(),
                customer.getBirthday(),
                customer.getAddress());
    }

    // 詰め替え�E琁E��EustomerTO→Customer�E�E
    private Customer toCustomer(CustomerTO customerTO) {
        // パスワード�E空斁E���Eとして扱ぁE��新規登録時�E別途設定が忁E��E��E
        return new Customer(customerTO.customerName(),
                "",  // パスワード�E別途設宁E
                customerTO.email(),
                customerTO.birthday(),
                customerTO.address());
    }

    // 詰め替え�E琁E��ErderTran→OrderHistoryTO�E�E
    private OrderHistoryTO toOrderHistoryTO(OrderTran orderTran) {
        List<OrderItemTO> items = new ArrayList<>();
        
        if (orderTran.getOrderDetails() != null) {
            for (OrderDetail detail : orderTran.getOrderDetails()) {
                items.add(new OrderItemTO(
                    detail.getOrderDetailId(),
                    detail.getBook().getBookId(),
                    detail.getBook().getBookName(),
                    detail.getBook().getAuthor(),
                    detail.getPrice(),
                    detail.getCount()
                ));
            }
        }
        
        return new OrderHistoryTO(
            orderTran.getOrderTranId(),
            orderTran.getOrderDate(),
            orderTran.getTotalPrice(),
            orderTran.getDeliveryPrice(),
            orderTran.getDeliveryAddress(),
            orderTran.getSettlementType(),
            items
        );
    }
}

