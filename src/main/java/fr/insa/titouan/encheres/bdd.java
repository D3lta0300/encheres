/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fr.insa.titouan.encheres;

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
            st.executeUpdate(
                    """
                    CREATE TABLE clients(
                        id INTEGER PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        nom VARCHAR(30) NOT NULL UNIQUE,
                        pass VACHAR(30) NOT NULL
                    )
                    """);

            st.executeUpdate("""
                             CREATE TABLE objects(
                                id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                title VARCHAR(64) NOT NULL UNIQUE,
                                description TEXT NOT NULL,
                                start TIMESTAMP WITHOUT TIME ZONE,
                                end TIMESTAMP WITHOUT TIME ZONE,
                                initial_price INTEGER,
                                category INTEGER,
                                created_by INTEGER
                             )
                             """);

            st.executeUpdate("""
                             CREATE TABLE bids(
                                id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                                from_user INTEGER,
                                on_object INTEGER,
                                when TIMESTAMP WITHOUT TIME ZONE,
                                value INTEGER
                             )
                             """);

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
                                pw varchar(128) not null,
                                codepostal varchar(64)
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

    public static void createTestTable(Connection con) throws SQLException {
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            // creation des tables
            st.executeUpdate(
                    """
                    create table test(
                        id integer not null primary key
                        generated always as identity,
                        nom varchar(64) not null,
                        prenom varchar(64) not null,
                        email varchar(64) not null unique,
                        pw varchar(128) not null,
                        codepostal varchar(64)
                    )
                    """);
            con.commit();
            System.out.println("table créée");
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            con.rollback();
            System.out.println("Il y a eu une erreur, table non créée.");
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }

    public static void clearTables(Connection con) throws SQLException {
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            st.executeUpdate("""
                             DROP TABLE bids
                             """);
            st.executeUpdate("""
                             DROP TABLE categories
                             """);
            st.executeUpdate("""
                             DROP TABLE clients
                             """);
            st.executeUpdate("""
                             DROP TABLE objects
                             """);
            st.executeUpdate("""
                             DROP TABLE users
                             """);
        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(true);
        }
    }

    public static void deleteTable(Connection con)
            throws SQLException {
        // je veux que le schema soit entierement créé ou pas du tout
        // je vais donc gérer explicitement une transaction
        con.setAutoCommit(false);
        try ( Statement st = con.createStatement()) {
            // creation des tables
            st.executeUpdate(
                    """
                    drop table Clients
                    """);
            System.out.println("table dropped");
            // si j'arrive jusqu'ici, c'est que tout s'est bien passé
            // je confirme (commit) la transaction
            con.commit();
            // je retourne dans le mode par défaut de gestion des transaction :
            // chaque ordre au SGBD sera considéré comme une transaction indépendante
            con.setAutoCommit(true);
        } catch (SQLException ex) {
            // quelque chose s'est mal passé
            // j'annule la transaction
            con.rollback();
            // puis je renvoie l'exeption pour qu'elle puisse éventuellement
            // être gérée (message à  l'utilisateur...)
            throw ex;
        } finally {
            // je reviens à  la gestion par défaut : une transaction pour
            // chaque ordre SQL
            con.setAutoCommit(true);
        }
    }

    public static void main(String[] args) {
        try {
            Connection con = defaultConnect();
            System.out.println("Link is sucessfully etablished");
            createSchema(con);
            System.out.println("Tables have been created");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(bdd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(bdd.class.getName()).log(Level.SEVERE, null, ex);
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

    public static void addUser(Connection con, String[] user) throws SQLException {
        try ( PreparedStatement pst = con.prepareStatement(
                """
                insert into test (nom,prenom,email,pw,codepostal)
                values (?,?,?,?,?)
                """)) {
            pst.setString(1, user[0]);
            pst.setString(2, user[1]);
            pst.setString(3, user[2]);
            pst.setString(4, user[3]);
            pst.setString(5, user[4]);
            pst.executeUpdate();
            System.out.println("user added");
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

    public static int chooseUser(Connection con) throws SQLException {
        System.out.println("Qui êtes vous ? (entrer votre numéro)");
        showUsers(con);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static void showCategories(Connection con) throws SQLException {
        try ( Statement st = con.createStatement()) {
            ResultSet res = st.executeQuery("SELECT id,name FROM categories");
            int i = 1;
            while (res.next()) {
                System.out.println(res.getInt("id") + " : " + res.getString("name") + ";");
                i++;
            }
        }
    }

    public static int chooseCategorie(Connection con) throws SQLException {
        System.out.println("Quel catégorie souhaitez vous ? (entrer son numéro)");
        showCategories(con);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static void createObject(Connection con, String title, String description, Timestamp end, int initial_price, int userID, int categoryID) throws SQLException {
        con.setAutoCommit(false);
        try ( PreparedStatement pst = con.prepareStatement(
                """
                INSERT INTO objects (title,description,start,end,initial_price,category,created_by)
                values (?,?,?,?,?,?,?)
                """)) {
            pst.setString(1, title);
            pst.setString(2, description);
            pst.setTimestamp(3, Timestamp.from(Instant.now()));
            pst.setTimestamp(4, end);
            pst.setInt(5, initial_price);
            pst.setInt(6, categoryID);
            pst.setInt(7, userID);
            pst.executeUpdate();
            System.out.println("System : user added");
        }
        con.setAutoCommit(true);
    }

    public static void textObject(Connection con) throws SQLException{
        Scanner scanner = new Scanner(System.in);
        int userID = chooseUser(con);
        System.out.println("Que vendez vous ? ");
        String title = scanner.nextLine();
        int categoryID = chooseCategorie(con);
        System.out.println("Décrivez votre objet : ");
        String description = scanner.nextLine();
        System.out.println("A quel prix doivent démarrer les enchères ? ");
        int initial_price = scanner.nextInt();
        System.out.println("Quand doit se terminer l'enchère ? (entrer sous la ofrme 'année-mois-jour heure:minute:seconde'");
        Timestamp end = Timestamp.valueOf(scanner.nextLine());
        createObject(con, title, description, end, initial_price, userID, categoryID);
    }
    
    public static void textInterface() throws SQLException{
        Connection con = defaultConnect();
    }
    
    public static void userBilan() throws SQLException{
        
    }
    
    public static ArrayList<String> getAllTableNames(Connection con) throws SQLException{
        ResultSet rs =  con.createStatement().executeQuery("""
 SELECT tablename FROM pg_catalog.pg_tables WHERE schemaname != 'information_schema' AND schemaname != 'pg_catalog'                                                 
                                                  """);
        ArrayList<String> A = new ArrayList <>();
        while(rs.next()){
            A.add(rs.getString("tablename"));
        }
        return A;
    }
    
    public static ResultSet getAllRelations(Connection con){
        
    }
    
    public static void deleteAll(String[] args) {
        
    }

}
