package stepik.javaweb1.lesson21.servlets;

import stepik.javaweb1.lesson21.accounts.AccountService;

public interface servletable {

    String SESSION_MEMBER_ATTR = "userId"
            ,COMMON_CONTENT_TYPE = "text/html;charset=utf-8"
            //,LOGOUT_FORM = "<form align='right' name='form1' method='get' action=''><label><input name='submit2' type='submit' id='submit2' value='log out'></label></form>"
            //,LOGOUT_FORM2 = "<form align='right' name='form1' method='DELETE' action=''><label><input name='submit2' type='submit' id='submit2' value='log out'></label></form>"
            //LOGOUT_FORM3 = "<button data-method='DELETE'>doDelete</button>"
            ,CALL_BACK_HTEXPR = "<br/><a href='/'>/</a>"
            ;
}
