import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by doug on 5/8/16.
 */
public class CSVParser {

    /**
     * This method takes the name of a file to read and parses it into an ArrayList of Person instances
     * @param filename
     * @return
     * @throws FileNotFoundException
     */
    public static ArrayList<Person> parse(String filename) throws FileNotFoundException {
        // Create an empty ArrayList of Person instances
        ArrayList<Person>personTraits = new ArrayList<>();

        // create an instance of File for the file name passed in
        File people = new File("people.csv");
        // Create an instance of Scanner to read the file
        Scanner scan = new Scanner(people);


        // read the first line of the file, but do nothing with the results.
        // We're doing this because the first line of the file contains column names and we don't want/need them.
        scan.nextLine();

        // while the scanner has more lines do the following
        while (scan.hasNext()) {
            // Read the next line from the scanner and store it in a variable
            String line = scan.nextLine();

            // Split the variable holding this line of data. Use comma as the delimiter.
            // note: there is one line of csv file that has a comma in the country name.
            // I'm ignoring that for the purpose of keeping this code simple.
            String[] personSplit = line.split("\\,");

            // Create a new instance of a Person object
            Person person1 = new Person();
            // set the person's ID using the split data from above
            person1.id = personSplit[0];
            // set the person's first name using the split data from above
            person1.firstName = personSplit[1];
            // set the person's last name using the split data from above
            person1.lastName = personSplit[2];
            // set the person's email using the split data from above
            person1.email = personSplit[3];
            // set the person's country using the split data from above
            person1.country = personSplit[4];
            // set the person's ip address using the split data from above
            person1.ipAddress = personSplit[5];

            // add the person to the array list of people
            personTraits.add(person1);
        }


            // return the array list of people
            return personTraits;

    }
}
