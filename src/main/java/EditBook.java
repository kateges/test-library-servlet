import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class EditBook extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String isbn = request.getParameter("isbn_edit");
        String author = request.getParameter("author_edit");
        String bookname = request.getParameter("bookname_edit");
        response.setContentType("text/html;charset=utf-8");

            try {
                Class.forName("org.h2.Driver");

                Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

                Statement stmt = conn.createStatement();

                String sql;
                sql = "update ges_books_test set author='"+author+"', name_book='"+bookname+"' where isbn='"+isbn+"'";

                stmt.execute(sql);

                stmt.close();
                conn.close();
                request.setAttribute("cnt_edit", 2);
                request.setAttribute("isbn_edit", isbn);
                request.setAttribute("author_edit", author);
                request.setAttribute("bookname_edit", bookname);

                RequestDispatcher rd= request.getServletContext().getRequestDispatcher("/start");

                rd.forward(request, response);
            }
            catch (Exception e) {

            }
    }
}
