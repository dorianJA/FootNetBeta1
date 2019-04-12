package sample;

import com.google.gson.Gson;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class TestButton    {


    public static void main(String[] args) throws IOException, ClassNotFoundException, JAXBException {


        Person person = new Person("Аленчев Максим",96,87,70,"bra",10,2,7);
        Person person1 = new Person("Andsad sadasda",96,87,70,"bra",10,2,7);
//        FileOutputStream file = new FileOutputStream("D:\\txt1.txt",true);
//        ObjectOutputStream oos = new ObjectOutputStream(file);
//        FileInputStream fileInputStream = new FileInputStream("D:\\txt1.txt");
//        ObjectInputStream ois = new ObjectInputStream(fileInputStream);
//
//          oos.writeObject(person);
//          oos.writeObject(person1);
//          oos.flush();
//          oos.close();
//
//
//        Person p = (Person)ois.readObject();
//        Person p2 = (Person)ois.readObject();
//        ois.close();

        Gson gson = new Gson();

        String p = gson.toJson(person);
        System.out.println(p);

        Person p2 = gson.fromJson(p,Person.class);
        System.out.println(p2);


            JAXBContext context = JAXBContext.newInstance(Person.class);
            Marshaller marshaller = context.createMarshaller();
            Unmarshaller unmarshaller = context.createUnmarshaller();
            File file = new File("D:\\myxml.xml");
            marshaller.marshal(person, file);
            Person newPerson = (Person) unmarshaller.unmarshal(file);
            System.out.println("========================================== "+newPerson);





        }



}


