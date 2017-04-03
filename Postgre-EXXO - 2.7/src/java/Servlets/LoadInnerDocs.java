package Servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ru.EXXO.EFile;
import ru.EXXO.EMail;
import ru.EXXO.EMailType;

/**
 *
 * @author Alexandre
 */
public class LoadInnerDocs extends HttpServlet {

    private static byte[] GetBytesFromStream(InputStream is) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];
        while ((nRead = is.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        return buffer.toByteArray();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchProviderException, MessagingException, ClassNotFoundException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession ses = request.getSession(true);
        String role = (String) ses.getAttribute("role");
        if (role.indexOf("p") >= 0) {
            ServletContext sc = request.getServletContext();
            String pop3 = BASE.VER.getInnerDocsPOP3(sc);
            String imap = BASE.VER.getInnerDocsIMAP(sc);
            String login = BASE.VER.getInnerDocsLogin(sc);
            String pass = BASE.VER.getInnerDocsPass(sc);
            String word = BASE.VER.getInnerDocsWord(sc);
            String from = BASE.VER.getInnerDocsFrom(sc);
            long maxSize = BASE.VER.getMaxFileSize(sc);
            int dopusk = BASE.VER.getInnerDocsOpen(sc);
            String server = "";
            String protocol = "";
            if (!pop3.equals("")) {
                protocol = "pop3";
                server = pop3;
            }
            if (!imap.equals("")) {
                protocol = "imap";
                server = imap;
            }
            if (!server.equals("") && !login.equals("") && !pass.equals("")) {
                Properties props = System.getProperties();
                //         props.put("mail.pop3.host", pop3);
                //        Session session = Session.getDefaultInstance(props, null);
                Session session = Session.getInstance(props);

                Store store = session.getStore(protocol);
                store.connect(server, login, pass);
                Folder folder = store.getFolder("INBOX");

                folder.open(Folder.READ_WRITE);

                Connection con = BASE.VER.getServletConnection(sc);
                con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
                Statement stmt = con.createStatement();

                //         Message[] message = folder.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
                Message[] message = folder.getMessages();
                int cat = 0;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd H:mm");

                for (int i = 0, n = message.length; i < n; i++) {
                    EMail email = new EMail();

                    int level = 0;
                    ProcessingPart(email, message[i], level);
                    String descr = email.getContent();
                    if (descr == null) {
                        descr = "";
                    }
                    List<EFile> list = email.getAttachedFile();
                    if ((from.equals("") || email.getFrom().get(0).contains(from)) && (list.size() > 0) && (word.equals("") || email.getSubject().contains(word))) {

                        String seqs = "SELECT nextval('files_seq')";

                        ResultSet rs = stmt.executeQuery(seqs);
                        if (rs.next()) {
                            cat = rs.getInt(1);
                        }
                        String catName;
                        catName = email.getSubject();
                        if (catName == null) {
                            catName = "";
                        } else {
                            catName = " " + catName;
                        }
                        String sql = "INSERT INTO  files (id, owner, name, file,superior,type,longname,tags,descr) VALUES (" + cat + ", '-101','" + simpleDateFormat.format(email.getSendDate()) + catName + "', '0', '1','','','','')";

                        stmt.executeUpdate(sql);
                        int fstore = BASE.VER.whereStore(sc);
                        if (fstore == -1) {
                            fstore = 0;
                        }
                        Connection con1 = BASE.VER.getDocsConnection(sc, fstore);
                        if (con1 == null) {
                            throw new SQLException("не задан параметр соединения с хранилищем документов");
                        }
                        for (int j = 0; j < list.size(); j++) {

                            byte[] bytes = null;
                            bytes = list.get(j).getContent();
                            String type = list.get(j).getMimeType().split(";")[0];
                            String fname = list.get(j).getFileName();
                            int iconlength = 0;
                            if (bytes != null && bytes.length <= maxSize) {
                                String format = "noresult";
                                boolean trig = false;
                                byte[] icon = null;
                                if (type.startsWith("image")) {
                                    format = EXXOlib.IMGESLIB.getFormat(bytes, type);
                                }
                                if (!format.equals("noresult")) {
                                    icon = EXXOlib.IMGESLIB.iconCropResize(bytes, 150, 150, format);
                                }
                                if (icon == null) {
                                    iconlength = 0;
                                } else {
                                    iconlength = icon.length;
                                }
                                if (con1 != null) {
                                    seqs = "SELECT nextval('files_seq'), nextval('files_ver_seq')";
                                    int files_seq = 0;
                                    int files_ver_seq = 0;
                                    rs = stmt.executeQuery(seqs);
                                    if (rs.next()) {
                                        files_seq = rs.getInt(1);
                                        files_ver_seq = rs.getInt(2);
                                    }

                                    String INSERT_FILE = "insert into files (owner, name, file,superior,type,longname,tags,descr,fname, dopusk_type, id, copy) values (?::int, ?, ?::smallint, ?::int, ?, ?, ?, ?, ?, ?::int,?,? )";

                                    PreparedStatement ps = null;

                                    con.setAutoCommit(false);
                                    String name = fname;
                                    name = (name.split("\\."))[0];
                                    ps = con.prepareStatement(INSERT_FILE);
                                    ps.setInt(1, -101);
                                    ps.setString(2, name);
                                    ps.setString(3, "1");
                                    ps.setInt(4, cat);
                                    ps.setString(5, type);
                                    ps.setString(6, "");
                                    ps.setString(7, "");
                                    ps.setString(8, descr);
                                    ps.setString(9, fname);
                                    ps.setInt(10, dopusk);
                                    ps.setInt(11, files_seq);
                                    ps.setInt(12, files_seq);
                                    ps.executeUpdate();

                                    sql = "INSERT INTO files_vers (id, size, ico, sizeico, ver_id, store, status) VALUES (?, ?, ?, ?::int,?,?,?)";
                                    ps = con.prepareStatement(sql);
                                    ps.setInt(2, bytes.length);
                                    //   ps.setBytes(1, bytes);
                                    ps.setBytes(3, icon);
                                    ps.setInt(4, iconlength);
                                    ps.setInt(1, files_seq);
                                    ps.setInt(5, files_ver_seq);
                                    ps.setInt(6, fstore);
                                    ps.setInt(7, 1);
                                    ps.executeUpdate();
                                    con.commit();

                                    sql = "INSERT INTO file_store(id,bin) VALUES(?, ?);";
                                    PreparedStatement ps1 = con1.prepareStatement(sql);
                                    ps1.setBytes(2, bytes);
                                    ps1.setInt(1, files_ver_seq);
                                    ps1.executeUpdate();
                                    ps1.close();

                                    ps.close();
                                } else {
                                    trig = true;
                                }

                                if (trig) {
                                    response.sendRedirect("alert.xhtml?p=noconnection");
                                }

                            }

                        }
                        con1.close();
                    }
                    message[i].setFlag(Flags.Flag.DELETED, true);
                }
                folder.close(true);
                stmt.close();
                con.close();
            }
        }
        response.sendRedirect("documentsMod.jsp?owner=-101");
    }

    /**
     * Обработка части письма
     *
     * @param email - объект EMail, который заполняеться из письма
     * @param m - Часть письма
     * @param level - Уровень структуры письма
     * @throws MessagingException
     * @throws IOException
     */

    private static void ProcessingPart(EMail email, Part m, Integer level)
            throws MessagingException, IOException {
        if (m instanceof Message) {
            ProcessingInfoPart(email, (Message) m);
        }
        String filename = m.getFileName();
        if ((filename == null || "".equals(filename)) && (m.isMimeType("text/plain"))) {
// Текст сообщения  подумать все-таки про html
            email.setContent((String) m.getContent());
        } else if (m.isMimeType("multipart/*")) {
// Рекурсивный разбор иерархии  
            Multipart mp = (Multipart) m.getContent();
            level++;
            int count = mp.getCount();
            for (int i = 0; i < count; i++) {
                ProcessingPart(email, mp.getBodyPart(i), level);
            }
        } else if (m.isMimeType("message/rfc822")) {
// Вложенное сообщение  
            level++;
            ProcessingPart(email, (Part) m.getContent(), level);
            level--;
        }

        if (level != 0 && !m.isMimeType("multipart/*") && filename != null) {
// Сохранения атачей  
            String disp = m.getDisposition();
// many mailers don't include a Content-Disposition  
            if (disp == null || disp.equalsIgnoreCase(Part.ATTACHMENT)) {

                List<EFile> efiles = email.getAttachedFile();
                InputStream in = null;
                try {
                    in = ((MimeBodyPart) m).getInputStream();
                    byte[] content = GetBytesFromStream(in);
                    EFile efile = new EFile();
                    efile.setContent(content);
                    efile.setMimeType(((MimeBodyPart) m).getContentType());
                    efile.setFileName(MimeUtility.decodeText(filename));
                    efiles.add(efile);
                } catch (IOException ex) {
                    try {
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException e) {
                    }
                }
            }
        }
    }

    /**
     * Обработка информационной части письма
     *
     * @param email - объект для сохранения email
     * @param m - входящий объект письма
     * @throws MessagingException
     * @throws UnsupportedEncodingException
     */
    private static void ProcessingInfoPart(EMail email, Message m)
            throws MessagingException, UnsupportedEncodingException {

// Обработка основных параметров письма  
        AddAddress(m.getFrom(), email, false, true);
        AddAddress(m.getRecipients(Message.RecipientType.TO), email, true,
                false);

        email.setSubject(m.getSubject());

        Date d = (m.getSentDate());
        if (d != null) {
            email.setSendDate(d);
        }

        String msgId = GetElementOrNull(m.getHeader("Message-Id"));

        if (msgId != null) {
            email.setMessageId(msgId);
        }
        String refs = GetElementOrNull(m.getHeader("References"));
        if (refs == null) {
            refs = GetElementOrNull(m.getHeader("In-Reply-To"));
        }
        if (msgId != null) {
            if (refs != null) {
                refs = MimeUtility.unfold(refs) + " " + msgId;
            } else {
                refs = msgId;
            }
        }
        if (refs != null) {
            email.setReferences(MimeUtility.fold(12, refs));
        }

// Обработка флагов сообщения  
        Flags flags = m.getFlags();
        Flags.Flag[] sf = flags.getSystemFlags();
        for (int i = 0; i < sf.length; i++) {
            Flags.Flag f = sf[i];
            if (f == Flags.Flag.ANSWERED) {
                email.setEmailType(EMailType.Answered);
            } else if (f == Flags.Flag.DELETED) {
                email.setEmailType(EMailType.Deleted);
            } else if (f == Flags.Flag.DRAFT) {
                email.setEmailType(EMailType.Draft);
            } else if (f == Flags.Flag.FLAGGED) {
                email.setEmailType(EMailType.Flagged);
            } else if (f == Flags.Flag.RECENT) {
                email.setEmailType(EMailType.Recent);
            } else if (f == Flags.Flag.SEEN) {
                email.setEmailType(EMailType.Seen);
            } else {
                continue;
            }
        }

// x-mail  
        String[] hdrs = m.getHeader("X-Mailer");
        if (hdrs != null) {
            email.setXMailer(hdrs[0]);
        } else {
            email.setXMailer("");
        }
    }

    /**
     * Добавляет адрес получателя/отправителя
     *
     * @throws UnsupportedEncodingException
     */
    private static void AddAddress(Address[] address, EMail email,
            Boolean addTo, Boolean addFrom) throws UnsupportedEncodingException {
        if ((!addTo && !addFrom) || (addTo && addFrom)) {
            throw new IllegalArgumentException(
                    "Не установлен не один из флагов addTo, addFrom или оба установлены!");
        }

        List<String> result = new ArrayList<String>();
        if (address == null || address.length == 0) {
            return;
        }
        for (int i = 0; i < address.length; i++) {
            result.add(MimeUtility.decodeText(address[i].toString()));
        }
        if (addTo) {
            email.setTo(result);
        }
        if (addFrom) {
            email.setFrom(result);
        }
    }

    /**
     * Возвращает первый не null элемент массива и если таких элементов нет, то
     * null
     *
     * @param mas - массив
     * @return Первый не null элемент массива и если таких элементов нет, то
     * null
     */
    private static String GetElementOrNull(String[] mas) {
        if (mas != null && mas.length > 0) {
            for (int i = 0; i < mas.length; i++) {
                if (mas[i] != null && !"".equals(mas[i])) {
                    return mas[i];
                }
            }
        }
        return null;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(LoadInnerDocs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(LoadInnerDocs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoadInnerDocs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoadInnerDocs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(LoadInnerDocs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MessagingException ex) {
            Logger.getLogger(LoadInnerDocs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(LoadInnerDocs.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(LoadInnerDocs.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
