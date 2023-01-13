/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres;


import fr.insa.titouan.encheres.objects.Bid;
import fr.insa.titouan.encheres.objects.Object;
import com.vaadin.flow.component.notification.Notification;
import fr.insa.titouan.encheres.objects.Bid;
import fr.insa.titouan.encheres.objects.Object;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Titouan
 */
public class bdd {

    public static Connection connectGeneralPostGres(String host, int port, String database, String user, String pass)
            throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        Connection con = DriverManager.getConnection(
                "jdbc:postgresql://" + host + ":" + port
                + "/" + database,
                user, pass);
        con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        return con;
    }

    public static Connection defaultConnect() throws ClassNotFoundException, SQLException {
        return connectGeneralPostGres("130.185.188.185", 5432, "postgres", "postgres", "root");
    }

    public static void createSchema(Connection con) throws SQLException {
        // je veux que le schema soit entierement créé ou pas du tout
        // je vais donc gérer explicitement une transaction
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            // creation des tables
                        st.executeUpdate("""
                             CREATE TABLE categories(
                                id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                name VARCHAR(64) NOT NULL UNIQUE
                             )
                             """);

            st.executeUpdate("""
                             CREATE TABLE users(
                                id integer not null primary key
                                generated always as identity,
                                nom varchar(64) not null,
                                prenom varchar(64) not null,
                                email varchar(64) not null unique,
                                pw varchar(1023) NOT NULL,
                                codepostal varchar(64)
                             )
                             """);
            st.executeUpdate("""
                             CREATE TABLE objects(
                                id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                title VARCHAR(64) NOT NULL UNIQUE,
                                description TEXT NOT NULL,
                                start_bids TIMESTAMP WITHOUT TIME ZONE,
                                end_bids TIMESTAMP WITHOUT TIME ZONE,
                                initial_price INTEGER,
                                category INTEGER,
                                created_by INTEGER,
                                highest_bid INTEGER,
                                image VARCHAR(256),
                                CONSTRAINT fk_obj_user
                                    FOREIGN KEY(created_by) 
                                        REFERENCES users(id)
                                        ON DELETE RESTRICT
                                        ON UPDATE RESTRICT,
                                CONSTRAINT fk_obj_cat
                                    FOREIGN KEY(category) 
                                        REFERENCES categories(id)
                                        ON DELETE RESTRICT
                                        ON UPDATE RESTRICT
                             )
                             """);

            st.executeUpdate("""
                             CREATE TABLE bids(
                                id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                from_user INTEGER,
                                on_object INTEGER,
                                at TIMESTAMP WITHOUT TIME ZONE,
                                value INTEGER,
                                CONSTRAINT fk_bids_user
                                    FOREIGN KEY(from_user) 
                                    REFERENCES users(id)
                                    ON DELETE RESTRICT
                                    ON UPDATE RESTRICT,
                                CONSTRAINT fk_bids_object
                                    FOREIGN KEY(on_object) 
                                        REFERENCES objects(id)
                                        ON DELETE RESTRICT
                                        ON UPDATE RESTRICT
                             )
                             """);
            // si j'arrive jusqu'ici, c'est que tout s'est bien passé
            // je confirme (commit) la transaction
            con.commit();
            // je retourne dans le mode par défaut de gestion des transaction :
            // chaque ordre au SGBD sera considéré comme une transaction indépendante
        } catch (SQLException ex) {
            // quelque chose s'est mal passé
            // j'annule la transaction
            con.rollback();
            // puis je renvoie l'exeption pour qu'elle puisse éventuellement
            // àªtre gérée (message à  l'utilisateur...)
            throw ex;
        } finally {
            // je reviens à  la gestion par défaut : une transaction pour
            // chaque ordre SQL
            con.setAutoCommit(true);
        }
    }

    public static String[] textUser() {
        String[] out = new String[5];
        try ( Scanner scanner = new Scanner(System.in)) {
            System.out.println("Quel est votre nom ? ");
            out[0] = scanner.nextLine();
            System.out.println("Quel est votre prénom ? ");
            out[1] = scanner.nextLine();
            System.out.println("Quel est votre email ? ");
            out[2] = scanner.nextLine();
            System.out.println("Quel est votre mot de passe ? ");
            out[3] = scanner.nextLine();
            System.out.println("Quel est votre code postal ? ");
            out[4] = scanner.nextLine();
        }
        return out;
    }

    public static int addUser(Connection con, String[] user) throws SQLException, NoSuchAlgorithmException {
        try ( PreparedStatement pst = con.prepareStatement(
                """
                insert into users (nom,prenom,email,pw,codepostal)
                values (?,?,?,?,?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, user[0]);
            pst.setString(2, user[1]);
            pst.setString(3, user[2]);
            pst.setString(4, hashPw(user[3], user[4]));
            pst.setString(5, user[4]);
            pst.executeUpdate();
            ResultSet uID = pst.getGeneratedKeys();
            uID.next();
            System.out.println("user added");
            return uID.getInt(1);
        }
    }

    public static void PrintObjects(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT id,title FROM objects");
            int i = 1;
            while (res.next()) {
                System.out.println(res.getInt("id") + " : " + res.getString("title") + ";");
                i++;
            }
        }
    }

    public static void showUsers(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT id,(nom || ' ' || prenom) AS ez FROM users");
            int i = 1;
            while (res.next()) {
                System.out.println(res.getInt("id") + " : " + res.getString("ez") + ";");
                i++;
            }
        }
    }

    public static List<Bid> showBids(Connection con) throws SQLException {
        List<Bid> out = new ArrayList<>();
        try ( Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("""
                                            SELECT at, value, (prenom || ' ' || nom) AS nom_complet, title
                                            FROM bids
                                            JOIN users ON from_user = users.id
                                            JOIN objects ON objects.id = on_object
                                            ORDER BY at ASC""");
            while (res.next()) {
                out.add(new Bid(res.getInt("value"),res.getTimestamp("at"), res.getString("nom_complet"), res.getString("title")));
            }
        }
        return out;
    }
    
    public static List<Object> showObjects(Connection con) throws SQLException {
        List<Object> out = new ArrayList<>();
        try ( Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("""
                                            SELECT title, objects.id, (prenom || ' ' || nom) AS nom_complet, highest_bid
                                            FROM objects
                                            JOIN users ON created_by = users.id""");
            while (res.next()) {
                out.add(new Object(res.getInt("id"), res.getString("title"), res.getString("nom_complet"), res.getInt("highest_bid")));
            }
        }
        return out;
    }

    public static int chooseUser(Connection con) throws SQLException {
        System.out.println("Qui êtes vous ? (entrer votre numéro)");
        showUsers(con);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static void showCategories(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT id,name FROM categories");
            while (res.next()) {
                System.out.println(res.getInt("id") + " : " + res.getString("name") + ";");
            }
        }
    }

    public static int chooseCategorie(Connection con) throws SQLException {
        System.out.println("Quel catégorie souhaitez vous ? (entrer son numéro)");
        showCategories(con);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static int addCategory(Connection con, String catName) throws SQLException {
        try ( PreparedStatement pst = con.prepareStatement("INSERT INTO categories (name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, catName);
            pst.executeUpdate();
            ResultSet catID = pst.getGeneratedKeys();
            catID.next();
            return catID.getInt(1);
        }
    }

    public static int addObject(Connection con, String title, String description, Timestamp end, int initial_price, int userID, int categoryID) throws SQLException {
        con.setAutoCommit(false);
        try ( PreparedStatement pst = con.prepareStatement(
                """
                INSERT INTO objects (title,description,start_bids,end_bids,initial_price,category,created_by,highest_bid)
                VALUES (?,?,?,?,?,?,?,?)
                """, PreparedStatement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, title);
            pst.setString(2, description);
            pst.setTimestamp(3, Timestamp.from(Instant.now()));
            pst.setTimestamp(4, end);
            pst.setInt(5, initial_price);
            pst.setInt(6, categoryID);
            pst.setInt(7, userID);
            pst.setInt(8, initial_price);
            pst.executeUpdate();
            System.out.println("System : user added");
            ResultSet oID = pst.getGeneratedKeys();
            oID.next();
            return oID.getInt(1);
        } finally {
            con.setAutoCommit(true);
        }
    }

    public static void textObject(Connection con) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int userID = chooseUser(con);
        System.out.println("Que vendez vous ? ");
        String title = scanner.nextLine();
        int categoryID = chooseCategorie(con);
        System.out.println("Décrivez votre objet : ");
        String description = scanner.nextLine();
        System.out.println("A quel prix doivent démarrer les enchères ? ");
        int initial_price = scanner.nextInt();
        System.out.println("Quand doit se terminer l'enchère ? (entrer sous la forme 'année-mois-jour heure:minute:seconde'");
        scanner.nextLine();
        Timestamp end = Timestamp.valueOf(scanner.nextLine());
        addObject(con, title, description, end, initial_price, userID, categoryID);
    }

    public static int addBid(Connection con, int userID, int objectID, int value) throws SQLException {
        con.setAutoCommit(false);
        try ( PreparedStatement pst = con.prepareStatement("SELECT highest_bid FROM objects WHERE id = ?")) {
            pst.setInt(1, objectID);
            ResultSet rst = pst.executeQuery();
            rst.next();
            if (rst.getInt("highest_bid") < value) {
                PreparedStatement pst2 = con.prepareStatement("""
                                              INSERT INTO bids (from_user, on_object, at, value)
                                              values (?,?,?,?)
                                              """, PreparedStatement.RETURN_GENERATED_KEYS);
                
                 
                pst2.setInt(1, userID);
                pst2.setInt(2, objectID);
                pst2.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                pst2.setInt(4, value);
                pst2.executeUpdate();
                ResultSet bidID = pst2.getGeneratedKeys();
                bidID.next();
                
                PreparedStatement pst3 = con.prepareStatement("UPDATE objects SET highest_bid = ? WHERE id = ?");
                pst3.setInt(1, value);
                pst3.setInt(2, objectID);
                pst3.executeUpdate();
                
                con.commit();
                
                return bidID.getInt(1);

            } else { //si l'enchère proposée est trop faible.
                con.rollback();
                return -1; //pas propre comme méthode...
            }
        }
        finally {
            con.setAutoCommit(true);
        }

    }

    public static ArrayList<String> getAllTableNames(Connection con) throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("""
 SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname != 'information_schema' AND schemaname != 'pg_catalog'                                                 
                                                  """);
        ArrayList<String> A = new ArrayList<>();
        while (rs.next()) {
            A.add(rs.getString("tablename"));
        }
        return A;
    }

    public static ArrayList<String> getAllRelationsName(Connection con) throws SQLException {
        ResultSet rs = con.createStatement().executeQuery("""
 SELECT conname FROM pg_catalog.pg_constraint                                                 
                                                  """);
        ArrayList<String> A = new ArrayList<>();
        while (rs.next()) {
            A.add(rs.getString("conname"));
        }
        return A;
    }

    public static void deleteAllTables(Connection con) throws ClassNotFoundException, SQLException {
        ArrayList<String> A = getAllTableNames(con);
        con.setAutoCommit(false);
        Statement st = con.createStatement();
        for (int i = 0; i < A.size(); i++) {
            st.executeUpdate("drop table " + A.get(i) + " CASCADE");
            con.commit();
        }
        con.setAutoCommit(true);
        System.out.println("tout drop");
    }

    public static void addExample(Connection con) throws SQLException, NoSuchAlgorithmException {
        int[] categories = new int[2];
        int[] users = new int[3];
        int[] bids = new int[5];
        int[] objects = new int[3];
        try {
            categories[0] = addCategory(con, "Moustaches");
            categories[1] = addCategory(con, "Endomorphismes");
            users[0] = addUser(con, new String[]{"Pelissier", "Jean", "mail.com", "motdp", "68007"});
            users[1] = addUser(con, new String[]{"Plage", "Toto", "parsols@mer.com", "helloWorld", "TK8639"});
            users[2] = addUser(con, new String[]{"Chèvre", "Gandolfi", "roi.des.vosges@caf.com", "helloWorld", "TK8639"});
            objects[0] = addObject(con, "bâtons", "lexi ultra trail", Timestamp.valueOf("2020-02-25 13:46:57"), 158, users[2], categories[1]);
            objects[1] = addObject(con, "noire", "a subit les méfait d'arthur", Timestamp.valueOf("2020-05-30 13:49:57"), 35, users[1], categories[0]);
            objects[2] = addObject(con, "lunette de soleil", "majoritairement utilisé de nuit", Timestamp.valueOf("2021-07-12 07:56:57"), 182, users[1], categories[0]);
            bids[0] = addBid(con, users[0], objects[0], 219);
            bids[1] = addBid(con, users[0], objects[1], 40);
            bids[2] = addBid(con, users[1], objects[0], 234);
            bids[3] = addBid(con, users[2], objects[0], 249);
            bids[4] = addBid(con, users[1], objects[1], 46);
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public static void textInterface() throws SQLException, NoSuchAlgorithmException {

        try ( Connection con = defaultConnect()) {
            int choice = 1;
            while (choice != 0) {
                System.out.println("Bienvenue dans l'interface textuelle du site de l'enchere.");
                Scanner scanner = new Scanner(System.in);
                System.out.println("Que souhaitez vous faire ? ");
                System.out.println("1 : créer un nouvel utilisateur.");
                System.out.println("2 : recréer les schemas de BDD.");
                System.out.println("3 : se connecter en tant qu'utilisateur.");
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1 ->
                        addUser(con, textUser());
                    case 2 -> {
                        deleteAllTables(con);
                        createSchema(con);
                    }
                    case 3 -> {
                        int userID = chooseUser(con);
                        while (choice != 0) {
                            System.out.println("Que souhaitez vous faire ? ");
                            System.out.println("1 : Créer un objet.");
                            System.out.println("2 : Créer une enchère. ");
                            System.out.println("3 : Afficher les enchères.");
                            System.out.println("4 : Afficher tous les objets.");
                            choice = scanner.nextInt();
                            switch (choice) {
                                case 1 ->
                                    textObject(con);
                                case 2 -> {
                                    PrintObjects(con);
                                    System.out.println("Entrer l'ID de l'objet : ");
                                    int objectID = scanner.nextInt();
                                    System.out.println("Quel est votre enchère ?");
                                    int value = scanner.nextInt();
                                    addBid(con, userID, objectID, value);
                                }
                                case 3 ->
                                    System.out.println("not done yet");
                                case 4 ->
                                    PrintObjects(con);
                            }
                        }
                        choice = 1;
                    }
                    default -> {
                    }
                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(bdd.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String hashPw(String pw, String CP) throws NoSuchAlgorithmException {
        String password = pw/*+CP*/;
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte [] hash = md.digest(password.getBytes());
        return new String(hash,Charset.forName("UTF-8"));
    }
    
    public static boolean userExists(String user) throws SQLException, ClassNotFoundException{
        Connection con = defaultConnect();
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery("SELECT email FROM users");
        boolean a=false;
        while (!a&&res.next()){
            a=user.equals(res.getString("email"));
        }
        return a;
    }
    
    public static boolean rightPw(String mail, String pw) throws SQLException, ClassNotFoundException, NoSuchAlgorithmException{
        Connection con = defaultConnect();
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery("SELECT email,codepostal,pw FROM users");
        boolean a=false;
        while (!a&&res.next()){
            if (mail.equals(res.getString("email"))){
                
                a=hashPw(pw, res.getString("codepostal")).equals(res.getString("pw"));
            }
        }
        return a;
    }
    
    public static int getUserId(String mail) throws ClassNotFoundException, SQLException{
        Connection con = defaultConnect();
        Statement st = con.createStatement();
        ResultSet res = st.executeQuery("SELECT id,email FROM users");
        int i=-1;
        while (res.next()){
            if (mail.equals(res.getString("email"))){
                i=res.getInt("id");
            }
        }
        return i;
    }
}
