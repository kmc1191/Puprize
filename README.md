# E-Store:  _____ Puprize Pet Grooming_ _____
# Modify this document to expand any and all sections that are applicable for a better understanding from your users/testers/collaborators (remove this comment and other instructions areas for your FINAL release)

An online E-store system built in Java 17 and ___ _replace with other platform requirements_ ___
  
## Team

- Kaitlynn Clement
- Rafael Rivera
- Paul Curcio
- Hunter Boggan
- Jade Condez


## Prerequisites

- Java 8=>11 (Make sure to have correct JAVA_HOME setup in your environment) (using Java 17)
- Maven
-  _add any other tech stack requirements_


## How to run it

1. Clone the repository and go to the root directory.
2. Execute `mvn compile exec:java`
3. Open in your browser `http://localhost:8080/`
4.  _add any other steps required or examples of how to use/run_

## Known bugs and disclaimers
(It may be the case that your implementation is not perfect.)

Document any known bug or nuisance.
If any shortcomings, make clear what these are and where they are located.

## How to test it

The Maven build script provides hooks for run unit tests and generate code coverage
reports in HTML.

To run tests on all tiers together do this:

1. Execute `mvn clean test jacoco:report`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/index.html`

To run tests on a single tier do this:

1. Execute `mvn clean test-compile surefire:test@tier jacoco:report@tier` where `tier` is one of `controller`, `model`, `persistence`
2. Open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/{controller, model, persistence}/index.html`

To run tests on all the tiers in isolation do this:

