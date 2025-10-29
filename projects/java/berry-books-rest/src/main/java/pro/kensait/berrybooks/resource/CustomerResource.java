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

// 鬘ｧ螳｢諠・ｱ繧呈署萓帙☆繧騎EST API繝ｪ繧ｽ繝ｼ繧ｹ繧ｯ繝ｩ繧ｹ
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
    private static final Logger logger = LoggerFactory.getLogger(
            CustomerResource.class);

    @Inject
    private CustomerService customerService;

    // API繝｡繧ｽ繝・ラ・壼・鬘ｧ螳｢縺ｨ邨ｱ險域ュ蝣ｱ繧貞叙蠕励☆繧・
    @GET
    @Path("/")
    public Response getAllWithStats() {
        logger.info("[ CustomerResource#getAllWithStats ]");

        // 蜈ｨ鬘ｧ螳｢繧貞叙蠕・
        List<Customer> customers = customerService.getAllCustomers();

        // 鬘ｧ螳｢縺斐→縺ｫ邨ｱ險域ュ蝣ｱ繧定ｿｽ蜉
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

        // 鬘ｧ螳｢邨ｱ險医Μ繧ｹ繝茨ｼ医・繝・ぅ・峨→HTTP繧ｹ繝・・繧ｿ繧ｹOK繧呈戟縺､Response繧定ｿ斐☆
        return Response.ok(responseCustomers).build();
    }

    // API繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繧貞叙蠕励☆繧具ｼ井ｸｻ繧ｭ繝ｼ讀懃ｴ｢・・
    @GET
    @Path("/{customerId}")
    public Response getById(@PathParam("customerId") Integer customerId) {
        logger.info("[ CustomerResource#getById ]");

        // 繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺九ｉ鬘ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ繧呈､懃ｴ｢縺吶ｋ
        Customer customer = customerService.getCustomerById(customerId);

        // 鬘ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ縺九ｉ縲？TTP繝ｬ繧ｹ繝昴Φ繧ｹ霑泌唆逕ｨ縺ｮ鬘ｧ螳｢TO繧堤函謌舌☆繧・
        CustomerTO responseCustomer = toCustomerTO(customer);
 
        // 鬘ｧ螳｢TO・医・繝・ぅ・峨→HTTP繧ｹ繝・・繧ｿ繧ｹOK繧呈戟縺､Response繧定ｿ斐☆
        return Response.ok(responseCustomer).build();
    }

    // API繝｡繧ｽ繝・ラ・夐｡ｧ螳｢縺ｮ豕ｨ譁・ｱ･豁ｴ繧貞叙蠕励☆繧・
    @GET
    @Path("/{customerId}/orders")
    public Response getOrderHistory(@PathParam("customerId") Integer customerId) {
        logger.info("[ CustomerResource#getOrderHistory ]");

        // 鬘ｧ螳｢縺ｮ豕ｨ譁・ｱ･豁ｴ繧貞叙蠕励☆繧・
        List<OrderTran> orderTrans = customerService.getOrderHistory(customerId);

        // OrderTran繧ｨ繝ｳ繝・ぅ繝・ぅ縺九ｉ縲？TTP繝ｬ繧ｹ繝昴Φ繧ｹ霑泌唆逕ｨ縺ｮOrderHistoryTO繝ｪ繧ｹ繝医ｒ逕滓・縺吶ｋ
        List<OrderHistoryTO> responseOrders = new ArrayList<>();
        for (OrderTran orderTran : orderTrans) {
            responseOrders.add(toOrderHistoryTO(orderTran));
        }
 
        // 豕ｨ譁・ｱ･豁ｴ繝ｪ繧ｹ繝茨ｼ医・繝・ぅ・峨→HTTP繧ｹ繝・・繧ｿ繧ｹOK繧呈戟縺､Response繧定ｿ斐☆
        return Response.ok(responseOrders).build();
    }

    // API繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繧貞叙蠕励☆繧具ｼ井ｸ諢上く繝ｼ縺九ｉ縺ｮ譚｡莉ｶ讀懃ｴ｢・・
    @GET
    @Path("/query_email")
    public Response queryByEmail(@QueryParam("email") String email) {
        logger.info("[ CustomerResource#queryByEmail ]");

        // 繝｡繝ｼ繝ｫ繧｢繝峨Ξ繧ｹ縺九ｉ鬘ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ繧呈､懃ｴ｢縺吶ｋ
        Customer customer = customerService.getCustomerByEmail(email);

        // 鬘ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ縺九ｉ縲？TTP繝ｬ繧ｹ繝昴Φ繧ｹ霑泌唆逕ｨ縺ｮ鬘ｧ螳｢TO繧堤函謌舌☆繧・
        CustomerTO responseCustomer = toCustomerTO(customer);

        // 鬘ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ・医・繝・ぅ・峨→HTTP繧ｹ繝・・繧ｿ繧ｹOK繧呈戟縺､Response繧定ｿ斐☆
        return Response.ok(responseCustomer).build();
    }

    // API繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繝ｪ繧ｹ繝医ｒ蜿門ｾ励☆繧具ｼ郁ｪ慕函譌･縺九ｉ縺ｮ譚｡莉ｶ讀懃ｴ｢・・
    @GET
    @Path("/query_birthday")
    public Response queryFromBirthday(@QueryParam("birthday") String birthdayStr) {
        logger.info("[ CustomerResource#queryFromBirthday ]");

        // 譁・ｭ怜・繧鱈ocalDate縺ｫ螟画鋤
        LocalDate birthday = LocalDate.parse(birthdayStr);

        // 隱慕函譌･髢句ｧ区律縺九ｉ鬘ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ縺ｮ繝ｪ繧ｹ繝医ｒ蜿門ｾ励☆繧・
        List<Customer> customers = customerService.searchCustomersFromBirthday(birthday);

        // 鬘ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ縺ｮ繝ｪ繧ｹ繝医°繧峨？TTP繝ｬ繧ｹ繝昴Φ繧ｹ霑泌唆逕ｨ縺ｮ鬘ｧ螳｢TO繝ｪ繧ｹ繝医ｒ逕滓・縺吶ｋ
        List<CustomerTO> responseCustomerList = new ArrayList<>();
        for (Customer customer : customers) {
            responseCustomerList.add(toCustomerTO(customer));
        }

        // 鬘ｧ螳｢繝ｪ繧ｹ繝茨ｼ医・繝・ぅ・峨→HTTP繧ｹ繝・・繧ｿ繧ｹOK繧呈戟縺､Response繧定ｿ斐☆
        return Response.ok(responseCustomerList).build();
    }
    
    // API繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繧呈眠隕冗匳骭ｲ縺吶ｋ
    @POST
    @Path("/")
    public Response create(CustomerTO requestCustomer) {
        logger.info("[ CustomerResource#create ]");

        // 蜿励￠蜿悶▲縺滄｡ｧ螳｢TO縺九ｉ縲・｡ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ繧堤函謌舌☆繧・
        Customer customer = toCustomer(requestCustomer);

        // 蜿励￠蜿悶▲縺滄｡ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ繧剃ｿ晏ｭ倥☆繧・
        customerService.registerCustomer(customer);

        // 鬘ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ縺九ｉ縲？TTP繝ｬ繧ｹ繝昴Φ繧ｹ霑泌唆逕ｨ縺ｮ鬘ｧ螳｢TO繧堤函謌舌☆繧・
        CustomerTO responseCustomerTO = toCustomerTO(customer);

        // 繝懊ョ繧｣縺檎ｩｺ縺ｧ縲？TTP繧ｹ繝・・繧ｿ繧ｹOK繧呈戟縺､Response繧定ｿ斐☆
        return Response.ok(responseCustomerTO).build();
    }

    // API繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繧堤ｽｮ謠帙☆繧・
    @PUT
    @Path("/{customerId}")
    public Response replace(
            @PathParam("customerId") Integer customerId,
            CustomerTO requestCustomer) {
        logger.info("[ CustomerResource#replace ]");

        // 蜿励￠蜿悶▲縺滄｡ｧ螳｢TO縺九ｉ縲・｡ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ繧堤函謌舌☆繧・
        Customer customer = toCustomer(requestCustomer);

        // 蜿励￠蜿悶▲縺滄｡ｧ螳｢繧ｨ繝ｳ繝・ぅ繝・ぅ繧堤ｽｮ謠帙☆繧・
        customer.setCustomerId(customerId);
        customerService.replaceCustomer(customer);

        // 繝懊ョ繧｣縺檎ｩｺ縺ｧ縲？TTP繧ｹ繝・・繧ｿ繧ｹOK繧呈戟縺､Response繧定ｿ斐☆
        return Response.ok().build();
    }

    // API繝｡繧ｽ繝・ラ・夐｡ｧ螳｢繧貞炎髯､縺吶ｋ
    @DELETE
    @Path("/{customerId}")
    public Response delete(@PathParam("customerId") Integer customerId) {
        logger.info("[ CustomerResource#delete ]");

        // 蜿励￠蜿悶▲縺滄｡ｧ螳｢ID繧呈戟縺､繧ｨ繝ｳ繝・ぅ繝・ぅ繧貞炎髯､縺吶ｋ
        customerService.deleteCustomer(customerId);

        // 繝懊ョ繧｣縺檎ｩｺ縺ｧ縲？TTP繧ｹ繝・・繧ｿ繧ｹOK繧呈戟縺､Response繧定ｿ斐☆
        return Response.ok().build();
    }

    // 隧ｰ繧∵崛縺亥・逅・ｼ・ustomer竊辰ustomerTO・・
    private CustomerTO toCustomerTO(Customer customer) {
        return new CustomerTO(customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getEmail(),
                customer.getBirthday(),
                customer.getAddress());
    }

    // 隧ｰ繧∵崛縺亥・逅・ｼ・ustomerTO竊辰ustomer・・
    private Customer toCustomer(CustomerTO customerTO) {
        // 繝代せ繝ｯ繝ｼ繝峨・遨ｺ譁・ｭ怜・縺ｨ縺励※謇ｱ縺・ｼ域眠隕冗匳骭ｲ譎ゅ・蛻･騾碑ｨｭ螳壹′蠢・ｦ・ｼ・
        return new Customer(customerTO.customerName(),
                "",  // 繝代せ繝ｯ繝ｼ繝峨・蛻･騾碑ｨｭ螳・
                customerTO.email(),
                customerTO.birthday(),
                customerTO.address());
    }

    // 隧ｰ繧∵崛縺亥・逅・ｼ・rderTran竊丹rderHistoryTO・・
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

