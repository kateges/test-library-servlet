import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TakeBook extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String isbn = request.getParameter("isbn_take");
        String user_log = request.getParameter("user_log");
        response.setContentType("text/html;charset=utf-8");

        try {
            Class.forName("org.h2.Driver");

            Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

            Statement stmt = conn.createStatement();

            String sql;
            sql= "SELECT * FROM ges_us_test where user_log='"+user_log+"'";
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
                int userid  = rs.getInt("user_id");


            sql = "update ges_books_test set user_take= '"+userid+"' where isbn='"+isbn+"'";

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