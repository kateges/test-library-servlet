// Loading required libraries
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class UsersList extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type

        String usr = request.getRemoteUser();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String title = "Книги";

        String docType =
                "<!doctype html>\n";

        out.println(docType +
                "<html lang = \"ru\">\n" +
                "<head><meta charset=\"utf-8\"><title>" + title + "</title>" +
                "<script type=\"text/javascript\" src=\"js/del.js\"></script>" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\"></head>\n" +
                "<body bgcolor = \"#c8ffc8\">\n" +
                "<table class=\"firstline\" align=\"center\"><tr><td><a href=\"start\" class=\"toptext\">Книги</a></td><td class=\"toptext\">Пользователи</td></tr></table></h1>\n" +
                "<h3 align = \"center\">Текущий пользователь: "+usr+"</h3>\n");
        try {
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

            Statement stmt = conn.createStatement();

            String sql;
            sql = "SELECT * FROM ges_us_test order by user_log";

            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table class=\"sort\" align=\"center\"><thead><tr>" +
                    "<td>Логин</td>" +
                    "<td>Удалить</td>" +
                    "</tr></thead><tbody>");

            while (rs.next()) {

                String user_log = rs.getString("user_log");


                //Display values

                out.print("<td>" + user_log + "</td>");

                out.print("<td>" + "<button><a href=\"delete_user.java\" onclick=\"return confirmDelete();\">Удалить файл</a></button>" + "</td></tr>");

            }
            out.println("</tbody></table></body></html>");

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
