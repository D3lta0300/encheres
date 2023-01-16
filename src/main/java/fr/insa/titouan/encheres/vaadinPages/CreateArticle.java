/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres.vaadinPages;

/**
 *
 * @author magic
 */
import java.sql.Timestamp;
import java.time.LocalDateTime;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import fr.insa.titouan.encheres.bdd;
import java.awt.image.BufferedImage;

import java.io.*;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

//Fortement inspiré de https://cookbook.vaadin.com/upload-image-to-file
@Route("upload-image-to-file")
public class CreateArticle extends VerticalLayout {


    private File file;
    private String originalFileName;
    private String mimeType;
    private TextField Titre;
    private TextArea Description;
    private DatePicker fin;
    private TextField prix;
    private ComboBox categories;
    private Button send;

    CreateArticle(VuePrincipale main) {
        Upload upload = new Upload(this::receiveUpload);
        Div output = new Div(new Text("Ajoutez une image"));
        this.originalFileName = "";
        this.Titre = new TextField("Titre");
        this.Description = new TextArea("Description");
        Description.setHeight("200px");
        Description.setWidth("800px");
        this.send = new Button("Valider");
        this.fin = new DatePicker("Date de Fin");
        this.categories = new ComboBox("Categories");
        try {
            ArrayList<String> a = bdd.getCategories(main.getSession().getCon());
            String [] b = new String [a.size()] ;
            for (int i=0;i<b.length;i++){
                b[i]=a.get(i);
            }
            this.categories.setItems(b);
        } catch (SQLException ex) {
            Logger.getLogger(CreateArticle.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.prix = new TextField("Prix Initial");
        add(upload, output, categories,Titre, Description,fin,prix,send);

        // Configure upload component
        upload.setAcceptedFileTypes("image/jpeg", "image/png", "image/gif");
        upload.addSucceededListener(event -> {
            output.removeAll();
            output.add(new Text("Uploaded: "+originalFileName+" to "+ file.getAbsolutePath()+ "Type: "+mimeType));
            output.add(new Image(new StreamResource(this.originalFileName,this::loadFile),"Uploaded image"));
        });
        upload.addFailedListener(event -> {
            output.removeAll();
            output.add(new Text("Upload failed: " + event.getReason()));
        });
        send.addClickListener((event) -> {
            LocalDateTime ldt = LocalDateTime.of(fin.getValue(), LocalTime.MIN);
            Timestamp ts = Timestamp.valueOf(ldt);
            try {
                BufferedImage bImage = ImageIO.read(file);
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ImageIO.write(bImage, "jpg", bos );
                byte [] data = bos.toByteArray();
                bdd.addArticle(main.getSession().getCon(), Titre.getValue(), Description.getValue(),ts, Integer.parseInt(prix.getValue()), main.getSession().getUser(), bdd.getCategoryFromName(main.getSession().getCon(), (String) this.categories.getValue()),data);
            } catch (IOException ex) {
                Logger.getLogger(CreateArticle.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(CreateArticle.class.getName()).log(Level.SEVERE, null, ex);
            }
            Titre.clear();
            Description.clear();
            fin.clear();
            prix.clear();
            output.removeAll();
            categories.clear();
            upload.clearFileList();
        });
        

    }

    /** Load a file from local filesystem.
     *
     */
    public InputStream loadFile() {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Failed to create InputStream for: '" + this.file.getAbsolutePath(), e);
        }
        return null;
    }

    /** Receive a uploaded file to a file.
     */
    public OutputStream receiveUpload(String originalFileName, String MIMEType) {
        this.originalFileName = originalFileName;
        this.mimeType = MIMEType;
        try {
            // Create a temporary file for example, you can provide your file here.
            this.file = File.createTempFile("prefix-", "-suffix");
            file.deleteOnExit();
            return new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Failed to create InputStream for: '" + this.file.getAbsolutePath(), e);
        } catch (IOException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Failed to create InputStream for: '" + this.file.getAbsolutePath() + "'", e);
        }

        return null;
    }
}
