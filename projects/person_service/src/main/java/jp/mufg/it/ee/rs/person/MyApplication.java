package jp.mufg.it.ee.rs.person;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/*")
public class MyApplication extends Application {
}