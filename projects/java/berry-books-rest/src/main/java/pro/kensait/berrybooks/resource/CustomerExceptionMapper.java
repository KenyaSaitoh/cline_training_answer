package pro.kensait.berrybooks.resource;

import pro.kensait.berrybooks.dto.ErrorResponse;
import pro.kensait.berrybooks.exception.CustomerExistsException;
import pro.kensait.berrybooks.exception.CustomerNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

// 鬘ｧ螳｢髢｢騾｣縺ｮ萓句､悶ｒHTTP繝ｬ繧ｹ繝昴Φ繧ｹ縺ｫ螟画鋤縺吶ｋ繝槭ャ繝代・繧ｯ繝ｩ繧ｹ
@Provider
public class CustomerExceptionMapper implements ExceptionMapper<RuntimeException> {

    @Override
    public Response toResponse(RuntimeException exception) {
        if (exception instanceof CustomerNotFoundException) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorResponse("customer.not-found", exception.getMessage()))
                    .build();
        } else if (exception instanceof CustomerExistsException) {
            return Response.status(Response.Status.CONFLICT)
                    .entity(new ErrorResponse("customer.exists", exception.getMessage()))
                    .build();
        }
        
        // 縺昴・莉悶・萓句､悶・500繧ｨ繝ｩ繝ｼ縺ｨ縺励※霑斐☆
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("internal.error", exception.getMessage()))
                .build();
    }
}

