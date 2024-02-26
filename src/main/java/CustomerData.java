import java.util.HashMap;
import java.util.Iterator;
import java.util.Objects;

public class CustomerData {
  // a map to store all attributes of customer's data from CSV header, so the amount of attribute can change
  private HashMap<String, String> attributes = new HashMap<>();

  public CustomerData(HashMap<String, String> attributes){
    this.attributes = attributes;
  }// constructor

  public void setAttribute(String attributeName, String attributeValue) {
    attributes.put(attributeName,attributeValue);
  } // set one attribute into the map, used in csvfilereader class
  public String getAttribute(String attributeName){ // getter function
    String ans =  attributes.get(attributeName);
    return ans;
  }
  public Iterator<String> getAllAttributes() {
    return attributes.keySet().iterator();
  } // check all the attributes
  @Override
  public boolean equals(Object o) { // equal method
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CustomerData that = (CustomerData) o;
    return Objects.equals(attributes, that.attributes);
  }

  @Override
  public int hashCode() { // hashcode method
    return Objects.hash(attributes);
  }

  @Override
  public String toString() {// tostring method
    return "CustomerData{" +
        "attributes=" + attributes +
        '}';
  }
 // setter and getter function for hashmap attributes
  public HashMap<String, String> getAttributes() {//getter method
    return attributes;
  }

  public void setAttributes(HashMap<String, String> attributes) {// setter method
    this.attributes = attributes;
  }
}