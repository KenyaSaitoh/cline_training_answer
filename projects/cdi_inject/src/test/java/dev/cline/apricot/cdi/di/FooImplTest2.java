package dev.cline.apricot.cdi.di;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import dev.cline.apricot.cdi.mocking.MockBar;

public class FooImplTest2 {

    @Test
    public void testDoBusiness() throws Exception {
        // Barのモックオブジェクトを生成する
        Bar bar = new MockBar();

        // Fooインスタンスを生成する
        Foo foo = new FooImpl(bar);

        // テスト対象メソッドを呼び出す
        int answer = foo.doBusiness(3);

        // 結果を検証する
        Assertions.assertEquals(20, answer);
    }
}