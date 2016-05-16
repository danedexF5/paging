import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PeopleWeb {
    public static void main(String[] args) throws FileNotFoundException {
        // create a variable of type ArrayList that holds instances of People.
        ArrayList<Person> people = CSVParser.parse("people.csv");
        // Use the CSVParser to read and parse the people.csv into this ArrayList of People


        Spark.get(
                "/",
                (request, response) -> {
                    // Create a HashMap for your model
                    HashMap m = new HashMap();

                    // declare a variable named offset to hold your offset
                    // this needs to be an integer so we can do some arithmetic
                    int offset;

                    // because we're going to have to parse an integer from string data we need to open a try/catch block
                    try {
                        // Get the offset query parameter from the request.
                        String StrOffset = request.queryParams("offset");
                        // Set this into a string variable temporarily.


                        // Parse the string offset from a string to an integer.
                        offset = Integer.parseInt(StrOffset);
                        // Set the resulting integer into the variable you created to hold the offset.
                    }

                    // now we need a catch block in case the offset can't be parsed as an integer
                catch(NumberFormatException nfe) {
                    // When the number can't be parsed default the offset to 0
                    offset = 0;
                }


                    // We need to know two things
                    // 1) can we go to a previous page
                    // 2) if so, what is the offset of that page
                    // To do so, create an Integer variable named "backOffset" to hold the offset for the previous page and set it to null.
                    Integer backOffset = null;

                    // Check if the offset is not 0. This tells us we're not on the first page.
                    if(offset != 0) {
                        // If so, set backOffset to the offset minus twenty
                        backOffset = offset - 20;
                    }

                    // put the offset for the previous page into the model.
                    m.put("backOffset", backOffset);

                    // Now we need to know if we can go to a next page from the current page.
                    // Create another Integer variable named nextOffset and set that to null.
                    Integer nextOffSet = null;

                    // check if the current offset is less than the size of the people array, minus twenty
                    if(offset < people.size()-20) {
                        // if so we can go to the next page.
                        // set nextOffset to the offset plus twenty
                        nextOffSet = offset + 20;
                    }

                    // put the offset for the next page into the model.
                    m.put("nextOffset", nextOffSet);

                    // Now we need to get a subset of the people to display on this page.
                    // The ArrayList class has a method on it called subList(). Use this to get all of the
                    // People instances from the current offset to the offset plus twenty.
                    // Note that subList returns a List, not an ArrayList. You'll need to create a variable
                    // that can hold a List of People.
                    List listOfPeople = people.subList(offset, offset + 20);

                    // put the variable containing the list of people into the model with a key named "people"
                    m.put("people", listOfPeople);

                    // return a ModelAndView object for the people.mustache template.
                    return new ModelAndView(m, "people.mustache");
                },
                new MustacheTemplateEngine()
        );

        Spark.get(
                "/person",
                (request, response) -> {
                    // create a HashMap for your model
                    HashMap m = new HashMap();

                    // Create a variable to hold the ID property and read it from the request's query params
                    String peopleId = request.queryParams("id");

                    // for each Person instance in the people ArrayList do the following
                    for(Person p : people) {
                        // check if this Person instance's id property is equal to the ID in the request's query params.
                        // note: we just read this ID and created a variable to hold it
                        if (peopleId.equals(p.id)) {
                            // if the id DOES match, put this person into the model
                            m.put("person", p);
                            // break out of the for loop.
                            break;
                        }
                    }



                    // return a ModelAndView object for the person.mustache template.
                    return new ModelAndView(m, "person.mustache");
                },
                new MustacheTemplateEngine()
        );
    }
}
