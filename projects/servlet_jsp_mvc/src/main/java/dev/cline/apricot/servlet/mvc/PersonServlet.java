package dev.cline.apricot.servlet.mvc;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/PersonServlet")
public class PersonServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // リクエストの文字コードを設定する
        request.setCharacterEncoding("UTF-8");

        // 入力値を取得する
        String personName = request.getParameter("personName");
        String country = request.getParameter("country");

        // モデルを呼び出してビジネスロジックを実行する
        PersonModel person = new PersonModel(personName, country);
        person.sayHello();

        // リクエストスコープにモデルを格納する
        request.setAttribute("person", person);

        // リクエストをJSPページにフォワードする
        RequestDispatcher rd = request.getRequestDispatcher(
                "/WEB-INF/jsp/PersonOutputPage.jsp");
        rd.forward(request, response);
    }
}