// Loading required libraries
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class UsersList extends HttpServlet{

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Set response content type

        String usr = request.getRemoteUser();
        response.setContentType("text/html;charset=Windows-1251");
        PrintWriter out = response.getWriter();
        String title = "�����";

        String docType =
                "<!doctype html>\n";

        out.println(docType +
                "<html lang = \"ru\">\n" +
                "<head><meta charset=\"utf-8\"><title>" + title + "</title>" +
              
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\"></head>\n" +
                "<body bgcolor = \"#c8ffc8\">\n" +
                "<h1 align = \"center\"><table class=\"firstline\" align=\"center\"><tr><td><a href=\"DatabaseAccess\">�����</a></td><td>������������</td></tr></table></h1>\n" +
                "<h3 align = \"center\">������� ������������: "+usr+"</h3>\n");
        try {
            // Register JDBC driver

            Class.forName("oracle.jdbc.OracleDriver");

            // Open a connection
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:oradevdb01", "test", "test");

            // Execute SQL query
            Statement stmt = conn.createStatement();

            String sql;
            sql = "SELECT * FROM ges_us_test order by user_log";

            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table class=\"sort\" align=\"center\"><thead><tr>" +     
                    "<td>�����</td>" +
                    "<td>�������</td>" +
                    "</tr></thead><tbody>");

            while (rs.next()) {
               
                String user_log = rs.getString("user_log");


                //Display values

                out.print("<td>" + user_log + "</td>");

                out.print("<td>" + "<button>�������</button>" + "</td></tr>");
                
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
