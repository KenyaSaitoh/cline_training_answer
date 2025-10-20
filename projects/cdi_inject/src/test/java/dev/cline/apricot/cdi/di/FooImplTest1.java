package dev.cline.apricot.cdi.di;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FooImplTest1 {

    @Test
    public void testDoBusiness() throws Exception {
        // Barインスタンスを生成する
        Bar bar = new BarImpl();

        // Fooインスタンスを生成する
        Foo foo = new FooImpl(bar);

        // テスト対象メソッドを呼び出す
        int answer = foo.doBusiness(3);

        // 結果を検証する
        Assertions.assertEquals(18, answer);
    }
}