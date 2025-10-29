package pro.kensait.berrybooks.resource;

import pro.kensait.berrybooks.dto.ErrorResponse;
import pro.kensait.berrybooks.exception.CustomerExistsException;
import pro.kensait.berrybooks.exception.CustomerNotFoundException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

// 顧客関連の例外をHTTPレスポンスに変換するマッパ�Eクラス
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
        
        // そ�E他�E例外�E500エラーとして返す
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("internal.error", exception.getMessage()))
                .build();
    }
}