1. Execute `mvn exec:exec@tests-and-coverage`
2. To view the Controller tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
3. To view the Model tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`
4. To view the Persistence tier tests open in your browser the file at `PROJECT_API_HOME/target/site/jacoco/model/index.html`

*(Consider using `mvn clean verify` to attest you have reached the target threshold for coverage)
  
  
## How to generate the Design documentation PDF

1. Access the `PROJECT_DOCS_HOME/` directory
2. Execute `mvn exec:exec@docs`
3. The generated PDF will be in `PROJECT_DOCS_HOME/` directory


## How to setup/run/test program 
1. Tester, first obtain the Acceptance Test plan
2. IP address of target machine running the app
3. Execute cURL
    ->Get entire inventory: curl -i -X GET “http://localhost:8080/products”
    ->Get single product: curl -i -X GET “http://localhost:8080/products/1” (change the last number for product ID)
    ->Search for product with keyword: curl -i -X GET “http://localhost:8080/products/?name=keyword”
    ->Delete a product: curl -i -X DELETE “http://localhost:8080/products/1” (change last number for product ID)
    ->Create a new product: curl -i -X POST -H “Content-Type:application/json” “http://localhost:8080/products” -d “{\”name\”: \”NewProductName\”}” 
    ->Update the price and quantity of a product: curl -i -X PUT -H “Content-Type:application/json” “http://localhost:8080/products” -d “{\”id\”: 4, \”name\”: \”SameProductName\”, \”price\”: 25, \”quantity\”: 2}”
    
    -> Get all profiles: curl -i -X GET "http://localhost:8080/profiles"
    -> Get single profile: curl -i -X GET "http://localhost:8080/profiles/Cozmic"
    -> Create a new profile: curl -i -X POST -H “Content-Type:application/json” “http://localhost:8080/profiles” -d “{\”name\”: \”Yazz\”, \”username\”: \”scubaby\”, \”password\”: \”Skateboard\”, \”email\”: \”Yazz@gmail\”, \”phone\”: \”123\”, \”isOwner\”: false}”
    -> Update a profiles name: curl -i -X PUT -H "Content-Type:application/json" "http://localhost:8080/profiles" -d "{\"name\": \"Yazz Miller\", \"username\": \"scubaby\", \"password\": \"Skateboard\", \"email\": \"Yazz@gmail\", \"phone\": \"123\", \”isOwner\”: false}"
    -> Get the current login object: curl -i -X GET "http://localhost:8080/login"
    -> Login with a username and password: curl -i -X PUT -H "Content-Type:application/json" "http://localhost:8080/login?username=scubaby&password=Skateboard"

    -> Get all appointments: curl -i -X GET “http://localhost:8080/appointments”
    -> Get single appointment: curl -i -X GET “http://localhost:8080/appointments/1”
    -> Get an appointment that does not exist: curl -i -X GET “http://localhost:8080/appointments/25”
    -> Search for appointments with no specific date or time: curl -i -X GET “http://localhost:8080/appointments/?date=&time=”
    -> Search for appointments with a specific time: curl -i -X GET “http://localhost:8080/appointments/?date=&time=7_00”
    -> Search for appointments with a specific date: curl -i -X GET “http://localhost:8080/appointments/?date=12_3_2022&time=”
    -> Search for appointments with a specific date and time: curl -i -X GET “http://localhost:8080/appointments/?date=12_3_2022&time=3_00”
    -> Search for appointments with a date that does not exist: curl -i -X GET “http://localhost:8080/appointments/?date=12_25_2022&time=”
    -> Delete an appointment: curl -i -X DELETE “http://localhost:8080/appointments/1”
    -> Delete an appointment that does not exist: curl -i -X DELETE “http://localhost:8080/appointments/25”
    -> Create an appointment: curl -i -X POST -H “Content-Type:application/json” “http://localhost:8080/appointments” -d “{\”date\”: \”12_30_2022\”, \”time\”: \”2_30\”, \”user\”: \”\”, \”groomer\”: \”Hunter\”, \”reserved\”: \”false\”}”
    -> Update a user and a reservation status for an appointment: curl -i -X PUT -H “Content-Type:application/json” “http://localhost:8080/appointments” -d “{\”id\”: 6, \”date\”: \”12_30_2022\”, \”time\”: \”2_30\”, \”user\”: \”Mr. Mert\”, \”groomer\”: \”Hunter\”, \”reserved\”: \”true\”}”
    -> Update a groomer for an appointment: curl -i -X PUT -H “Content-Type:application/json” “http://localhost:8080/appointments” -d “{\”id\”: 6, \”date\”: \”12_30_2022\”, \”time\”: \”2_30\”, \”user\”: \”Mr. Mert\”, \”groomer\”: \”Paul\”, \”reserved\”: \”true\”}”

4. When running the front-end, as of Sprint 3: release 1: 
        -> In one terminal, navigate to "estore-api" and run "mvn compile exec:java"
        -> In a different terminal, with the back-end now running, navigate to "estore-api/puprize-app" and run ng serve --open
        -> You should now see the website open in your browser, defaulting to the "home" page

        -> WHEN NOT LOGGED IN: 
            -> you do not have access to create or remove products, or to add products to the cart
            -> you can still search for products on the Products page
            -> you do not have access to reserving or viewing appointment slots
            -> you will not see any information in the profile page
            -> you will not be able to view any shopping cart
            -> you will be able to view information on the services and FAQ page
        
        -> TO LOG IN: 
            -> click the LOGIN button towards the top of the page to get to the login page
            -> NOTE that the statement "Username and password fields are required" will be displayed until at least one character is entered in both the Username and Password box
            -> when the login fails (username/password invalid), a failure message will be displayed and you will remain on the login page
            -> to log in, take the username and password from any object in profiles.json. For example: username "Cozmic" and password "Tabletop". After entering both of the fields accordingly, click the Login button below the input spaces (or hit Enter)
            -> a welcome message will be displayed using the corresponding name of the user profile (in this case, "Kaitlynn". again, these names can be viewed in profiles.json)
            -> a FIRST TIME USER? button is also included for those who want to create a new account

        -> CREATING AN ACCOUNT:
            -> by clicking the FIRST TIME USER? button, you will be prompted with various fields to create your profile
            -> usernames that already exist will not be accepted, users will be prompted to enter a new username
            -> all fields are required for the creation of a profile
        
        -> TO LOG OUT: 
            -> click the Logout button that is under the "Hello" message on the Login page
            -> this will automatically log out the user and display the login form once again.

        -> SEARCHING FOR PRODUCTS:
            -> type a keyword in the search bar, and any product in the inventory with a matching name will have a labeled button spawn under the search bar
            -> clicking one such button will move the item to the top of the inventory list for ease of access
            -> NOTE that searching for a product will not remove the other products from the display: it simply allows you to pull the desired product to the top to view it

        -> WHEN LOGGED IN AS A CUSTOMER: 
            -> Customers are not allowed to see out of stock products
            -> you can still search for products on the products page, but you can now add products to the cart. Use the number input field next to "Quantity to Add" on a product to select the amount
            -> NOTE that this amount is not allowed to go above the amount in stock, displayed in the bottom left of the product, and cannot go below 1
            -> NOTE that no indication is currently made when the Add Product button is clicked. You must move the the Cart page to see the changes
            -> NOTE that products are not removed from the overall inventory when they are added to your cart. There is back-end functionality in place for them to be removed when the user checks out, but DISCLAIMER: there is not front-end fucntionality for checking out as of this release. 
            -> in the Cart page, you will be able to see the items you have added to your cart (or nothing if you haven't added anything)
            -> you can remove in much the same way that you add, using the number input box and the Remove Product button. For specifications on number input, see the instructions for adding to the cart. 
            -> you can empty the entire cart by pressing the "empty cart" button
            -> you can now checkout by pressing the checkout button, which will pull up a form to enter checkout information
            -> NOTE that this form cannot be submitted until every field is filled out
            -> you can view our master calendar of appointments by clicking APPOINTMENTS towards the top of
            -> here you can see a calender with dates you can click to see the available appointments for that day along with a button saying CONFIRM RESERVATION that allows the user to reserve the appointment under their appointment
            -> upon reserving an appointment, it will not be available anymore and the user with the reserved appointment will not be able to reserve any other appointments 
            -> upon clicking a date, a list of available appointments and their information will display to the user
            -> you can view your profile information by clicking PROFILE towards the top of the page to get to the profile 
            page. Here the current user's name, username, password, email, and phone are visible.
            -> the user can update their profile information and save these changes using the SAVE CHANGES button near the bottom of the screen
            -> DISCLAIMER: the user can not change their username
            -> the user can view their reserved appointment's date and time. If there is no appointment reserved then a message will display saying "No Appointment currently reserved".
            -> the user can cancel their appointment if they have one reserved using the CANCEL APPOINTMENT button located near the display for the appointment.
            -> the user can add a pet profile to their profile at the profile page using the CREATE PET PROFILE button. This will display a field of text boxes for the user to enter in a pet profile's name, age, breed, temperament, extra information, nail trim, ear clean, bath, style, and cut. If all required fields are not filled out and/or in proper format the user can not create a pet profile. They then click the CREATE PET! button to create and view the pet profile.
            -> NOTE: the temperament and extra information fields are optional and the age field will only take a number. The user can also only create 1 pet profile.
            -> the user can view their pet profile when navigating to the profile page using the PROFILE button towards the top of the page. The user can delete their pet profle using the DELETE PET PROFILE button at the bottom of the screen. 


        -> WHEN LOGGED IN AS THE OWNER: 
            -> the owner has a special set of login credentials, which is also in profiles.json under the profile with name "owner". the owner's username is "admin", and the password is "ownerPassword"
            -> on the products page, the owner can see out of stock products and search for out of stock products
            -> the owner can perform different actions in the Products page. the owner cannot add anything to a cart, but there are new buttons that can be used. 
            -> the X button will remove the product from the inventory
            -> A new product can be created using the fields at the bottom and then clicking Add product, and the page will automatically show the new product in the inventory
            -> NOTE: A new product can be made with quantity and price 0. Quantities of 0 will not be shown to the customer.
            -> when the owner views the profile page using the PROFILE button located towards the top of the page. The owner can view their profile information but they will not be able to view an appointments field and will not be able to create a pet profile.
            -> the owner cannot view a shopping cart and will find a button to be redirected to the login page
            -> the owner can view the master calender by navigating to the appointments page using the APPOINTMENTS button located towards the top of the page. 
            -> upon clicking a date, the owner can change the groomer name and update the appointment's visibility using the BLOCK APPOINTMENT SLOT button at the bottom of that specific appointment. The owner can save the changed appointment information using the SAVE CHANGES button located at the bottom of that specific appointment. Upon chnaging the visibility, to block the slot, the user will no longer be able to see that appointment in their view.

## License

MIT License

See LICENSE for details.
