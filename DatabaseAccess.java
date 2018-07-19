// Loading required libraries
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class DatabaseAccess extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type


        response.setContentType("text/html;charset=Windows-1251");
        PrintWriter out = response.getWriter();
        String title = "Database Table";

        String docType =
                "<!doctype html>\n";

        out.println(docType +
                "<html lang='ru'>\n" +
                "<head><meta charset='utf-8'><title>" + title + "</title></head>\n" +
                "<body bgcolor = \"#ffff99\">\n" +
                "<h1 align = \"center\">" + title + "</h1>\n");

        try {
            // Register JDBC driver

            Class.forName("oracle.jdbc.OracleDriver");

            // Open a connection
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:oradevdb01", "test", "test");

            // Execute SQL query
            Statement stmt = conn.createStatement();

            String sql;
            sql = "SELECT * FROM ges_books_test";

            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table border='1' align='center'><caption>Книги</caption><tr>" +
                    "<th>ISBN</th>" +
                    "<th>Автор</th>" +
                    "<th>Название книги</th>" +
                    "<th>Кем взята</th>" +
                    "</tr>");
            // Extract data from result set
            while (rs.next()) {
                //Retrieve by column name
                String ISBN  = rs.getString("ISBN");
                String author = rs.getString("author");
                String name_book = rs.getString("name_book");
                int user_take = rs.getInt("user_take");

                //Display values

                out.print("<tr><td>" + ISBN + "</td>");
                out.print("<td>" + author + "</td>");
                out.print("<td>" + name_book + "</td>");
                if (user_take == 0)
                    out.print("<td>" + "<button>Взять</button>" + "</td></tr>");
                else
                    out.print("<td>" + user_take + "</td></tr>");
            }
            out.println("</table></body></html>");

            // Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            out.println("<h2 align = \"center\">" +"SQLException"+ se.getSQLState().toString() + "</h2>\n");
        } catch (Exception e) {
            //Handle errors for Class.forName
            out.println("<h2 align = \"center\">" +"Exception: "+ e.toString() + "</h2>\n");
        }
    }

}
