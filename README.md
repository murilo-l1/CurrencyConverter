<h1>Currency Converter App </h1>
<h2>Introduction</h2>
<p>
    The Currency Converter App is a simple Java-based application that let users convert currencies using real - time conversion rates among a list of more than 168 tokens. It fetches conversion rates data from an external API and displays it in a graphical user interface (GUI). Users enter the amount, and both currencies to get the converted value. 
</p>

</p>

<p align="center">
    <img src="https://github.com/murilo-l1/CurrencyConverter/blob/master/src/main/assets/ApplicationDemo.png" align="center">
</p>

<h2>Technologies Used</h2>
<p>
    The Currency Converter utilizes the following technologies and libraries:
</p>
<ul>
  <li>Java 21</li>
  <li><a href="https://github.com/google/gson">Gson</a> - Used to parse and read through JSON data</li>
  <li><a href="https://square.github.io/okhttp/3.x/okhttp/okhttp3/OkHttpClient.html">OkHttpClient</a>: Library used for making HTTP requests to fetch data from the external API.</li>
  <li><a href="https://apilayer.com/marketplace/currency_data-api">API</a>: API used to fetch data.</li>
</ul>

<h2>Class Summaries</h2>

<h3>1.0. ConverterRunner </h3>
<p>
    <strong>Description:</strong> The ConverterRunner class serves as the initializer for the GUI using the 'invokelater()' to initialize the thread and display the window generated in 'ConverterGUI'.
</p>

<h3>2.0. ConverterController </h3>
<p>
    <strong>Description:</strong> The ConverterController class is responsible for handling all API calls required by the project, retrieving data from external APIs, and parsing it in a format suitable for consumption by the user interface.
</p>
<p>
    <strong>Summary:</strong> This class utilizes the OkHttpClient library to make HTTP requests to the external API. It retrieves data related to currency conversion rates and currency lists. The data retrieved is in JSON format, which is parsed using Gson for easy manipulation and consumption within the application.
</p>
<p>
    The ConverterController class provides the following key functionalities:
    <ul>
        <li><strong>convertValues:</strong> This method performs currency conversion by sending a request to the external API with the specified source and target currencies, along with the amount to be converted. It returns the converted value.</li>
        <li><strong>loadCurrencyList:</strong> This method retrieves the list of available currencies from the external API. It parses the JSON response and populates an ArrayList with the currency codes.</li>
        <li><strong>currencyIsValid:</strong> This method checks whether a given currency code is valid by comparing it against the list of available currencies.</li>
    </ul>
</p>

<h3>3.0. ConverterGUI </h3>
<p>
    <strong>Description:</strong> The ConverterGUI class represents the graphical user interface (GUI) for the currency conversion application. It provides the necessary components for users to input currency conversion parameters and view the results of conversions.
</p>
<p>
    <strong>Summary:</strong> This class utilizes Swing components to create a simple interactive user interface to get the necessary inputs in order to the methods from the controller class works.
<p>
    The ConverterController class provides the following key functionalities:
    <ul>
        <li><strong>createApplicationWindow:</strong> This method initializes the main application window, setting its size, title, layout, and other properties.</li>
        <li><strong>setComponents:</strong> This method sets up the various components of the GUI, including the logo, title label, descriptions, input fields, combo boxes, convert button, and result text area.</li>
        <li><strong>makeConversionAction:</strong> This method defines the action to be performed when the "Convert" button is clicked. It retrieves input values (amount, source currency, target currency) from the GUI components, invokes the conversion process through the Controller class, and displays the result in the result text area.</li>
    </ul>
</p>

<h3>4.0. ControllerTests </h3>
<p>
    <strong>Description:</strong> The ControllerTests class contains a suite of unit tests to verify the functionality of the ConverterController class.
</p>
<p>
    <strong>Summary:</strong> This class utilizes JUnit and assertions to perform a few unit testing on the methods of the ConverterController class that contais the main functionality of the application.
</p>
