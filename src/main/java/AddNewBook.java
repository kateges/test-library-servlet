import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public class AddNewBook extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RequestCustomer customer = RequestCustomer.fromRequestParameters(request);
        customer.setAsRequestAttributes(request);
        List violations = customer.validate();

        if (!violations.isEmpty()) {
            request.setAttribute("violations", violations);
        }

        if (violations.isEmpty())
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:oradevdb01", "test", "test");
            Statement stmt = conn.createStatement();

            String sql;
            sql = "insert into ges_books_test(isbn, author, name_book) values('"+customer.isbn+"','"+customer.author+"','"+customer.bookname+"')";

            stmt.executeQuery(sql);
            sql = "commit";
            stmt.executeQuery(sql);

            stmt.close();
            conn.close();
            String url = "/servlet-test/html/result_ok.html";
            response.sendRedirect(url);
        }
     catch (Exception e) {

    }
    else response.sendRedirect("/servlet-test/html/result_error.html");



    }



    private static class RequestCustomer {

        private final String isbn;
        private final String author;
        private final String bookname;

        private RequestCustomer(String isbn, String author, String bookname) {
            this.isbn = isbn;
            this.author = author;
            this.bookname = bookname;
        }

        public static RequestCustomer fromRequestParameters(
                HttpServletRequest request) {
            return new RequestCustomer(
                    request.getParameter("isbn"),
                    request.getParameter("author"),
                    request.getParameter("bookname"));
        }

        public void setAsRequestAttributes(HttpServletRequest request) {
            request.setAttribute("isbn", isbn);
            request.setAttribute("author", author);
            request.setAttribute("bookname", bookname);
        }

        public List validate() {
            List violations = new ArrayList<String>();
            if ((this.isbn.trim().length() == 0)||(this.isbn.equals("* ISBN"))) {
                violations.add("isbn является обязательным полем");
            }
            if ((this.author.trim().length() == 0)||(this.author.equals("* Author"))) {
                violations.add("Фамилия автора является обязательным полем");
            }
            if ((this.bookname.trim().length() == 0)||(this.bookname.equals("* Book name"))) {
                violations.add("Название книги является обязательным полем");
            }
            return violations;
        }

    }

}