package com.decode.web.domain.mail.service;

import com.decode.web.domain.mail.dto.MailDto;

public interface MailService {

    void sendMail(MailDto mailDto);

}
