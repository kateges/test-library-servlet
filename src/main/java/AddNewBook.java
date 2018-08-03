import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletResponse;

public class AddNewBook extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String isbn = request.getParameter("isbn_add");
        String author = request.getParameter("author_add");
        String bookname = request.getParameter("bookname_add");
        response.setContentType("text/html;charset=utf-8");

        try {
            Class.forName("org.h2.Driver");

            Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

            Statement stmt = conn.createStatement();

            String sql;
            sql = "SELECT count(*) as cnt FROM ges_books_test where isbn='"+isbn+"'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            int cnt  = rs.getInt("cnt");

            request.setAttribute("cnt_add", cnt);
            request.setAttribute("isbn_add", isbn);
            request.setAttribute("author_add", author);
            request.setAttribute("bookname_add", bookname);

            if (cnt>0)
            {
                stmt.close();
                conn.close();
            }
            else {
                sql = "insert into ges_books_test values('" + isbn + "','" + author + "','" + bookname + "', null)";
                stmt.execute(sql);
                stmt.close();
                conn.close();
            }
            RequestDispatcher rd= request.getServletContext().getRequestDispatcher("/start");
            rd.forward(request, response);
        }
     catch (Exception e) {

        }
    }

}