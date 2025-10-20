package jp.mufg.it.ee.rs.hello;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

// Quarkus注記：このクラスはQuarkusでは不要ですが、互換性のため残しています。
// application.propertiesで quarkus.resteasy-reactive.path=/ を設定することで同じ動作を実現できます。
@ApplicationPath("/")
public class MyApplication extends Application {
}
