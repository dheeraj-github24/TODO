import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dheeraj
 */
public class TODO extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String date = request.getParameter("date");
        String descr = request.getParameter("description");
        String deadline = request.getParameter("deadline");
        String time = request.getParameter("time");
        System.out.println("This is Date "+date);
        System.out.println("This is Time "+descr);
        System.out.println("This is Task "+deadline);
        System.out.println("This is Deadline "+time);
        System.out.println("=================");
        
            String select_val = request.getParameter("dp");
            System.out.println(select_val);

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/todo","root","");
            
            PrintWriter pw = response.getWriter();
            
            if(select_val.equals("Create")){
           
            PreparedStatement pst = con.prepareStatement("insert into todo.task values(?, ?, ?, ?)");
            
            pst.setString(1, date);
            pst.setString(2, descr);
            pst.setString(3, deadline);
            pst.setString(4, time);
            pst.executeUpdate();
            
            pst.close();
            con.close();
            
                 pw.println("<html><body><h1>Successfully Inserted" + "</h1></body></html>");
            pw.println("<input type= \n"+ "button \n" +"value=DashBoard\n" + "onclick=dashBoardPage(this.value);\n" + "/>");
                           pw.print(" </head>\n" +
"    <body>\n" +
"        <script type=\"text/javascript\">\n" +
"            function dashBoardPage(clickVal){\n" +
"                if(clickVal === \"DashBoard\")\n" +
"                    window.location = \"todo_web.html\";\n" +
"                else\n" +
"                    alert(\"Please click buttons to generate some events\");\n" +
"            }\n" +
"        </script>\n" +
"    </body>\n" +
"</html>");     
        }
            
        if(select_val.equals("Update")){
            
            PreparedStatement pst=con.prepareStatement("update todo.task set deadline=?, time=? where date=? and description=?");  
            pst.setString(1,deadline);  
            pst.setString(2, time);
            pst.setString(3,date);
            pst.setString(4,descr);
           
            int x=pst.executeUpdate();
            
            pst.close();
            con.close();
            
            if(x==0){
                   pw.println("<html><body><h1>Details Unmatched, Update Failed" + "</h1></body></html>");
                   pw.println("<html><body><h3>Provide Date and Description to Update the Deadline and Time " + "</h3></body></html>");
                    pw.println("<input type= \n"+ "button \n" +"value=DashBoard\n" + "onclick=dashBoardPage(this.value);\n" + "/>");
                           pw.print(" </head>\n" +
"    <body>\n" +
"        <script type=\"text/javascript\">\n" +
"            function dashBoardPage(clickVal){\n" +
"                if(clickVal === \"DashBoard\")\n" +
"                    window.location = \"todo_web.html\";\n" +
"                else\n" +
"                    alert(\"Please click buttons to generate some events\");\n" +
"            }\n" +
"        </script>\n" +
"    </body>\n" +
"</html>");
            }
            else{
                pw.println("<html><body><h1>Successfully Updated" + "</h1></body></html>");
            pw.println("<input type= \n"+ "button \n" +"value=DashBoard\n" + "onclick=dashBoardPage(this.value);\n" + "/>");
                           pw.print(" </head>\n" +
"    <body>\n" +
"        <script type=\"text/javascript\">\n" +
"            function dashBoardPage(clickVal){\n" +
"                if(clickVal === \"DashBoard\")\n" +
"                    window.location = \"todo_web.html\";\n" +
"                else\n" +
"                    alert(\"Please click buttons to generate some events\");\n" +
"            }\n" +
"        </script>\n" +
"    </body>\n" +
"</html>");
            }
                  
        }
        
        if(select_val.equals("View")){
                try{
                       Statement st;
                      
                       ResultSet rs = null;
                       
                       String query = "SELECT * FROM todo.task";
                       
                           st = con.createStatement();
                           rs = st.executeQuery(query);
                           
                           pw.println("<html>");
                           pw.println("<head>");
                           pw.println("<title>TODO</title>");
                           pw.println("</head>");
                           pw.println("<body>");
                           pw.println("<fieldset>");
                           pw.println("<legend><h1>Your ToDo List</h1></legend>");
                           pw.println(" <div style=\"text-align: center\"><h3>TASK's</h3></div>");
                           pw.println("<table border=\"1\" style= \"margin-left:auto;margin-right:auto;\">");
                           pw.println("<tr><td><b>DATE</b></td><td><b>DESCRIPTION</b></td><td><b>DEADLINE</b></td><td><b>TIME</b></td></tr>");
                           
                           while (rs.next()) {
                               pw.println("<tr>");
                               pw.println("<td>" + rs.getString("date") + "</td>");
                               pw.println("<td>" + rs.getString("description") + "</td>");
                               pw.println("<td>" + rs.getString("deadline") + "</td>");
                               pw.println("<td>" + rs.getString("time") + "</td>");
                               pw.println("</tr>");
                           }
                           pw.println("</table>");
                }catch(SQLException sqlex){
                           sqlex.printStackTrace();
                           }
                         
                            pw.println("<br>");
                            pw.println("<input type= \n"+ "button \n" + "style=\"margin-left:47%;\""+"value=DashBoard\n" + "onclick=dashBoardPage(this.value);\n" + "/>");
                           pw.print(" </head>\n" +
"    <body>\n" +
"        <script type=\"text/javascript\">\n" +
"            function dashBoardPage(clickVal){\n" +
"                if(clickVal === \"DashBoard\")\n" +
"                    window.location = \"todo_web.html\";\n" +
"                else\n" +
"                    alert(\"Please click buttons to generate some events\");\n" +
"            }\n" +
"        </script>\n" +
"    </body>\n" +
"</html>");
            }
        
            
        if(select_val.equals("Delete")){
                java.sql.Statement remove = con.createStatement();
                int removed = remove.executeUpdate("delete from todo.task where date = '" + date + "';");
                if(removed==0){
                    pw.println("<html><body><h1>Details Unmatched" + "</h1></body></html>");
                    pw.println("<input type= \n"+ "button \n" +"value=DashBoard\n" + "onclick=dashBoardPage(this.value);\n" + "/>");
                           pw.print(" </head>\n" +
"    <body>\n" +
"        <script type=\"text/javascript\">\n" +
"            function dashBoardPage(clickVal){\n" +
"                if(clickVal === \"DashBoard\")\n" +
"                    window.location = \"todo_web.html\";\n" +
"                else\n" +
"                    alert(\"Please click buttons to generate some events\");\n" +
"            }\n" +
"        </script>\n" +
"    </body>\n" +
"</html>");
                }
                else{
                   pw.println("<html><body><h1>Successfully Deleted" + "</h1></body></html>");
                   pw.println("<input type= \n"+ "button \n" +"value=DashBoard\n" + "onclick=dashBoardPage(this.value);\n" + "/>");
                           pw.print(" </head>\n" +
"    <body>\n" +
"        <script type=\"text/javascript\">\n" +
"            function dashBoardPage(clickVal){\n" +
"                if(clickVal === \"DashBoard\")\n" +
"                    window.location = \"todo_web.html\";\n" +
"                else\n" +
"                    alert(\"Please click buttons to generate some events\");\n" +
"            }\n" +
"        </script>\n" +
"    </body>\n" +
"</html>");
                }
            }
            }catch(Exception e){
            e.printStackTrace();
        }
            
        }
    }