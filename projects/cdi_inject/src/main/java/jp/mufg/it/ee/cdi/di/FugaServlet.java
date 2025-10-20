package jp.mufg.it.ee.cdi.di;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FugaServlet")
public class FugaServlet extends HttpServlet {
    // インジェクションポイント
    @Inject
    private Foo foo;

    // doPostメソッド
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        // ビジネスメソッドを呼び出す
        int answer = foo.doBusiness(3);

        // 結果を画面に出力する
        PrintWriter out = response.getWriter();
        out.print("NORMAL END ( Answer ---> " + answer + " )");
        out.close();
    }
}