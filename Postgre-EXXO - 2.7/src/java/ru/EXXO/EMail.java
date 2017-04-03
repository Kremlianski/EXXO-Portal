package ru.EXXO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Класс описывающий письмо
 */
public class EMail extends Object {

    public EMail() {
        attachedFile = new ArrayList<EFile>();
    }

    private String content;
    private List<EFile> attachedFile;
    private EMailType emailType;
    private List<String> from;
    private List<String> to;
    private String subject;

    private Date sendDate;

    private String messageId;

    private String references;

    private String replyContent;

    private EFile replyFile;

    private Boolean isValid;

    private String xMailer;

    /**
     * Устанавливает флаг письма
     *
     * @param emailType - флаг письма
     */
    public void setEmailType(EMailType emailType) {
        this.emailType = emailType;
    }

    /**
     * Возвращает флаг письма
     *
     * @return - флаг письма
     */
    public EMailType getEmailType() {
        return emailType;
    }

    /**
     * Устанавливает список прикрепленных файлов
     *
     * @param attachedFile - список прикрепленных файлов
     */
    public void setAttachedFile(List<EFile> attachedFile) {
        this.attachedFile = attachedFile;
    }

    /**
     * Возвращает список прикрепленных файлов
     *
     * @return - список прикрепленных файлов
     */
    public List<EFile> getAttachedFile() {
        return attachedFile;
    }

    /**
     * Устанавливает список отправителей
     *
     * @param from - список отправителей
     */
    public void setFrom(List<String> from) {
        this.from = from;
    }

    /**
     * Возвращает список отправителей
     *
     * @return - список отправителей
     */
    public List<String> getFrom() {
        return this.from;
    }

    /**
     * Устанавливает список получателей
     *
     * @param to - список получателей
     */
    public void setTo(List<String> to) {
        this.to = to;
    }

    /**
     * Возвращает список получателей
     *
     * @return - список получателей
     */
    public List<String> getTo() {
        return to;
    }

    /**
     * Устанавливает тему письма
     *
     * @param subject - тема письма
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Возвращает тему письма
     *
     * @return - тема письма
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Устанавливает дату отправки письма
     *
     * @param sendDate - дата отправки письма
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    /**
     * Возвращает дату отправки письма
     *
     * @return - дата отправки письма
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * Устанавливает содержание письма
     *
     * @param content - содержание письма
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Возвращает содержание письма
     *
     * @return - содержание письма
     */
    public String getContent() {
        return content;
    }

    /**
     * Устанавливает идентификатор письма
     *
     * @param messageId - идентификатор письма
     */
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    /**
     * Возвращает идентификатор письма
     *
     * @return - идентификатор письма
     */
    public String getMessageId() {
        return messageId;
    }

    /**
     * Устанавливает ссылки на связанные письма
     *
     * @param references - ссылки на связанные письма
     */
    public void setReferences(String references) {
        this.references = references;
    }

    /**
     * Возвращает ссылки на связанные письма
     *
     * @return - ссылки на связанные письма
     */
    public String getReferences() {
        return references;
    }

    public String getXMailer() {
        return xMailer;
    }

    public void setXMailer(String xMailer) {
        this.xMailer = xMailer;
    }

}
