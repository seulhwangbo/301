package service;

import java.io.IOException;

import dao.Board;
import dao.BoardDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WriteFormAction implements CommandProcess {

   @Override
   public String requestPro(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
      System.out.println("WriteFormAction start....");
      
      try {
         //logic을 댓글하고 합치려고 미리 변수로 잡아놓는 것! 
         //신규글
         int num = 0, ref = 0, re_level = 0, re_step = 0;
         String pageNum = request.getParameter("pageNum");
         if (pageNum == null) pageNum = "1";
         
         //댓글일 경우
         if(request.getParameter("num") != null) {
        	 num = Integer.parseInt(request.getParameter("num"));
        	 BoardDao bd = BoardDao.getInstance();
        	 Board board = bd.select(num);
        	 ref = board.getRef();
        	 re_level=board.getRe_level();
        	 re_step= board.getRe_step();
         }
         
         
         request.setAttribute("num", num);
         request.setAttribute("ref", ref);
         request.setAttribute("re_level", re_level);
         request.setAttribute("re_step", re_step);
         request.setAttribute("pageNum", pageNum);
      } catch (Exception e) {
         System.out.println("WriteFormAction e.getMessage()=>"+e.getMessage());
      }   
      
      return "writeForm.jsp";
   }

}
