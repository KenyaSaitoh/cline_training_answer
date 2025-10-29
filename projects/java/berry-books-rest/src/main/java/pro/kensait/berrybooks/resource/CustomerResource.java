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

// ?????????REST API???????
@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {
    private static final Logger logger = LoggerFactory.getLogger(
            CustomerResource.class);

    @Inject
    private CustomerService customerService;

    // API??????????????????
    @GET
    @Path("/")
    public Response getAllWithStats() {
        logger.info("[ CustomerResource#getAllWithStats ]");

        // ??????
        List<Customer> customers = customerService.getAllCustomers();

        // ????????????
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

        // ?????????????HTTP?????OK???Response???
        return Response.ok(responseCustomers).build();
    }

    // API???????????????????
    @GET
    @Path("/{customerId}")
    public Response getById(@PathParam("customerId") Integer customerId) {
        logger.info("[ CustomerResource#getById ]");

        // ??????????????????????
        Customer customer = customerService.getCustomerById(customerId);

        // ???????????HTTP???????????TO?????
        CustomerTO responseCustomer = toCustomerTO(customer);
 
        // ??TO??????HTTP?????OK???Response???
        return Response.ok(responseCustomer).build();
    }

    // API?????????????????
    @GET
    @Path("/{customerId}/orders")
    public Response getOrderHistory(@PathParam("customerId") Integer customerId) {
        logger.info("[ CustomerResource#getOrderHistory ]");

        // ????????????
        List<OrderTran> orderTrans = customerService.getOrderHistory(customerId);

        // OrderTran?????????HTTP?????????OrderHistoryTO????????
        List<OrderHistoryTO> responseOrders = new ArrayList<>();
        for (OrderTran orderTran : orderTrans) {
            responseOrders.add(toOrderHistoryTO(orderTran));
        }
 
        // ?????????????HTTP?????OK???Response???
        return Response.ok(responseOrders).build();
    }

    // API?????????????????????????
    @GET
    @Path("/query_email")
    public Response queryByEmail(@QueryParam("email") String email) {
        logger.info("[ CustomerResource#queryByEmail ]");

        // ??????????????????????
        Customer customer = customerService.getCustomerByEmail(email);

        // ???????????HTTP???????????TO?????
        CustomerTO responseCustomer = toCustomerTO(customer);

        // ??????????????HTTP?????OK???Response???
        return Response.ok(responseCustomer).build();
    }

    // API???????????????????????????
    @GET
    @Path("/query_birthday")
    public Response queryFromBirthday(@QueryParam("birthday") String birthdayStr) {
        logger.info("[ CustomerResource#queryFromBirthday ]");

        // ????LocalDate???
        LocalDate birthday = LocalDate.parse(birthdayStr);

        // ?????????????????????????
        List<Customer> customers = customerService.searchCustomersFromBirthday(birthday);

        // ???????????????HTTP???????????TO????????
        List<CustomerTO> responseCustomerList = new ArrayList<>();
        for (Customer customer : customers) {
            responseCustomerList.add(toCustomerTO(customer));
        }

        // ???????????HTTP?????OK???Response???
        return Response.ok(responseCustomerList).build();
    }
    
    // API??????????????
    @POST
    @Path("/")
    public Response create(CustomerTO requestCustomer) {
        logger.info("[ CustomerResource#create ]");

        // ???????TO????????????????
        Customer customer = toCustomer(requestCustomer);

        // ??????????????????
        customerService.registerCustomer(customer);

        // ???????????HTTP???????????TO?????
        CustomerTO responseCustomerTO = toCustomerTO(customer);

        // ???????HTTP?????OK???Response???
        return Response.ok(responseCustomerTO).build();
    }

    // API????????????
    @PUT
    @Path("/{customerId}")
    public Response replace(
            @PathParam("customerId") Integer customerId,
            CustomerTO requestCustomer) {
        logger.info("[ CustomerResource#replace ]");

        // ???????TO????????????????
        Customer customer = toCustomer(requestCustomer);

        // ??????????????????
        customer.setCustomerId(customerId);
        customerService.replaceCustomer(customer);

        // ???????HTTP?????OK???Response???
        return Response.ok().build();
    }

    // API????????????
    @DELETE
    @Path("/{customerId}")
    public Response delete(@PathParam("customerId") Integer customerId) {
        logger.info("[ CustomerResource#delete ]");

        // ???????ID??????????????
        customerService.deleteCustomer(customerId);

        // ???????HTTP?????OK???Response???
        return Response.ok().build();
    }

    // ???????Customer?CustomerTO
    private CustomerTO toCustomerTO(Customer customer) {
        return new CustomerTO(customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getEmail(),
                customer.getBirthday(),
                customer.getAddress());
    }

    // ???????CustomerTO?Customer
    private Customer toCustomer(CustomerTO customerTO) {
        // ??????????????????????????????
        return new Customer(customerTO.customerName(),
                "",  // ??????????
                customerTO.email(),
                customerTO.birthday(),
                customerTO.address());
    }

    // ???????OrderTran?OrderHistoryTO
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
