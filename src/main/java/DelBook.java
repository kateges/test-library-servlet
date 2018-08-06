import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.servlet.http.HttpServletResponse;

public class DelBook extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String isbn = request.getParameter("isbn_del");
        request.setAttribute("cnt_add", -2); // флаг, что книга удалена
        response.setContentType("text/html;charset=utf-8");

        try {
            Class.forName("org.h2.Driver");

            Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

            Statement stmt = conn.createStatement();

            String sql;
            sql = "delete FROM ges_books_test where isbn='"+isbn+"'";

            stmt.execute(sql);

                stmt.close();
                conn.close();

            RequestDispatcher rd= request.getServletContext().getRequestDispatcher("/start");
            rd.forward(request, response);
        }
        catch (Exception e) {

        }
    }

}