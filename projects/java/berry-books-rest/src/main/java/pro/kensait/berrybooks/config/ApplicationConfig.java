package pro.kensait.berrybooks.config;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

// JAX-RS アプリケーション設定クラス�E�E 以下�EパスでREST APIを�E開する！E
@ApplicationPath("/")
public class ApplicationConfig extends Application {
    // チE��ォルトでは全てのJAX-RSリソースが�E動検�EされめE
}

