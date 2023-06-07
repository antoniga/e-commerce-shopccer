package com.shopccer.site.util;

import com.shopccer.common.entity.Ajuste;
import com.shopccer.common.entity.AjusteUtil;

import java.util.List;

public class EmailUtil extends AjusteUtil {
    public EmailUtil(List<Ajuste> listaAjustes) {
        super(listaAjustes);
    }


    public String getHost() {
        return super.getValor("MAIL_HOST");
    }

    public int getPort() {
        return Integer.parseInt(super.getValor("MAIL_PORT"));
    }

    public String getUsername() {
        return super.getValor("MAIL_USERNAME");
    }

    public String getPassword() {
        return super.getValor("MAIL_PASSWORD");
    }

    public String getSmtpAuth() {
        return super.getValor("SMTP_AUTH");
    }

    public String getSmtpSecured() {
        return super.getValor("SMTP_SECURED");
    }

    public String getFromAddress() {
        return super.getValor("MAIL_FROM");
    }

    public String getSenderName() {
        return super.getValor("MAIL_SENDER_NAME");
    }

    public String getCustomerVerifySubject() {
        return super.getValor("CUSTOMER_VERIFY_SUBJECT");
    }

    public String getCustomerVerifyContent() {
        return super.getValor("CUSTOMER_VERIFY_CONTENT");
    }

    public String getOrderConfirmationSubject() {
        return super.getValor("ORDER_CONFIRMATION_SUBJECT");
    }

    public String getOrderConfirmationContent() {
        return super.getValor("ORDER_CONFIRMATION_CONTENT");
    }

}
