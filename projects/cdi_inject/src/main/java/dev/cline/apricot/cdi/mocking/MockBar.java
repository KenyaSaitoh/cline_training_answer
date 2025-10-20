package dev.cline.apricot.cdi.mocking;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Alternative;

import dev.cline.apricot.cdi.di.Bar;

@RequestScoped
@Alternative
//ビジネスメソッド
public class MockBar implements Bar {
    public int doBusiness(int param) {
     // Fooが単体テストを実施しやすくなるように実装する
     // ここでは常に10を返す
        return 10;
    }
}