// Loading required libraries
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*;

public class DatabaseAccess extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usr = request.getRemoteUser();
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        int cnt_add = -1;
        if (request.getAttribute("cnt_add") != null)
            cnt_add = Integer.parseInt(request.getAttribute("cnt_add").toString());
        int cnt_edit = -1;
        if (request.getAttribute("cnt_edit") != null)
            cnt_edit = Integer.parseInt(request.getAttribute("cnt_edit").toString());



        out.println("<html lang = \"ru\">\n" +
                "<head><meta charset=\"utf-8\"><title>Книги</title>" +
                "<script type=\"text/javascript\" charset=\"utf-8\" src=\"js/sorting.js\"></script>" +
                "<link rel=\"stylesheet\" type=\"text/css\" href=\"css/style.css\">"+
                "<script type=\"text/javascript\" charset=\"utf-8\"> " +
                "function del(isbn) {" +
                " if (confirm(\"Удалить книгу?\")){" +
                "document.fordelete.isbn_del.value =isbn;"+
                "document.fordelete.submit();"+
                "}}</script>"+

                "</head>\n" +
                "<body bgcolor = \"#c8ffc8\">\n" +
                
                "<table class=\"firstline\" align=\"center\"><tr><td class=\"toptext\">Книги</td><td><a href=\"UsersList\" class=\"toptext\">Пользователи</a></td></tr></table>\n" +
                "<h3 align = \"center\">Текущий пользователь: "+usr+"</h3>\n"+

                "<h4 align = \"center\"><a class=\"show-btn\" href = \"javascript:void(0)\" onclick = \"document.getElementById('envelope').style.display='block';document.getElementById('fade').style.display='block'\">Добавить книгу</a></h4>" +
    // add new book window
                "<div id=\"envelope\" class=\"envelope\">" +

			    "<form method=\"get\" action=\"AddNewBook\"><br>" +
			    "<h3 align = \"center\">Добавить книгу</h3><br>" +
		        "<table align=\"center\">" +
		        "<tr><td>ISBN</td><td><input type=\"text\" name=\"isbn_add\" id=\"isbn_add\" class=\"text-ok\" required /></td></tr>" +
		        "<tr><td>Автор</td><td><input type=\"text\" name=\"author_add\" id=\"author_add\" class=\"text-ok\" required /></td></tr>" +
		        "<tr><td>Наименование книги</td><td><input type=\"text\" name=\"bookname_add\" id=\"bookname_add\" class=\"text-ok\" required /></td></tr>" +
                "<tr><td> <br> </td><td>  </td></tr>" +
				"<tr><td><input type=\"submit\" name=\"send\" value=\"Добавить\" ></td>" +
                "<td><input type=\"button\" name=\"cancel\" value=\"Отмена\" onclick = \"document.getElementById('envelope').style.display='none';document.getElementById('fade').style.display='none'\"></td></tr>" +
			    "</table></form>" +
                "</div>" +
	            "<div id=\"fade\" class=\"black-overlay\"></div>" +
                "</h1>");

        out.println("<div id=\"editbook\" class=\"envelope\">" +
                "<form method=\"get\" action=\"EditBook\"><br>" +
                "<h3 align = \"center\">Редактировать книгу</h3><br>" +
                "<table align=\"center\">" +
                "<tr><td>ISBN</td><td><input type=\"text\" name=\"isbn_edit\" id=\"isbn_edit\"  class=\"text-disabled\" readonly /></td></tr>" +
                "<tr><td>Автор</td><td><input type=\"text\" name=\"author_edit\" id=\"author_edit\" class=\"text-ok\" required /></td></tr>" +
                "<tr><td>Наименование книги</td><td><input type=\"text\" name=\"bookname_edit\" id=\"bookname_edit\" class=\"text-ok\" required /></td></tr>" +
                "<tr><td> <br> </td><td>  </td></tr>" +
                "<tr><td><input type=\"submit\" name=\"send1\" value=\"Сохранить\" ></td>" +
                "<td><input type=\"button\" name=\"cancel1\" value=\"Отмена\" onclick = \"document.getElementById('editbook').style.display='none';document.getElementById('fade1').style.display='none'\"></td></tr>" +
                "</table></form>" +
                "</div>" +
                "<div id=\"fade1\" class=\"black-overlay\"></div>");
        if (cnt_add==1) {
            out.println("<script>alert(\"Книга с таким isbn уже существует!\");document.getElementById('envelope').style.display='block';document.getElementById('fade').style.display='block'</script>");
            if (request.getParameter("isbn_add") != null)
                out.println("<script>document.getElementById('isbn_add').value='"+request.getParameter("isbn_add")+"'</script>");
            if (request.getParameter("author_add") != null)
                out.println("<script>document.getElementById('author_add').value='"+request.getParameter("author_add")+"'</script>");
            if (request.getParameter("bookname_add") != null)
                out.println("<script>document.getElementById('bookname_add').value='"+request.getParameter("bookname_add")+"'</script>");
        cnt_add=-1;
        }
        if (cnt_add==0) {
            out.println("<script>alert(\"Книга добавлена\");</script>");
        cnt_add=-1;
        }
        if (cnt_add==-2) {
            out.println("<script>alert(\"Книга удалена\");</script>");
        cnt_add=-1;
        }
        if (cnt_edit==2) {
            out.println("<script>alert(\"Изменения сохранены\");</script>");
            cnt_add=-1;
            cnt_edit=-1;
        }
            try {// connection to database
            Class.forName("org.h2.Driver");
            Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");

            Statement stmt = conn.createStatement();

            String sql;
            sql = "SELECT * FROM ges_books_test";

            ResultSet rs = stmt.executeQuery(sql);
            out.println("<table class=\"sort\" align=\"center\"><thead><tr>" +
                    "<td>ISBN</td>" +
                    "<td>Автор</td>" +
                    "<td>Название книги</td>" +
                    "<td>Кем взята</td>" +
                    "<td>Удалить</td>" +
                    "</tr></thead><tbody>");
            //  print data
            while (rs.next()) {
                String ISBN  = rs.getString("ISBN");
                String author = rs.getString("author");
                String name_book = rs.getString("name_book");
                int user_take = rs.getInt("user_take");

                out.print("<tr>");

                out.print("<td onclick = \"document.getElementById('editbook').style.display='block';document.getElementById('fade1').style.display='block';document.getElementById('isbn_edit').value='"+ISBN+"';document.getElementById('author_edit').value='"+author+"';document.getElementById('bookname_edit').value='"+name_book+"'\">"+ ISBN + "</td>");
                out.print("<td>" + author + "</td>");
                out.print("<td>" + name_book + "</td>");
                if (user_take == 0)
                    out.print("<td>" + "<button>Взять</button>" + "</td>");
                else
                    out.print("<td>" + user_take + "</td>");

                out.print("<td> <input type=\"button\" value=\"Удалить\" onclick = \"del('"+ISBN+"')\">" + "</td></tr>");

            }
            out.println("</tbody></table><form name=\"fordelete\" action=\"DelBook\" method=\"get\">\n" +
                    "    <input type=\"hidden\" name=\"isbn_del\" id=\"isbn_del\">\n" +
                    "</form></body></html>");

            rs.close();
            stmt.close();
            conn.close();
            out.close();
        } catch (SQLException se) {

            out.println("<h2 align = \"center\">" +"SQLException "+ se.getMessage() + "</h2>\n");
            out.close();
        } catch (Exception e) {

            e.printStackTrace();
            out.println("<h2 align = \"center\">" +"Exception: "+ e.toString() + "</h2>\n");
            out.close();
        }
    }

}
